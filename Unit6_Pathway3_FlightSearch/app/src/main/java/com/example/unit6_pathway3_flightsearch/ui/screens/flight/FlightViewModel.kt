package com.example.unit6_pathway3_flightsearch.ui.screens.flight

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.unit6_pathway3_flightsearch.FlightSearchApplication
import com.example.unit6_pathway3_flightsearch.data.FlightRepository
import com.example.unit6_pathway3_flightsearch.data.UserPreferencesRepository
import com.example.unit6_pathway3_flightsearch.data.model.Airport
import com.example.unit6_pathway3_flightsearch.data.model.Favorite
import com.example.unit6_pathway3_flightsearch.data.Flight
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import android.util.Log

class FlightViewModel(
    private val flightRepository: FlightRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedAirportCode = MutableStateFlow<String?>(null)
    val selectedAirportCode: StateFlow<String?> = _selectedAirportCode.asStateFlow()

    val autocompleteSuggestions: StateFlow<List<Airport>> = _searchQuery
        .flatMapLatest { query ->
            if (query.isEmpty()) {
                kotlinx.coroutines.flow.flowOf(emptyList())
            } else {
                flightRepository.getAllAirportsFlow(query)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    val favoriteRoutes: StateFlow<List<Flight>> = flightRepository.getAllFavoriteFlightsFlow()
        .flatMapLatest { favorites ->
            mapFavoritesToFlights(favorites)
        }
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    val destinationFlights: StateFlow<List<Flight>> = combine(
        _selectedAirportCode,
        flightRepository.getAllAirportsFlow(),
        flightRepository.getAllFavoriteFlightsFlow()
    ) { code, allAirports, favorites ->
        if (code == null) {
            emptyList()
        } else {
            val departureAirport = allAirports.firstOrNull { it.iata_code == code }
            if (departureAirport == null) {
                emptyList()
            } else {
                allAirports
                    .filter { it.iata_code != code }
                    .map { destinationAirport ->
                        val isFavorite = favorites.any {
                            it.departureCode == departureAirport.iata_code &&
                                    it.destinationCode == destinationAirport.iata_code
                        }
                        Flight(
                            departureCode = departureAirport.iata_code,
                            departureName = departureAirport.name,
                            destinationCode = destinationAirport.iata_code,
                            destinationName = destinationAirport.name,
                            isFavorite = isFavorite
                        )
                    }
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    init {
        viewModelScope.launch {
            val initialQuery = userPreferencesRepository.userPreferencesFlow.first().searchValues
            _searchQuery.value = initialQuery
            if (initialQuery.isNotEmpty()) {
                try {
                    val airport = flightRepository.getAirportByCode(initialQuery)
                    _selectedAirportCode.value = airport.iata_code
                } catch (e: Exception) {
                    _selectedAirportCode.value = null
                }
            }
        }
    }

    fun onQueryChange(query: String) {
        _searchQuery.value = query
        _selectedAirportCode.value = null
        viewModelScope.launch {
            userPreferencesRepository.updateUserPreferences(query)
        }
    }

    fun onAirportSelected(airport: Airport) {
        _searchQuery.value = airport.iata_code
        _selectedAirportCode.value = airport.iata_code
        viewModelScope.launch {
            userPreferencesRepository.updateUserPreferences(airport.iata_code)
        }
    }

    fun toggleFavorite(flight: Flight) {
        Log.d("FlightToggle", "Toggling: ${flight.departureCode} -> ${flight.destinationCode}")
        viewModelScope.launch {
            try {
                val existingFavorite = flightRepository.getSingleFavorite(
                    flight.departureCode,
                    flight.destinationCode
                )
                if (existingFavorite == null) {
                    Log.d("FlightToggle", "Favorite not found. Inserting...")
                    val newFavorite = Favorite(
                        departureCode = flight.departureCode,
                        destinationCode = flight.destinationCode
                    )
                    flightRepository.insertFavoriteFlight(newFavorite)
                    Log.d("FlightToggle", "Insert complete.")
                } else {
                    Log.d("FlightToggle", "Favorite found (id: ${existingFavorite.id}). Deleting...")
                    flightRepository.deleteFavoriteFlight(existingFavorite)
                    Log.d("FlightToggle", "Delete complete.")
                }
            } catch (e: Exception) {
                Log.e("FlightToggle", "CRITICAL ERROR during toggle: ${e.message}", e)
            }
        }
    }

    private fun mapFavoritesToFlights(favorites: List<Favorite>): Flow<List<Flight>> {
        return kotlinx.coroutines.flow.flow {
            val flightList = favorites.mapNotNull { fav ->
                try {
                    val departure = flightRepository.getAirportByCode(fav.departureCode)
                    val destination = flightRepository.getAirportByCode(fav.destinationCode)
                    Flight(
                        id = fav.id,
                        departureCode = departure.iata_code,
                        departureName = departure.name,
                        destinationCode = destination.iata_code,
                        destinationName = destination.name,
                        isFavorite = true
                    )
                } catch (e: Exception) {
                    null
                }
            }
            emit(flightList)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FlightSearchApplication)
                val flightRepository = application.appContainer.flightRepository
                val userPreferencesRepository = UserPreferencesRepository.create(application.applicationContext)
                FlightViewModel(flightRepository, userPreferencesRepository)
            }
        }
    }
}
