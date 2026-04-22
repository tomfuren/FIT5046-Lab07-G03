package com.example.zen.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Retrofit bootstrap (Assessment 2 skeleton).
 *
 * This is intentionally not used by the UI prototype yet.
 * In Assessment 4, it will be called to fetch weather data from Open-Meteo.
 */
@Suppress("unused")
object RetrofitClient {
    private const val BASE_URL = "https://api.open-meteo.com/"

    val openMeteoService: OpenMeteoService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenMeteoService::class.java)
    }
}

