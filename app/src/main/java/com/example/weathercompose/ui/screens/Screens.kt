package com.example.weathercompose.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.weathercompose.R
import com.example.weathercompose.ui.models.ScreenState
import com.example.weathercompose.ui.theme.MyLightGray
import com.example.weathercompose.ui.viewmodel.WeatherViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private val alfa = 0.6f

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun mainCard(name: String, screenState: ScreenState, viewmodel: WeatherViewModel) {
    when (screenState) {
        is ScreenState.ContentState -> {
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
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp, start = 5.dp, end = 5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = screenState.result.dateTime,
                                style = TextStyle(fontSize = 15.sp, color = Color.Black),
                            )
                            GlideImage(
                                model = "https:${screenState.result.icon.toUri()}",
                                contentDescription = "image_cloud",
                                modifier = Modifier.size(35.dp)
                            )
                        }
                        Text(
                            text = screenState.result.name,
                            style = TextStyle(fontSize = 25.sp, color = Color.Black),
                        )
                        Text(
                            text = "${screenState.result.temp_c}°C",
                            style = TextStyle(fontSize = 65.sp, color = Color.Black),
                        )
                        Text(
                            text = screenState.result.text,
                            style = TextStyle(fontSize = 15.sp, color = Color.Black),
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 5.dp, start = 5.dp, end = 5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            IconButton(
                                onClick = {

                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.search),
                                    contentDescription = "image_search",
                                    tint = Color.Black
                                )
                            }

                            Text(
                                text = "${screenState.result.forecastday.first().day.mintemp_c}°C/" +
                                        "${screenState.result.forecastday.first().day.maxtemp_c}°C",
                                style = TextStyle(fontSize = 15.sp, color = Color.Black),
                            )

                            IconButton(
                                onClick = {
                                    viewmodel.getWeather(name)
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

        is ScreenState.ErrorState -> {
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
                    Text(text = screenState.message, style = TextStyle(fontSize = 35.sp))
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun tabLayout() {
    val tabList = listOf("HOURS", "DAYS")
    val pagerState = rememberPagerState()
    val tabIndex = pagerState.currentPage
    val corouScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(10.dp))
            .alpha(alfa)
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            indicator = { tabPosition ->
                TabRowDefaults.Indicator(Modifier.tabIndicatorOffset(currentTabPosition = tabPosition[tabIndex]))
            },
            containerColor = MyLightGray,
            contentColor = Color.Black
        ) {
            tabList.forEachIndexed { index, text ->
                Tab(
                    selected = false,
                    onClick = {
                        corouScope.launch(Dispatchers.IO) {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(text = text)
                    }
                )
            }
        }
        HorizontalPager(
            count = tabList.size,
            state = pagerState, modifier = Modifier.weight(1f)
        ) { index ->
            when (index) {
                0 -> listItemHoursWeather()
                1 -> Text(text = "Days weather")
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun cardItemHoursList() {
    Column(
        modifier = Modifier
            .padding(8.dp),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MyLightGray)
                .alpha(0.8f),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.padding(top = 5.dp, start = 5.dp, bottom = 5.dp)) {
                    Text(
                        text = "13:00",
                        style = TextStyle(fontSize = 15.sp, color = Color.Black),
                    )
                    Text(
                        text = "Sunny",
                        style = TextStyle(fontSize = 15.sp, color = Color.Black),
                    )
                }

                Text(
                    text = "-16°C",
                    style = TextStyle(fontSize = 25.sp, color = Color.Black),
                )

                GlideImage(
                    model = "https://cdn.weatherapi.com/weather/64x64/day/116.png",
                    contentDescription = "image_cloud",
                    modifier = Modifier
                        .padding(top = 5.dp, end = 5.dp, bottom = 5.dp)
                        .size(35.dp)
                )
            }
        }
    }
}

@Composable
fun listItemHoursWeather() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(15) {
            cardItemHoursList()
        }

    }
}