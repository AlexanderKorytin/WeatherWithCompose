package com.example.weathercompose.domain.models

import com.example.weathercompose.data.dto.forecast_days_weather.Astro

data class Forecastday(
    val astro: Astro,
    val date: String,
    val date_epoch: Int,
    val day: Day,
    val hour: List<Hour>
)