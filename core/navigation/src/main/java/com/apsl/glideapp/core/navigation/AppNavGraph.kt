package com.apsl.glideapp.core.navigation

import androidx.compose.runtime.Immutable

@Immutable
sealed class AppNavGraph(val route: String) {
    object Auth : AppNavGraph("authGraph")
    object Home : AppNavGraph("homeGraph")
    object Rides : AppNavGraph("ridesGraph")
    object Wallet : AppNavGraph("walletGraph")
    object Settings : AppNavGraph("settingsGraph")
}
