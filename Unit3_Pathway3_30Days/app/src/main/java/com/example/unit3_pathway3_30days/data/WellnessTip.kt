package com.example.unit3_pathway3_30days.data

import androidx.annotation.DrawableRes

data class WellnessTip(
    val day: Int,
    val title: String,
    val description: String,
    @DrawableRes val imageRes: Int
)
