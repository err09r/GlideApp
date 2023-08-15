package com.apsl.glideapp.core.navigation

import androidx.compose.runtime.Immutable

@Immutable
sealed class AppNavHost(val route: String) {
    data object Root : AppNavHost("rootNavHost")
}
