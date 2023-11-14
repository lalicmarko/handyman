package com.example.handyman.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.handyman.navigation.Route
import com.example.handyman.navigation.toRoute
import com.example.handyman.ui.screens.AuthorizationScreen
import com.example.handyman.ui.screens.MainScreen
import com.example.handyman.ui.screens.OnboardingScreen
import com.example.handyman.ui.screens.SplashScreen
import com.example.handyman.ui.viewmodels.AuthViewModel
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
fun MyApp(
    modifier: Modifier,
    authViewModel: AuthViewModel = hiltViewModel(),
    myAppState: MyAppState = rememberMyAppState()
) {
    Scaffold(modifier = modifier, topBar = {
        TopAppBar(title = {
            if (authViewModel.isLogged()) {
                val loggedUser = authViewModel.getLoggedUser()
                Text(text = "Welcome $loggedUser}")
            } else {
                Text(text = "Please sing in}")
            }
        })
    }, bottomBar = {
        BottomNavigation {
            val navBackStackEntry by myAppState.navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            Route.listBottomNavigationRoutes().forEach { screenRoute ->
                BottomNavigationItem(
                    selected = currentDestination?.hierarchy?.any { it.route == screenRoute.getRouteName() } == true,
                    onClick = {
                        myAppState.navController.navigate(screenRoute.getRouteName()) {
                            popUpTo(myAppState.navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = screenRoute.getIconImageVector(),
                            contentDescription = "bottom-icon"
                        )
                    }
                )
            }

        }
    }, floatingActionButton = {
        FloatingActionButton(onClick = { }) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }) { innerPadding ->
        MyNavHost(myAppState, modifier = modifier.padding(innerPadding))
    }
}

@Preview
@Composable
fun PreviewMyApp() {
    MyApp(modifier = Modifier.fillMaxSize())
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
            SplashScreen { splashDirection ->
                navController.navigate(splashDirection.toRoute())
            }
        }

        composable(Route.Main.getRouteName()) {
            MainScreen()
        }
    }
}