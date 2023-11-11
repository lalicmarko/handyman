package com.example.handyman.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

sealed interface Route {

    fun navigateToThis(navController: NavController, navOptions: NavOptions? = null)

    fun getRouteName(): String

    data object AuthorizationRoute : Route {
        private const val routeName = "authorize_route"
        override fun navigateToThis(navController: NavController, navOptions: NavOptions?) {
            navController.navigate(routeName, navOptions)
        }


        override fun getRouteName(): String {
            return routeName
        }
    }

    data object OnboardingRoute : Route {
        private const val routeName = "onboarding_route"
        override fun navigateToThis(navController: NavController, navOptions: NavOptions?) {
            navController.navigate(routeName, navOptions)
        }

        override fun getRouteName(): String {
            return routeName
        }
    }

    data object SplashRoute : Route {
        private const val routeName = "splash_route"
        override fun navigateToThis(navController: NavController, navOptions: NavOptions?) {
            navController.navigate(routeName, navOptions)
        }

        override fun getRouteName(): String {
            return routeName
        }
    }
}