package com.example.unit4_pathway3_mycity.data

import androidx.annotation.DrawableRes

data class Place(
    val id: Int,
    val name: String,
    val description: String,
    @DrawableRes val imageRes: Int,
    val category: String
)
