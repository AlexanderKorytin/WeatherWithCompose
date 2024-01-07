package com.example.weathercompose.ui.screens.main

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.weathercompose.R
import com.example.weathercompose.ui.MainActivity
import com.example.weathercompose.ui.screens.alertdialogs.showAlertDialogEnterCityName
import com.example.weathercompose.ui.theme.MyLightGray
import com.example.weathercompose.ui.viewmodel.WeatherViewModel
import kotlinx.coroutines.async

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun mainCardError(
    message: String,
    viewmodel: WeatherViewModel,
    context: Context,
) {
    val coroutineScope = rememberCoroutineScope()
    var showFlag by remember {
        mutableStateOf(false)
    }
    if (showFlag) {
        showFlag = showAlertDialogEnterCityName(
            dialogShow = showFlag,
            context = context,
            viewModel = viewmodel
        )
    }

    Column(
        modifier = Modifier
            .padding(8.dp),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MyLightGray)
                .alpha(alfa),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = message,
                    style = TextStyle(fontSize = 45.sp, color = Color.Black),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp, start = 5.dp, end = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = {
                            showFlag = true
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.search),
                            contentDescription = "image_search",
                            tint = Color.Black
                        )
                    }

                    IconButton(
                        onClick = {
                            coroutineScope.async {
                                (context.getActivity() as MainActivity).checkPermissionLocation()
                                viewmodel.getWeather(MainActivity.coordinates)
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.cloud_sync),
                            contentDescription = "image_sync",
                            tint = Color.Black
                        )
                    }
                }
            }
        }
    }
}
