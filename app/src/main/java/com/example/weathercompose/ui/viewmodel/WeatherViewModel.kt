package com.example.weathercompose.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathercompose.domain.api.WeatherInteractor
import com.example.weathercompose.domain.models.SearchWeatherResult
import com.example.weathercompose.ui.models.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val interactor: WeatherInteractor,
) : ViewModel() {

    private var screenState: MutableLiveData<ScreenState> = MutableLiveData()

    val currentState: LiveData<ScreenState> get() = screenState

    fun getApi(): String {
        return interactor.getApiKey()
    }

    fun saveApi(apiKey: String) {
        interactor.saveApiRKy(apiKey)
    }

    fun getWeather(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.getWeather(city).collect {
                if (it is SearchWeatherResult.Error) {
                    screenState.postValue(ScreenState.ErrorState(it.message))
                }
                if (it is SearchWeatherResult.Data) {
                    screenState.postValue(ScreenState.ContentState(result = it.value))
                }
            }
        }
    }
}