package com.example.weathercompose.domain.models

sealed class SearchWeatherResult<T> {
    data class Data<T>(val value: T) : SearchWeatherResult<T>()
    data class Error<T>(val message: String) : SearchWeatherResult<T>()
}
