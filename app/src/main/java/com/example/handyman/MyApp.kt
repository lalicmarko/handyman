package com.example.handyman

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.handyman.navigation.Route
import com.example.handyman.ui.screens.AuthorizationScreen
import com.example.handyman.ui.screens.OnboardingScreen
import com.example.handyman.ui.screens.SplashScreen
import kotlinx.coroutines.CoroutineScope

enum class UserType {
    HANDYMAN, USER, UNAUTHORIZED
}

@Stable
data class MyAppState(
    val typeOfUser: UserType = UserType.UNAUTHORIZED,
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val startDestinationRoute: String = Route.SplashRoute.getRouteName()
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination
}

@Composable
fun rememberMyAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): MyAppState {
    return remember {
        MyAppState(navController = navController, coroutineScope = coroutineScope)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(modifier: Modifier, myAppState: MyAppState = rememberMyAppState()) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = {
                Text(text = "Welcome {user}")
            })
        },
        bottomBar = {
            BottomAppBar {
                Text(text = "Bottom App Bar")
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        MyNavHost(myAppState, modifier = modifier.padding(innerPadding))
    }
}

@Composable
fun MyNavHost(myAppState: MyAppState, modifier: Modifier = Modifier) {
    val navController = myAppState.navController

    Log.d("BOBAN", " -> MyNavHost -> render")

    NavHost(
        navController = navController,
        startDestination = myAppState.startDestinationRoute,
        modifier = modifier
    ) {
        composable(Route.AuthorizationRoute.getRouteName()) {
            AuthorizationScreen()
        }
        composable(Route.OnboardingRoute.getRouteName()) {
            OnboardingScreen()
        }

        composable(Route.SplashRoute.getRouteName()) {
            SplashScreen {
                Route.AuthorizationRoute.navigateToThis(navController = navController)
            }
        }
    }
}