package com.apsl.glideapp.core.navigation

sealed class AppNavHost(val route: String) {
    data object Root : AppNavHost("rootNavHost")
}
