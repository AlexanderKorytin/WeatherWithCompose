package com.example.weathercompose.data

import com.example.weathercompose.data.dto.Response

interface NetWorkClient {

    suspend fun getCurrentWeather(city: String, api: String): Response?
}