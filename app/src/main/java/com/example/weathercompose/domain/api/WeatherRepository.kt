package com.example.weathercompose.domain.api

import com.example.weathercompose.domain.models.CurrentWeather
import com.example.weathercompose.domain.models.SearchWeatherResult
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getCurrentWeather(citiy: String): Flow<SearchWeatherResult<CurrentWeather>>

    fun saveApiRKy(api: String)

    fun getApiKey(): String
}