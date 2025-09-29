package com.example.unit4_pathway3_mycity.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.unit4_pathway3_mycity.data.Place

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetailScreen(place: Place) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(place.name) }) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Image(
                painter = painterResource(id = place.imageRes),
                contentDescription = place.name,
                modifier = Modifier.fillMaxWidth().height(220.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(place.description, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
