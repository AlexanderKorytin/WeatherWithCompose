package com.example.weathercompose.data.network

import android.content.SharedPreferences
import com.example.weathercompose.data.dto.CurrentWeatherResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("current.json")
    suspend fun getWeather(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("aqi") aqi:String = "no",
    ): CurrentWeatherResponseDto
}