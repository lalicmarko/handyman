package com.example.handyman.navigation

import com.example.handyman.ui.screens.SplashScreenDirections

fun SplashScreenDirections.toRoute(): String {
    return when (this) {
        SplashScreenDirections.TO_LOGIN -> Route.AuthorizationRoute.getRouteName()
        SplashScreenDirections.TO_MAIN -> Route.Main.getRouteName()
    }
}

sealed interface Route {

    fun getRouteName(): String

    data object Main : Route {
        private const val routeName = "main_route"

        override fun getRouteName(): String {
            return routeName
        }

    }

    data object AuthorizationRoute : Route {
        private const val routeName = "authorize_route"

        override fun getRouteName(): String {
            return routeName
        }
    }

    data object OnboardingRoute : Route {
        private const val routeName = "onboarding_route"

        override fun getRouteName(): String {
            return routeName
        }
    }

    data object SplashRoute : Route {
        private const val routeName = "splash_route"

        override fun getRouteName(): String {
            return routeName
        }
    }
}