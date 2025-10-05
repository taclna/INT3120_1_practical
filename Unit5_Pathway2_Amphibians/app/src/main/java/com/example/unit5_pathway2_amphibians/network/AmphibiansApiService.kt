package com.example.unit5_pathway2_amphibians.network

import com.example.unit5_pathway2_amphibians.model.Amphibian
import retrofit2.http.GET

interface AmphibiansApiService {
    @GET("amphibians")
    suspend fun getAmphibians(): List<Amphibian>
}