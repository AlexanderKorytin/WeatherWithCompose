package com.example.weathercompose.di

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.weathercompose.data.NetWorkClient
import com.example.weathercompose.data.network.RetrofitNetWorkClient
import com.example.weathercompose.data.network.WeatherApi
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_KEY = "api_key"

val dataModule = module {

    single<WeatherApi> {
        Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }
    factory<NetWorkClient> {
        RetrofitNetWorkClient(context = androidContext(), api = get())
    }

    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            API_KEY,
            AppCompatActivity.MODE_PRIVATE
        )
    }
}