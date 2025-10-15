package com.example.unit5_pathway2_bookshelf


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.unit5_pathway2_bookshelf.ui.BookshelfApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookshelfApp()
        }
    }
}

