package com.apsl.glideapp.core.navigation

sealed class AppNavGraph(val route: String) {
    data object Auth : AppNavGraph("authGraph")
    data object Home : AppNavGraph("homeGraph")
    data object Rides : AppNavGraph("ridesGraph")
    data object Wallet : AppNavGraph("walletGraph")
    data object Settings : AppNavGraph("settingsGraph")
}
