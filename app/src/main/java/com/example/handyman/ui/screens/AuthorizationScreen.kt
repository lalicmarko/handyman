package com.example.handyman.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AuthorizationScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var username: String = remember {
            ""
        }
        OutlinedTextField(value = username, onValueChange = {
            username = it
        })

        var password: String = remember {
            ""
        }
        OutlinedTextField(value = password, onValueChange = {
            password = it
        })

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Login")
        }
    }
}

@Preview
@Composable
fun PreviewAuthorizationScreen() {
    AuthorizationScreen()
}