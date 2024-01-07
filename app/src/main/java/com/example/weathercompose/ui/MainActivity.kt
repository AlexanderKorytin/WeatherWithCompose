package com.example.weathercompose.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.lifecycleScope
import com.example.weathercompose.R
import com.example.weathercompose.ui.models.ScreenState
import com.example.weathercompose.ui.screens.main.mainCardContent
import com.example.weathercompose.ui.screens.main.mainCardError
import com.example.weathercompose.ui.screens.main.mainCardStart
import com.example.weathercompose.ui.screens.alertdialogs.showAlertDialogEnterCityName
import com.example.weathercompose.ui.screens.alertdialogs.showAlertDialogLogIn
import com.example.weathercompose.ui.screens.tab.tabLayout
import com.example.weathercompose.ui.viewmodel.WeatherViewModel
import com.markodevcic.peko.PermissionRequester
import com.markodevcic.peko.PermissionResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val weatherViewModel by viewModel<WeatherViewModel>()
    val requester = PermissionRequester.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            checkPermissionLocation()
        }.onJoin
        setContent {
            var dialogShowAlert by remember {
                mutableStateOf(false)
            }
            val apiKey = weatherViewModel.getApi()
            if (apiKey == "-1") {
                dialogShowAlert = true
            }
            dialogShowAlert =
                showAlertDialogLogIn(
                    dialogShow = dialogShowAlert,
                    context = this,
                    vm = weatherViewModel
                )
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.background_app),
                contentDescription = "image_back",
                contentScale = ContentScale.FillBounds,
                alpha = 0.5f
            )
            if (!dialogShowAlert) {
                if (coordinates != "") {
                    weatherViewModel.getWeather(coordinates)
                } else {
                    showAlertDialogEnterCityName(
                        dialogShow = true,
                        context = this,
                        viewModel = weatherViewModel
                    )
                }
                var state by remember {
                    mutableStateOf<ScreenState>(ScreenState.Start)
                }
                weatherViewModel.currentState.observe(this) {
                    state = it
                }
                Column {
                    when(state){
                        is ScreenState.ContentState ->{
                            mainCardContent((state as ScreenState.ContentState).result, weatherViewModel, this@MainActivity)
                            tabLayout(state)
                        }
                        is ScreenState.Start -> {
                            mainCardStart()
                        }
                        is ScreenState.ErrorState -> {
                            mainCardError((state as ScreenState.ErrorState).message, weatherViewModel, this@MainActivity)
                            tabLayout(state)
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    suspend fun checkPermissionLocation() {
        requester.request(
            Manifest.permission.ACCESS_FINE_LOCATION
        ).collect { result ->
            when (result) {
                is PermissionResult.Granted -> {
                    val locationManager =
                        getSystemService(Context.LOCATION_SERVICE) as LocationManager

                    val hasNetwork =
                        locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

                    val lastKnownLocationByNetwork =
                        locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    lastKnownLocationByNetwork?.let {
                        coordinates = "${it.latitude},${it.longitude}"
                    }
                } // Пользователь дал разрешение, можно продолжать работу
                is PermissionResult.Denied -> {} // Пользователь отказал в предоставлении разрешения
                is PermissionResult.Denied.DeniedPermanently -> {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.data = Uri.fromParts("package", this.packageName, null)
                    this.startActivity(intent)
                } // Запрещено навсегда, перезапрашивать нет смысла, предлагаем пройти в настройки
                is PermissionResult.Cancelled -> {
                    return@collect
                } // Запрос на разрешение отменён
            }
        }
    }

    companion object {
        var coordinates = ""
    }
}

