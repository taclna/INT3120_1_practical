package com.example.unit4_pathway3_mycity.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.unit4_pathway3_mycity.data.Place

@Composable
fun PlaceCard(place: Place, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Column {
            Image(
                painter = painterResource(id = place.imageRes),
                contentDescription = place.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(160.dp).fillMaxWidth()
            )
            Text(place.name, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(8.dp))
            Text(place.description, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(horizontal = 8.dp))
        }
    }
}
