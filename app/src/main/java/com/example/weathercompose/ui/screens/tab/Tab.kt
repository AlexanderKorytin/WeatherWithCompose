package com.example.weathercompose.ui.screens.tab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weathercompose.ui.models.ScreenState
import com.example.weathercompose.ui.screens.listsweather.listItemHoursWeather
import com.example.weathercompose.ui.screens.listsweather.listItemWeekWeather
import com.example.weathercompose.ui.screens.main.alfa
import com.example.weathercompose.ui.theme.MyLightGray
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
                        else -> {}
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
                        else -> {}
                    }
                }
            }
        }
    }
}
