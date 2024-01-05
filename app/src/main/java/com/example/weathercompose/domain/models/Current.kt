package com.example.weathercompose.domain.models

data class Current(
    val cloud: Int,
    val icon: String,
    val text: String,
    val feelslike_c: Double,
    val precip_mm: Double,
    val temp_c: Double,
)
