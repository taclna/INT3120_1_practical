package com.example.unit5_pathway2_bookshelf.network


import com.example.unit5_pathway2_bookshelf.model.BooksResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApiService {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int = 40
    ): BooksResponse
}
