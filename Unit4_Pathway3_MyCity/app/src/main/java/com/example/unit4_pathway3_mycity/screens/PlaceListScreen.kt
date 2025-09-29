package com.example.unit4_pathway3_mycity.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.example.unit4_pathway3_mycity.data.Place
import com.example.unit4_pathway3_mycity.ui.components.PlaceCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceListScreen(
    places: List<Place>,
    onPlaceClick: (Int) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Danh sách địa điểm") },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            items(places.size) { index ->
                PlaceCard(place = places[index]) {
                    onPlaceClick(places[index].id)
                }
            }
        }
    }
}

