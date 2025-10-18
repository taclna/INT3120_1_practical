package com.example.unit6_pathway3_flightsearch


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.unit6_pathway3_flightsearch.data.FlightRepository
import com.example.unit6_pathway3_flightsearch.data.UserPreferencesRepository
import com.example.unit6_pathway3_flightsearch.ui.screens.flight.FlightViewModel

class FlightViewModelFactory(
    private val flightRepository: FlightRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlightViewModel::class.java)) {
            return FlightViewModel(flightRepository, userPreferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
