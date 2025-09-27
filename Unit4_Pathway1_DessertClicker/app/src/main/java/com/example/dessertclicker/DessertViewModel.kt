package com.example.dessertclicker

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


import com.example.dessertclicker.data.Datasource.dessertList
import kotlinx.coroutines.flow.update


class DessertViewModel : ViewModel() {

    private val _dessertState = MutableStateFlow(DessertState())
    val dessertUiState: StateFlow<DessertState> = _dessertState.asStateFlow()

    fun onDessertClicked() {
        _dessertState.update { dessertState ->
            val dessertsSold = dessertState.dessertsSold + 1
            val dessertToShow = determineDessertToShow(dessertsSold)
            dessertState.copy(
                currentDessertIndex = dessertToShow,
                revenue = dessertState.revenue + dessertState.currentDessertPrice,
                dessertsSold = dessertsSold,
                currentDessertImageId = dessertList[dessertToShow].imageId,
                currentDessertPrice = dessertList[dessertToShow].price
            )
        }
    }

    fun determineDessertToShow(dessertsSold: Int): Int {
        var dessertToShow = 0
        for (index in dessertList.indices) {
            if (dessertsSold >= dessertList[index].startProductionAmount) {
                dessertToShow = index
            } else {
                break
            }
        }
        return dessertToShow
    }
}
