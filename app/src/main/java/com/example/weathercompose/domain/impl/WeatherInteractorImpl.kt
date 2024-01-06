package com.example.weathercompose.domain.impl

import com.example.weathercompose.domain.api.WeatherInteractor
import com.example.weathercompose.domain.api.WeatherRepository
import com.example.weathercompose.domain.models.CurrentWeather
import com.example.weathercompose.domain.models.SearchWeatherResult
import kotlinx.coroutines.flow.Flow

class WeatherInteractorImpl(private val repository: WeatherRepository) : WeatherInteractor {
    override suspend fun getWeather(city: String): Flow<SearchWeatherResult<CurrentWeather>> {
        return repository.getCurrentWeather(city)
    }

    override fun saveApiRKy(api: String) {
        repository.saveApiRKy(api)
    }

    override fun getApiKey(): String {
        return repository.getApiKey()
    }
}