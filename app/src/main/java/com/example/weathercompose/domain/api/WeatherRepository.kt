package com.example.weathercompose.domain.api

import com.example.weathercompose.domain.models.Current
import com.example.weathercompose.domain.models.SearchWeatherResult
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getCurrentWeather(citiy: String): Flow<SearchWeatherResult<Current>>

    fun saveApiRKy(api: String)

    fun getApiKey(): String
}