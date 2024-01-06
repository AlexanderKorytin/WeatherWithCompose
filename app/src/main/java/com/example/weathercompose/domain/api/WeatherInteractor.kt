package com.example.weathercompose.domain.api

import com.example.weathercompose.domain.models.CurrentWeather
import com.example.weathercompose.domain.models.SearchWeatherResult
import kotlinx.coroutines.flow.Flow


interface WeatherInteractor {

    suspend fun getWeather(city: String): Flow<SearchWeatherResult<CurrentWeather>>

    fun saveApiRKy(api: String)

    fun getApiKey(): String

}