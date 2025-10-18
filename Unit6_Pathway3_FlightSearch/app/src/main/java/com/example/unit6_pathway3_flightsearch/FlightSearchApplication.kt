package com.example.unit6_pathway3_flightsearch

import android.app.Application
import com.example.unit6_pathway3_flightsearch.data.AppContainer
import com.example.unit6_pathway3_flightsearch.data.AppDataContainer

class FlightSearchApplication : Application() {
    val appContainer: AppContainer by lazy { AppDataContainer(this) }
}
