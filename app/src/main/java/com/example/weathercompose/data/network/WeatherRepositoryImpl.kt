package com.example.weathercompose.data.network

import android.content.SharedPreferences
import com.example.weathercompose.data.NetWorkClient
import com.example.weathercompose.data.dto.CurrentWeatherResponseDto
import com.example.weathercompose.data.dto.toCurrent
import com.example.weathercompose.domain.api.WeatherRepository
import com.example.weathercompose.domain.models.Current
import com.example.weathercompose.domain.models.SearchWeatherResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeatherRepositoryImpl(
    private val client: NetWorkClient,
    private val sharedPreferences: SharedPreferences,
) : WeatherRepository {


    override suspend fun getCurrentWeather(city: String): Flow<SearchWeatherResult<Current>> =
        flow {
            val apiKey = getApiKey()
            val response = client.getCurrentWeather(city, apiKey)
            when (response?.resultCode) {
                -1 -> {
                    emit(SearchWeatherResult.Error("No Internet"))
                }

                200 -> {
                    val currentWeather = (response as CurrentWeatherResponseDto).current.toCurrent()
                    emit(SearchWeatherResult.Data(value = currentWeather))
                }

                else -> {
                    emit(SearchWeatherResult.Error("Server Error"))
                }
            }
        }

    override fun saveApiRKy(api: String) {
        sharedPreferences.edit().putString(KEY, api).apply()
    }

    override fun getApiKey(): String {
        return sharedPreferences.getString(KEY, "-1") ?: "-1"
    }

    companion object {
        private const val KEY = "key"
    }
}