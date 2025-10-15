package com.example.unit5_pathway2_bookshelf.data


import com.example.unit5_pathway2_bookshelf.model.Book
import com.example.unit5_pathway2_bookshelf.network.BooksApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BooksRepository(private val service: BooksApiService) {
    suspend fun fetchBooks(query: String): List<Book> = withContext(Dispatchers.IO) {
        try {
            val resp = service.searchBooks(query)
            val items = resp.items ?: emptyList()
            items.mapNotNull { item ->
                val id = item.id ?: return@mapNotNull null
                val title = item.volumeInfo?.title
                var url = item.volumeInfo?.imageLinks?.thumbnail ?: item.volumeInfo?.imageLinks?.smallThumbnail
                url = url?.replace("http://", "https://")
                Book(id = id, title = title, imageUrl = url)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
