package com.example.handyman.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.handyman.R
import com.example.handyman.ui.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(mainViewModel: MainViewModel = hiltViewModel()) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            val state by mainViewModel.stateFlow.collectAsState()
            val lazyColumnState = rememberLazyListState()

            val isTopRowVisible by remember {
                derivedStateOf {
                    lazyColumnState.firstVisibleItemIndex < 2
                }
            }

            AnimatedVisibility(visible = isTopRowVisible) {
                LazyRow(modifier = Modifier.height(100.dp)) {
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
            }
            AnimatedVisibility(visible = isTopRowVisible) {
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

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                state = lazyColumnState,
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                items(state.posts.size) {
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(10.dp),
                        onClick = { /*TODO*/ }) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = state.posts[it].title,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                            Text(
                                text = state.posts[it].description,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 3
                            )
                            listOf(
                                R.drawable.workshop_1,
                                R.drawable.workshop_2,
                                R.drawable.workshop_3,
                                R.drawable.workshop_4
                            ).random().let {
                                Image(
                                    painter = painterResource(id = it),
                                    contentDescription = "Worker",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}