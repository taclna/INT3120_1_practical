package com.example.unit6_pathway3_flightsearch.data


import kotlinx.coroutines.flow.Flow
import com.example.unit6_pathway3_flightsearch.data.model.Airport
import com.example.unit6_pathway3_flightsearch.data.model.Favorite

class OfflineFlightRepository(
    private val airportDao: FlightDao
) : FlightRepository {
    override suspend fun getAllAirports(): List<Airport> {
        return airportDao.getAllAirports()
    }

    override fun getAllAirportsFlow(): Flow<List<Airport>> {
        return airportDao.getAllAirportsFlow()
    }

    override suspend fun deleteFavoriteFlight(flight: Favorite) {
        return airportDao.deleteFavoriteFlight(flight)
    }

    override fun getAirportFlowByCode(code: String): Flow<Airport> {
        return airportDao.getAirportFlowByCode(code)
    }

    override suspend fun getAirportByCode(code: String): Airport {
        return airportDao.getAirportByCode(code)
    }

    override suspend fun getAirportById(id: Int): Airport {
        return airportDao.getAirportById(id)
    }

    override suspend fun getAllAirports(query: String): List<Airport> {
        return airportDao.getAllAirports(query)
    }

    override fun getAllAirportsFlow(query: String): Flow<List<Airport>> {
        return airportDao.getAllAirportsFlow(query)
    }

    override suspend fun insertFavoriteFlight(flight: Favorite) {
        return airportDao.insertFavoriteFlight(flight)
    }

    override suspend fun getAllFavoriteFlights(): List<Favorite> {
        return airportDao.getAllFavorites()
    }

    override fun getAllFavoriteFlightsFlow(): Flow<List<Favorite>> {
        return airportDao.getAllFavoritesFlow()
    }

    override suspend fun getSingleFavorite(
        departureCode: String,
        destinationCode: String
    ): Favorite? {
        return airportDao.getSingleFavorite(departureCode, destinationCode)
    }
}