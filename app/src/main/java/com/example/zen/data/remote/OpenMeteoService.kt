package com.example.zen.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

data class OpenMeteoCurrentWeatherResponse(
    val current: CurrentWeather? = null
)

data class CurrentWeather(
    val temperature_2m: Double? = null,
    val weather_code: Int? = null
)

interface OpenMeteoService {
    @GET("v1/forecast")
    suspend fun getCurrentWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String = "temperature_2m,weather_code"
    ): OpenMeteoCurrentWeatherResponse
}

