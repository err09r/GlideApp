@file:Suppress("Unused")

package com.apsl.glideapp.core.ui.navigation

sealed class AppNavGraph(val route: String, val startDestination: String) {

    data object Auth : AppNavGraph(
        route = "authGraph",
        startDestination = Screen.Auth.Login.route
    )

    data object Home : AppNavGraph(
        route = "homeGraph",
        startDestination = Screen.Home.Root.route
    )

    data object Rides : AppNavGraph(
        route = "ridesGraph",
        startDestination = Screen.Rides.Root.route
    )

    data object Wallet : AppNavGraph(
        route = "walletGraph",
        startDestination = Screen.Wallet.Root.route
    )
}
