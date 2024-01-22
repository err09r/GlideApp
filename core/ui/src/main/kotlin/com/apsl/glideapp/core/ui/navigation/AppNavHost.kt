@file:Suppress("Unused")

package com.apsl.glideapp.core.ui.navigation

sealed class AppNavHost(val route: String) {
    data object Root : AppNavHost("rootNavHost")
}
