package com.example.weathercompose.domain.models

import com.example.weathercompose.data.dto.forecast_days_weather.Forecastday

data class CurrentWeather(
    val icon: String,
    val text: String,
    val temp_c: Double,
    val country: String,
    val name: String,
    val dateTime: String,
    val forecastday: List<Forecastday>
)
