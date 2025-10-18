package com.example.unit6_pathway3_flightsearch.ui.screens.search

data class SearchUiState(
    val query: String = "",
    val suggestions: List<AirportSuggestion> = emptyList(),
    val isLoading: Boolean = false
)

data class AirportSuggestion(
    val iataCode: String,
    val name: String
)
