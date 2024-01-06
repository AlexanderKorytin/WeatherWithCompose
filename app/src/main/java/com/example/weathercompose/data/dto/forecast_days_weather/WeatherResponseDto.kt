package com.example.weathercompose.data.dto.forecast_days_weather

import com.example.weathercompose.data.dto.Response
import com.example.weathercompose.domain.models.CurrentWeather
import com.example.weathercompose.domain.models.Forecastday

data class WeatherResponseDto(
    val current: Current,
    val forecast: Forecast,
    val location: Location
): Response()

fun WeatherResponseDto.toCurrent(): CurrentWeather {
    val forecastday = mutableListOf<Forecastday>()
    forecastday.addAll(this.forecast.forecastday)
    return CurrentWeather(
        this.current.condition.icon,
        this.current.condition.text,
        this.current.temp_c,
        this.location.country,
        this.location.name,
        this.current.last_updated,
        forecastday = forecastday
    )
}