package com.example.unit5_pathway2_bookshelf

import com.example.unit5_pathway2_bookshelf.data.AppContainer

import android.app.Application

class BookshelfApplication : Application() {
    val container: AppContainer by lazy { AppContainer() }
}
