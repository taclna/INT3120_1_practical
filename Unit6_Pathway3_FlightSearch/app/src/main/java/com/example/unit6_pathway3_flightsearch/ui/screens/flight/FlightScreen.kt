package com.example.unit6_pathway3_flightsearch.ui.screens.flight

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import com.example.unit6_pathway3_flightsearch.ui.screens.search.AirportSuggestion
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightScreen(
    viewModel: FlightViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Flight Search") }) },
        modifier = modifier
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            SearchBar(
                value = state.searchQuery,
                onValueChange = { viewModel.onQueryChanged(it) },
                onClear = { viewModel.onQueryChanged("") },
                suggestions = state.suggestions,
                onSuggestionSelected = { viewModel.onSuggestionSelected(it) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = if (state.searchQuery.isBlank()) "Saved flights" else "Flights from ${state.searchQuery}",
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )

            FlightList(
                flights = state.flights,
                onToggleFavorite = { depart, arrive -> viewModel.toggleFavorite(depart, arrive) }
            )
        }
    }
}

@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    onClear: () -> Unit,
    suggestions: List<AirportSuggestion>,
    onSuggestionSelected: (String) -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {

        Surface(
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "search")
                Spacer(modifier = Modifier.width(8.dp))
                TextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Search airport or IATA") },
                    singleLine = true,
                )
            }
        }

        if (suggestions.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                suggestions.take(6).forEach { s ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSuggestionSelected(s.iataCode) }
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = s.iataCode, fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 12.dp))
                        Text(text = s.name, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                }
            }
        }
    }
}


@Composable
fun FlightList(
    flights: List<FlightItemUiModel>,
    onToggleFavorite: (String, String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(flights) { item ->
            FlightCard(item = item, onToggleFavorite = onToggleFavorite)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun FlightCard(item: FlightItemUiModel, onToggleFavorite: (String, String) -> Unit) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "DEPART")
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(text = item.departCode, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = item.departName, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "ARRIVE")
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(text = item.arriveCode, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = item.arriveName, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }

            IconButton(onClick = { onToggleFavorite(item.departCode, item.arriveCode) }) {
                if (item.isFavorite) {
                    Icon(imageVector = Icons.Filled.Star, contentDescription = "favorite")
                } else {
                    Icon(imageVector = Icons.Outlined.Star, contentDescription = "not favorite")
                }
            }
        }
    }
}
