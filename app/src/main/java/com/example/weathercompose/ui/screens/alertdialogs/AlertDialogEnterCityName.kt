package com.example.weathercompose.ui.screens.alertdialogs

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weathercompose.R
import com.example.weathercompose.ui.MainActivity
import com.example.weathercompose.ui.viewmodel.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun showAlertDialogEnterCityName(
    dialogShow: Boolean,
    context: Context,
    viewModel: WeatherViewModel
): Boolean {
    var dialog by remember { mutableStateOf(dialogShow) }
    var name by remember { mutableStateOf("") }
    if (dialog) {
        AlertDialog(
            onDismissRequest = {

            },
            title = {
                Text(text = context.getString(R.string.set_city))
            },
            text = {
                Column {
                    TextField(
                        value = name,
                        onValueChange = { name = it },
                        singleLine = true,
                        placeholder = {
                            Text(text = context.getString(R.string.set_city))
                        },
                        label = {
                            Text(text = "city name")
                        },
                        shape = RoundedCornerShape(8.dp)
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
                            MainActivity.coordinates = name
                            viewModel.getWeather(name)
                            dialog = false
                        }
                    ) {
                        Text(context.getString(R.string.ok))
                    }
                }
            }
        )
    }
    return dialog
}
