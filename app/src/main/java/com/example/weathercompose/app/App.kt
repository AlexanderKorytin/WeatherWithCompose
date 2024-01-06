package com.example.weathercompose.app

import android.app.Application
import com.example.weathercompose.di.dataModule
import com.example.weathercompose.di.domainModule
import com.example.weathercompose.di.viewModelModule
import com.markodevcic.peko.PermissionRequester
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        PermissionRequester.initialize(applicationContext)

        startKoin{
            androidContext(this@App)
            modules(
                dataModule,
                domainModule,
                viewModelModule
            )
        }
    }
}