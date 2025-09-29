package com.example.unit4_pathway3_mycity

import androidx.lifecycle.ViewModel
import com.example.unit4_pathway3_mycity.data.Place
import com.example.unit4_pathway3_mycity.data.PlaceRepository

class CityViewModel : ViewModel() {
    val categories = PlaceRepository.categories
    val places = PlaceRepository.places

    fun getPlacesByCategory(category: String): List<Place> {
        return places.filter { it.category == category }
    }

    fun getPlaceById(id: Int): Place? {
        return places.find { it.id == id }
    }
}
