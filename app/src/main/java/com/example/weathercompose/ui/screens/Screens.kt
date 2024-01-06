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
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.net.toUri
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.weathercompose.R
import com.example.weathercompose.domain.models.Day
import com.example.weathercompose.domain.models.Forecastday
import com.example.weathercompose.domain.models.Hour
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
fun tabLayout(screenState: ScreenState) {
    val tabList = listOf("HOURS", "DAYS")
    val pagerState = rememberPagerState()
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

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
                        coroutineScope.launch(Dispatchers.IO) {
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
                0 -> {
                    when (screenState) {
                        is ScreenState.ContentState -> {
                            listItemHoursWeather(screenState.result.forecastday.first().hour)
                        }

                        is ScreenState.ErrorState -> {
                            Text(text = "Day weather ${screenState.message}")
                        }
                    }
                }

                1 -> {
                    when (screenState) {
                        is ScreenState.ContentState -> {
                            listItemWeekWeather(screenState.result.forecastday)
                        }

                        is ScreenState.ErrorState -> {
                            Text(text = "Days weather ${screenState.message}")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun cardItemHoursList(item: Hour) {
    Column(
        modifier = Modifier
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MyLightGray)
                .alpha(0.8f),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxWidth()
            ) {
                val (text1, text2, text3, image) = createRefs()
                Text(
                    modifier = Modifier.constrainAs(text1) {
                        start.linkTo(parent.start, margin = 5.dp)
                        top.linkTo(parent.top, margin = 5.dp)
                    },
                    text = item.time.takeLast(5),
                    style = TextStyle(fontSize = 15.sp, color = Color.Black),
                )
                Text(
                    modifier = Modifier.constrainAs(text2) {
                        start.linkTo(parent.start, margin = 5.dp)
                        top.linkTo(text1.bottom, margin = 5.dp)
                    },
                    text = item.condition.text,
                    style = TextStyle(fontSize = 15.sp, color = Color.Black),
                )

                Text(
                    modifier = Modifier.constrainAs(text3) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                    text = "${item.temp_c}°C",
                    style = TextStyle(fontSize = 25.sp, color = Color.Black),
                )

                GlideImage(
                    modifier = Modifier
                        .constrainAs(image) {
                            end.linkTo(parent.end, margin = 5.dp)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                        .size(35.dp),
                    model = "https:${item.condition.icon}",
                    contentDescription = "image_cloud",
                )
            }
        }
    }
}

@Composable
fun listItemHoursWeather(list: List<Hour>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(list) { index, item ->
            cardItemHoursList(item = item)
        }

    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun cardItemDaysList(item: Forecastday) {
    Column(
        modifier = Modifier
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MyLightGray)
                .alpha(0.8f),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxWidth()
            ) {
                val (text1, text2, text3, image) = createRefs()
                Text(
                    modifier = Modifier.constrainAs(text1) {
                        start.linkTo(parent.start, margin = 5.dp)
                        top.linkTo(parent.top, margin = 5.dp)
                    },
                    text = item.date,
                    style = TextStyle(fontSize = 15.sp, color = Color.Black),
                )
                Text(
                    modifier = Modifier.constrainAs(text2) {
                        start.linkTo(parent.start, margin = 5.dp)
                        top.linkTo(text1.bottom, margin = 5.dp)
                    },
                    text = item.day.condition.text,
                    style = TextStyle(fontSize = 15.sp, color = Color.Black),
                )

                Text(
                    modifier = Modifier.constrainAs(text3) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                    text = "${item.day.mintemp_c}°C/${item.day.maxtemp_c}°C",
                    style = TextStyle(fontSize = 20.sp, color = Color.Black),
                )

                GlideImage(
                    modifier = Modifier
                        .constrainAs(image) {
                            end.linkTo(parent.end, margin = 5.dp)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                        .size(35.dp),
                    model = "https:${item.day.condition.icon}",
                    contentDescription = "image_cloud",
                )
            }
        }
    }
}

@Composable
fun listItemWeekWeather(list: List<Forecastday>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(list) { index, item ->
            cardItemDaysList(item = item)
        }

    }
}
