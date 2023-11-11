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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.handyman.ui.viewmodels.AuthViewModel

enum class SplashScreenDirections {
    TO_LOGIN, TO_MAIN
}

@Composable
fun SplashScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    onSplashScreenFinish: (SplashScreenDirections) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()

        LaunchedEffect(key1 = Unit, block = {
            when (authViewModel.isLogged()) {
                true -> onSplashScreenFinish(SplashScreenDirections.TO_MAIN)
                false -> onSplashScreenFinish(SplashScreenDirections.TO_LOGIN)
            }
        })
    }
}

@Preview
@Composable
fun PreviewSplashScreen() {
    SplashScreen {}
}