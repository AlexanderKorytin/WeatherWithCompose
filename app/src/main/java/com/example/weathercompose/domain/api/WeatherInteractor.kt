package com.example.weathercompose.domain.api

import com.example.weathercompose.domain.models.Current
import com.example.weathercompose.domain.models.SearchWeatherResult
import kotlinx.coroutines.flow.Flow


interface WeatherInteractor {

    suspend fun getWeather(city: String): Flow<SearchWeatherResult<Current>>

    fun saveApiRKy(api: String)

    fun getApiKey(): String

}