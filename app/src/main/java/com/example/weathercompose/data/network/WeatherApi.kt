package com.example.weathercompose.data.network

import com.example.weathercompose.data.dto.forecast_days_weather.WeatherResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("forecast.json")
    suspend fun getCurrentWeatherByCity(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("days") days: Int = 7,
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no",
    ): WeatherResponseDto


}