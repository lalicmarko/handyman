package com.example.handyman.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.handyman.ui.viewmodels.AuthViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorizationScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = hiltViewModel()
) {

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

        Button(onClick = {
            authViewModel.login(email = "mlalic995@gmail.com", password = "123456789")
        }) {
            Text(text = "Login")
        }
    }

    var errorEvent by remember { mutableStateOf(false to "") }

    if (errorEvent.first) {
        Log.d("BOBAN", " -> AuthorizationScreen -> show dialog")
        AlertDialog(onDismissRequest = {
            errorEvent = false to ""
        }) {
            Surface(
                modifier = Modifier.wrapContentSize(),
                shape = AlertDialogDefaults.shape
            ) {
                Text(text = "Error has occurred: ${errorEvent.second}")
            }
        }
    }

    LaunchedEffect(Unit) {
        authViewModel.errorEvent.collectLatest {
            Log.d("BOBAN", " -> AuthorizationScreen -> error update")
            errorEvent = true to (it ?: "error message null")
        }
    }
}

@Preview
@Composable
fun PreviewAuthorizationScreen() {
    AuthorizationScreen()
}