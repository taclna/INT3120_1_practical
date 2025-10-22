package com.example.unit6_pathway3_flightsearch.ui.screens.flight

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unit6_pathway3_flightsearch.R
import com.example.unit6_pathway3_flightsearch.data.model.Airport
import com.example.unit6_pathway3_flightsearch.data.Flight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightScreen(
    viewModel: FlightViewModel = viewModel(factory = FlightViewModel.Factory)
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCode by viewModel.selectedAirportCode.collectAsState()
    val suggestions by viewModel.autocompleteSuggestions.collectAsState()
    val favorites by viewModel.favoriteRoutes.collectAsState()
    val destinations by viewModel.destinationFlights.collectAsState()

    val showFavorites = searchQuery.isEmpty()
    val showSuggestions = searchQuery.isNotEmpty() && selectedCode == null
    val showDestinations = selectedCode != null

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Flight Search") })
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            SearchBar(
                query = searchQuery,
                onQueryChange = viewModel::onQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            if (showFavorites) {
                ListTitle(text = "Favorite routes")
                FlightList(
                    flights = favorites,
                    onFavoriteClick = viewModel::toggleFavorite
                )
            }

            if (showSuggestions) {
                AirportSuggestionList(
                    airports = suggestions,
                    onAirportClick = viewModel::onAirportSelected
                )
            }

            if (showDestinations) {
                ListTitle(text = "Flights from $selectedCode")
                FlightList(
                    flights = destinations,
                    onFavoriteClick = viewModel::toggleFavorite
                )
            }
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Enter departure airport") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(Icons.Default.Clear, contentDescription = "Clear")
                }
            } else {
                IconButton(onClick = { /* TODO: Mic action */ }) {
                    Icon(Icons.Default.Mic, contentDescription = "Mic")
                }
            }
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = MaterialTheme.shapes.extraLarge,
        modifier = modifier
    )
}

@Composable
fun ListTitle(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun AirportSuggestionList(
    airports: List<Airport>,
    onAirportClick: (Airport) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(airports, key = { it.id }) { airport ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAirportClick(airport) }
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = airport.iata_code,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(end = 16.dp)
                )
                Text(
                    text = airport.name,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun FlightList(
    flights: List<Flight>,
    onFavoriteClick: (Flight) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(flights, key = { "${it.departureCode}-${it.destinationCode}" }) { flight ->
            FlightRow(
                flight = flight,
                onFavoriteClick = { onFavoriteClick(flight) }
            )
        }
    }
}

@Composable
fun FlightRow(
    flight: Flight,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "DEPART",
                    style = MaterialTheme.typography.labelSmall
                )
                AirportInfo(code = flight.departureCode, name = flight.departureName)

                Text(
                    text = "ARRIVE",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
                AirportInfo(code = flight.destinationCode, name = flight.destinationName)
            }
            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (flight.isFavorite) Icons.Filled.Star else Icons.Outlined.StarOutline,
                    contentDescription = "Favorite",
                    tint = if (flight.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun AirportInfo(code: String, name: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = code,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}