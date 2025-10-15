package com.example.unit5_pathway2_bookshelf.ui.screens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfScreen(
    viewModel: BooksViewModel = viewModel(factory = BooksViewModel.Factory)
) {
    val uiState = viewModel.booksUiState

    Column(modifier = Modifier.fillMaxSize()) {
        Surface(color = Color(0xFF8A362A), modifier = Modifier.fillMaxWidth()) {
            CenterAlignedTopAppBar(
                title = { Text("Bookshelf", color = Color.White) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFF8A362A))
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
        ) {
            when (uiState) {
                is BooksUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is BooksUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Error loading books", color = Color.Gray)
                    }
                }

                is BooksUiState.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.books) { book ->
                            BookCoverItem(book.imageUrl, book.title)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookCoverItem(imageUrl: String?, title: String?) {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .aspectRatio(0.65f),
        color = Color.White,
        border = BorderStroke(4.dp, Color.White),
        shape = RoundedCornerShape(4.dp),
        shadowElevation = 2.dp
    ) {
        if (imageUrl != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = title ?: "",
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No image", color = Color.DarkGray)
            }
        }
    }
}
