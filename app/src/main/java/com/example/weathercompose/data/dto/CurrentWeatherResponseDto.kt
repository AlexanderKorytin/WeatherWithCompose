package com.example.weathercompose.data.dto

import com.example.weathercompose.domain.models.Current

data class CurrentWeatherResponseDto(
    val current: CurrentDto
) : Response()


fun CurrentDto.toCurrent(): Current {
    return Current(
        this.cloud,
        this.condition.icon,
        this.condition.text,
        this.feelslike_c,
        this.pressure_mb,
        this.temp_c,
        this.wind_kph
    )
}