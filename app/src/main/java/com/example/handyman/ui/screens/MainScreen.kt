package com.example.handyman.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.handyman.R
import com.example.handyman.ui.viewmodels.MainViewModel

@Composable
fun MainScreen(mainViewModel: MainViewModel = hiltViewModel()) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            val state by mainViewModel.stateFlow.collectAsState()

            LazyRow {
                items(state.professionsList.size) {
                    Surface(onClick = {
                        // clickable
                    }) {
                        Column(
                            modifier = Modifier
                                .width(100.dp)
                                .padding(horizontal = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape),
                                painter = painterResource(id = R.drawable.tools_icon),
                                contentDescription = "Icon"
                            )
                            Text(text = state.professionsList[it].name)
                        }
                    }


                }
            }
            Log.e("BOBAN", " -> MainScreen -> render")
            LazyRow {
                items(state.handymanList.size) {
                    Surface(onClick = {
                        // TODO: Implement
                    }) {
                        Column(
                            modifier = Modifier
                                .width(200.dp)
                                .padding(horizontal = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(CircleShape),
                                painter = painterResource(id = R.drawable.worker_icon),
                                contentDescription = "Icon"
                            )
                            Text(text = state.handymanList[it].name)
                            Text(text = state.handymanList[it].surname)
                            Text(text = "*".repeat(state.handymanList[it].rating))
                        }

                    }

                }
            }

        }
    }
}