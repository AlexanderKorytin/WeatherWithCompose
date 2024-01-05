package com.example.weathercompose.di

import com.example.weathercompose.data.network.WeatherRepositoryImpl
import com.example.weathercompose.domain.api.WeatherInteractor
import com.example.weathercompose.domain.api.WeatherRepository
import com.example.weathercompose.domain.impl.WeatherInteractorImpl
import org.koin.dsl.module

val domainModule = module {

    single<WeatherRepository> {
        WeatherRepositoryImpl(client = get(), sharedPreferences = get())
    }

    factory<WeatherInteractor> {
        WeatherInteractorImpl(repository = get())
    }
}