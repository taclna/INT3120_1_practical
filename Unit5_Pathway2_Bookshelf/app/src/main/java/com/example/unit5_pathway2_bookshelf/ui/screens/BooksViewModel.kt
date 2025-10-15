package com.example.unit5_pathway2_bookshelf.ui.screens


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.unit5_pathway2_bookshelf.BookshelfApplication
import com.example.unit5_pathway2_bookshelf.data.BooksRepository
import com.example.unit5_pathway2_bookshelf.model.Book

sealed interface BooksUiState {
    data class Success(val books: List<Book>) : BooksUiState
    object Error : BooksUiState
    object Loading : BooksUiState
}

class BooksViewModel(private val booksRepository: BooksRepository) : ViewModel() {
    var booksUiState: BooksUiState by mutableStateOf(BooksUiState.Loading)
        private set

    init {
        loadBooks()
    }

    fun loadBooks() {
        viewModelScope.launch {
            booksUiState = BooksUiState.Loading
            booksUiState = try {
                BooksUiState.Success(booksRepository.fetchBooks("jazz+history"))
            } catch (e: IOException) {
                BooksUiState.Error
            } catch (e: HttpException) {
                BooksUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as BookshelfApplication
                val repository = application.container.booksRepository
                BooksViewModel(booksRepository = repository)
            }
        }
    }
}
