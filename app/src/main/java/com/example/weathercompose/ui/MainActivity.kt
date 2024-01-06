package com.example.weathercompose.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.weathercompose.R
import com.example.weathercompose.ui.models.ScreenState
import com.example.weathercompose.ui.screens.mainCard
import com.example.weathercompose.ui.screens.tabLayout
import com.example.weathercompose.ui.viewmodel.WeatherViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val weatherViewModel by viewModel<WeatherViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val city = " Voronezh"
        setContent {
            var dialogShowAlert by remember {
                mutableStateOf(false)
            }
            val apiKey = weatherViewModel.getApi()
            if (apiKey == "-1") {
                dialogShowAlert = true
            }
            dialogShowAlert =
                showAlertDialog(dialogShow = dialogShowAlert, context = this, vm = weatherViewModel)
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.background_app),
                contentDescription = "image_back",
                contentScale = ContentScale.FillBounds,
                alpha = 0.5f
            )
           if (!dialogShowAlert) {
               var state by  remember {
                   mutableStateOf<ScreenState>(ScreenState.ErrorState(""))
               }
               weatherViewModel.getWeather(city)
               weatherViewModel.currentState.observe(this){
                  state = it
               }
               Column {
                   mainCard(city, state, weatherViewModel)
                   tabLayout()
               }
           }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun showAlertDialog(dialogShow: Boolean, context: Context, vm: WeatherViewModel): Boolean {
    var openDialog by remember { mutableStateOf(dialogShow) }
    var text by remember { mutableStateOf("") }
    if (openDialog) {
        AlertDialog(
            onDismissRequest = {
                openDialog = false
            },
            title = {
                Text(text = context.getString(R.string.set_api))
            },
            text = {
                Column {
                    TextField(
                        value = text,
                        onValueChange = { text = it }
                    )
                }
            },
            confirmButton = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            if (text == "") text = "-1"
                            vm.saveApi(text)
                            openDialog = false
                        }
                    ) {
                        Text(context.getString(R.string.ok))
                    }
                }
            }
        )
    }
    return openDialog
}
