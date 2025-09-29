package com.example.unit4_pathway3_mycity.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(categories: List<String>, onCategoryClick: (String) -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("My City") }) }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            items(categories.size) { index ->
                val category = categories[index]
                Text(
                    text = category,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable { onCategoryClick(category) }
                )
                Divider()
            }
        }
    }
}
