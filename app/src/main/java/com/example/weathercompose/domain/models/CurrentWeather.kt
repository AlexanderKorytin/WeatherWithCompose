package com.example.weathercompose.domain.models

data class CurrentWeather(
    val icon: String,
    val text: String,
    val temp_c: Double,
    val country: String,
    val name: String,
    val dateTime: String,
    val forecastday: List<Forecastday>
)
