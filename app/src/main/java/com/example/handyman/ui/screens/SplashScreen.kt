package com.example.handyman.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun SplashScreen(onSplashScreenFinish: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()

        LaunchedEffect(key1 = Unit, block = {
            delay(4.seconds)
            onSplashScreenFinish()
        })
    }
}

@Preview
@Composable
fun PreviewSplashScreen() {
    SplashScreen {}
}