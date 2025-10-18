package com.example.unit6_pathway3_flightsearch.ui.screens.flight

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unit6_pathway3_flightsearch.data.FlightRepository
import com.example.unit6_pathway3_flightsearch.data.UserPreferencesRepository
import com.example.unit6_pathway3_flightsearch.data.model.Airport
import com.example.unit6_pathway3_flightsearch.data.model.Favorite
import com.example.unit6_pathway3_flightsearch.ui.screens.search.AirportSuggestion
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FlightViewModel(
    private val repository: FlightRepository,
    private val userPrefRepo: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FlightUiState(isLoading = true))
    val uiState: StateFlow<FlightUiState> = _uiState.asStateFlow()

    private val _query = MutableStateFlow("")

    init {
        viewModelScope.launch {
            userPrefRepo.userPreferencesFlow
                .map { it.searchValues }
                .catch { emit("") }
                .collect { saved -> _query.value = saved ?: "" }
        }

        val suggestionsFlow = _query
            .debounce(200)
            .distinctUntilChanged()
            .flatMapLatest { q ->
                if (q.isBlank()) {
                    flowOf(emptyList<Airport>())
                } else {
                    repository.getAllAirportsFlow(q)
                }
            }

        val allAirportsFlow = repository.getAllAirportsFlow()

        val favoritesFlow = repository.getAllFavoriteFlightsFlow()

        combine(suggestionsFlow, allAirportsFlow, favoritesFlow) { suggestions, allAirports, favorites ->
            Triple(suggestions, allAirports, favorites)
        }.map { (suggestions, allAirports, favorites) ->
            val q = _query.value
            val suggestionModels = suggestions.map { AirportSuggestion(it.iata_code, it.name) }
            val airportMap = allAirports.associateBy { it.iata_code }
            val flights = if (q.isBlank()) {
                favorites.map { fav ->
                    val depart = airportMap[fav.departureCode]
                    val dest = airportMap[fav.destinationCode]
                    FlightItemUiModel(
                        id = fav.id,
                        departCode = fav.departureCode,
                        departName = depart?.name ?: "",
                        arriveCode = fav.destinationCode,
                        arriveName = dest?.name ?: "",
                        isFavorite = true
                    )
                }
            } else {
                val departAirport = suggestions.find { it.iata_code.equals(q, ignoreCase = true) } ?: airportMap[q]
                if (departAirport != null) {
                    allAirports.filter { it.iata_code != departAirport.iata_code }.map { a ->
                        val isFav = favorites.any { f -> f.departureCode == departAirport.iata_code && f.destinationCode == a.iata_code }
                        FlightItemUiModel(
                            id = 0,
                            departCode = departAirport.iata_code,
                            departName = departAirport.name,
                            arriveCode = a.iata_code,
                            arriveName = a.name,
                            isFavorite = isFav
                        )
                    }
                } else {
                    emptyList()
                }
            }
            FlightUiState(
                searchQuery = q,
                suggestions = suggestionModels,
                flights = flights,
                isLoading = false
            )
        }.onEach { state -> _uiState.value = state }.launchIn(viewModelScope)
    }

    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
        viewModelScope.launch { userPrefRepo.updateUserPreferences(newQuery) }
        _uiState.update { it.copy(isLoading = true) }
    }

    fun toggleFavorite(departCode: String, arriveCode: String) {
        viewModelScope.launch {
            try {
                val single = repository.getSingleFavorite(departCode, arriveCode)
                repository.deleteFavoriteFlight(single)
            } catch (e: Exception) {
                val fav = Favorite(departureCode = departCode, destinationCode = arriveCode)
                repository.insertFavoriteFlight(fav)
            }
        }
    }

    fun onSuggestionSelected(iataCode: String) {
        onQueryChanged(iataCode)
    }
}
