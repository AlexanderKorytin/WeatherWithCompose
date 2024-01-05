package com.example.weathercompose.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                    //     getWeather("Voronezh")
                    weatherViewModel.currentState.observe(this) {
                        message = when (it) {
                            is ScreenState.ErrorState -> it.message
                            is ScreenState.ContentState -> it.result.temp_c.toString()
                        }
                    }
                    if (dialogShowAlert){
                       dialogShowAlert = showAlertDialog(dialogShowAlert, this, weatherViewModel)
                    } else {
                        showResult("Voronezh", message, this, weatherViewModel)
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

@Composable
fun showResult(name: String, message: String, context: Context, vm: WeatherViewModel) {
    var state by remember {
        mutableStateOf("")
    }
    var showAlert by remember {
        mutableStateOf(false)
    }
    if (showAlert) {
       showAlert = showAlertDialog(dialogShow = showAlert, context = context, vm = vm)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        if (!showAlert) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Temp in $name = ${state}", fontSize = 16.sp
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
            if (!showAlert) {
                Button(
                    onClick = {
                        state = message
                    }, modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = context.getString(R.string.refresh), fontSize = 16.sp)
                }
            }

        }
    }
}