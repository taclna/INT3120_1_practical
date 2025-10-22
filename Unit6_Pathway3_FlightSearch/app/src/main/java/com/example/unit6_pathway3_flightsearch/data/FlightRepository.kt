package com.example.unit6_pathway3_flightsearch.data


import com.example.unit6_pathway3_flightsearch.data.model.Airport
import com.example.unit6_pathway3_flightsearch.data.model.Favorite
import kotlinx.coroutines.flow.Flow

interface FlightRepository {
    fun getAllAirportsFlow(): Flow<List<Airport>>
    fun getAllAirportsFlow(query: String): Flow<List<Airport>>
    fun getAirportFlowByCode(code: String): Flow<Airport>

    suspend fun getAllAirports(): List<Airport>
    suspend fun getAllAirports(query: String): List<Airport>
    suspend fun getAirportByCode(code: String): Airport

    suspend fun getAirportById(id: Int): Airport

    fun getAllFavoriteFlightsFlow(): Flow<List<Favorite>>
    suspend fun getAllFavoriteFlights(): List<Favorite>
    suspend fun insertFavoriteFlight(flight: Favorite)
    suspend fun deleteFavoriteFlight(flight: Favorite)

    suspend fun getSingleFavorite(departureCode: String, destinationCode: String): Favorite?
}