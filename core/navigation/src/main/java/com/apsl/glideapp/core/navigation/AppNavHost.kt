package com.apsl.glideapp.core.navigation

import androidx.compose.runtime.Immutable

@Immutable
sealed class AppNavHost(val route: String) {
    object Root : AppNavHost("rootNavHost")
}
