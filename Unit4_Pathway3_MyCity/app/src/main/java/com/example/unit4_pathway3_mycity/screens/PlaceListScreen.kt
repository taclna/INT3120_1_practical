package com.example.unit4_pathway3_mycity.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.example.unit4_pathway3_mycity.data.Place
import com.example.unit4_pathway3_mycity.ui.components.PlaceCard

@Composable
fun PlaceListScreen(places: List<Place>, onPlaceClick: (Int) -> Unit) {
    LazyColumn {
        items(places.size) { index ->
            PlaceCard(place = places[index]) {
                onPlaceClick(places[index].id)
            }
        }
    }
}
