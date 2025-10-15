package com.example.unit5_pathway2_bookshelf.data

import com.example.unit5_pathway2_bookshelf.network.BooksApiService
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.Retrofit

class AppContainer {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.googleapis.com/books/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service: BooksApiService = retrofit.create(BooksApiService::class.java)
    val booksRepository = BooksRepository(service)
}
