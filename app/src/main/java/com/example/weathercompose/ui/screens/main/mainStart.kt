package com.example.weathercompose.ui.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun mainCardStart() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (bar) = createRefs()
        CircularProgressIndicator(
            modifier = Modifier
                .size(100.dp)
                .constrainAs(bar) {
                    top.linkTo(parent.top, margin = 32.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            strokeWidth = 10.dp
        )
    }
}