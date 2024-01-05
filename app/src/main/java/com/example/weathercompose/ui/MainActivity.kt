package com.example.weathercompose.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.weathercompose.R
import com.example.weathercompose.ui.models.ScreenState
import com.example.weathercompose.ui.theme.WeatherComposeTheme
import com.example.weathercompose.ui.viewmodel.WeatherViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val weatherViewModel by viewModel<WeatherViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var dialogShowAlert by remember {
                mutableStateOf(false)
            }
            var message by remember {
                mutableStateOf("")
            }
            var apiKey = weatherViewModel.getApi()
            if (apiKey == "-1") {
                dialogShowAlert = true
            }
            WeatherComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (dialogShowAlert) {
                        dialogShowAlert = showAlertDialog(dialogShowAlert, this, weatherViewModel)
                    } else {
                        showResult(this, weatherViewModel, this)
                    }
                }
            }
        }
    }

    fun getWeather(name: String) {
        weatherViewModel.getWeather(name)
    }

    companion object {
        private const val KEY = "key"
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun showResult(context: Context, vm: WeatherViewModel, live: LifecycleOwner) {
    var icon by remember {
        mutableStateOf("")
    }
    var state by remember {
        mutableStateOf("")
    }
    var showAlert by remember {
        mutableStateOf(false)
    }
    var text by remember { mutableStateOf("") }
    var resultIsEmpty by remember {
        mutableStateOf(true)
    }
    if (showAlert) {
        showAlert = showAlertDialog(dialogShow = showAlert, context = context, vm = vm)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth()) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp),
                value = text,
                placeholder = { Text(text = "Enter city name") },
                label = { Text(text = "city name") },
                singleLine = true,
                onValueChange = { text = it },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = "search"
                    )
                }
            )
        }
        if (!resultIsEmpty) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                GlideImage(
                    model = "https://cdn.weatherapi.com/weather/64x64/day/116.png",
                    contentDescription = "load image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp),
                )

                Text(
                    text = "${state}", fontSize = 16.sp
                )
            }
        }
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
            Button(
                onClick = {
                    showAlert = true
                }, modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
            ) {
                Text(text = context.getString(R.string.log_in), fontSize = 16.sp)
            }
            Button(
                onClick = {
                    vm.getWeather(text)
                    vm.currentState.observe(live) {
                        when (it) {
                            is ScreenState.ErrorState -> state = "Error: ${it.message}"
                            is ScreenState.ContentState -> {
                                icon = it.result.icon.substring(2, it.result.icon.length)
                                state = """
                        • Облачность:                      ${it.result.cloud}%
                        • Температура (градусы) : ${it.result.temp_c} град. С
                        • Ощущается как:                 ${it.result.feelslike_c} град. С
                        • Давление:                           ${(it.result.precip_mm)} мм
                        • Видимость:                         ${(it.result.wind_kph)} км
                    """.trimIndent()
                            }
                        }
                        resultIsEmpty = false
                    }
                }, modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
            ) {
                Text(text = context.getString(R.string.refresh), fontSize = 16.sp)
            }
        }

    }
}