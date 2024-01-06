package com.example.weathercompose.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.weathercompose.domain.models.Hour
import com.example.weathercompose.ui.theme.MyLightGray

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