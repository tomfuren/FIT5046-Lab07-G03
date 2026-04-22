package com.example.zen.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Minimal Open-Meteo API models (Assessment 2 skeleton).
 *
 * Kept lightweight for the prototype; additional fields can be added in Assessment 4.
 */
@Suppress("unused")
data class OpenMeteoCurrentWeatherResponse(
    val current: CurrentWeather? = null
)

@Suppress("unused")
data class CurrentWeather(
    val temperature_2m: Double? = null,
    val weather_code: Int? = null
)

@Suppress("unused")
interface OpenMeteoService {
    @GET("v1/forecast")
    suspend fun getCurrentWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String = "temperature_2m,weather_code"
    ): OpenMeteoCurrentWeatherResponse
}

