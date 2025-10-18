package com.example.unit6_pathway3_flightsearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unit6_pathway3_flightsearch.ui.screens.flight.FlightScreen
import com.example.unit6_pathway3_flightsearch.ui.screens.flight.FlightViewModel
import com.example.unit6_pathway3_flightsearch.ui.theme.Unit6_Pathway3_FlightSearchTheme

class MainActivity : ComponentActivity() {

    private val app by lazy { application as FlightSearchApplication }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val flightRepository = app.appContainer.flightRepository
        val userPrefRepo = com.example.unit6_pathway3_flightsearch.data.UserPreferencesRepository.create(this)

        val factory = FlightViewModelFactory(
            flightRepository,
            userPrefRepo
        )

        setContent {
            Unit6_Pathway3_FlightSearchTheme {
                val vm: FlightViewModel = viewModel(factory = factory)
                FlightScreen(viewModel = vm)
            }
        }
    }
}

