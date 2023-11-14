package com.example.handyman.navigation

import android.graphics.drawable.Icon
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.handyman.ui.screens.SplashScreenDirections

fun SplashScreenDirections.toRoute(): String {
    return when (this) {
        SplashScreenDirections.TO_LOGIN -> Route.AuthorizationRoute.getRouteName()
        SplashScreenDirections.TO_MAIN -> Route.Main.getRouteName()
    }
}

sealed interface Route {

    fun getRouteName(): String

    fun getIconImageVector(): ImageVector

    data object Main : Route {
        private const val routeName = "main_route"

        override fun getRouteName(): String {
            return routeName
        }

        override fun getIconImageVector(): ImageVector {
            return Icons.Filled.Home
        }

    }

    data object AuthorizationRoute : Route {
        private const val routeName = "authorize_route"

        override fun getRouteName(): String {
            return routeName
        }

        override fun getIconImageVector(): ImageVector {
            return Icons.Filled.Lock
        }
    }

    data object OnboardingRoute : Route {
        private const val routeName = "onboarding_route"

        override fun getRouteName(): String {
            return routeName
        }

        override fun getIconImageVector(): ImageVector {
            return Icons.Filled.Done
        }
    }

    data object SplashRoute : Route {
        private const val routeName = "splash_route"

        override fun getRouteName(): String {
            return routeName
        }

        override fun getIconImageVector(): ImageVector {
            return Icons.Filled.ExitToApp
        }
    }

    companion object {
        fun listBottomNavigationRoutes(): List<Route> {
            return listOf(Main, OnboardingRoute)
        }
    }
}