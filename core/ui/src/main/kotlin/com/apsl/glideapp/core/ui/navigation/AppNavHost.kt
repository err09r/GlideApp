@file:Suppress("Unused")

package com.apsl.glideapp.core.ui.navigation

sealed class AppNavHost(val route: String, val startDestination: String) {
    data object Root : AppNavHost(route = "rootNavHost", startDestination = Screen.Loading.route)
}
