package com.example.unit6_pathway3_flightsearch.ui.screens.flight

import com.example.unit6_pathway3_flightsearch.ui.screens.search.AirportSuggestion

data class FlightUiState(
    val searchQuery: String = "",
    val suggestions: List<AirportSuggestion> = emptyList(),
    val flights: List<FlightItemUiModel> = emptyList(),
    val isLoading: Boolean = false
)

data class FlightItemUiModel(
    val id: Int = 0,
    val departCode: String,
    val departName: String,
    val arriveCode: String,
    val arriveName: String,
    val isFavorite: Boolean = false
)
