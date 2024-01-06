package com.example.weathercompose.ui.models

import com.example.weathercompose.domain.models.CurrentWeather

sealed interface ScreenState {
    data class ErrorState(val message: String) : ScreenState
    data class ContentState(val result: CurrentWeather) : ScreenState
}