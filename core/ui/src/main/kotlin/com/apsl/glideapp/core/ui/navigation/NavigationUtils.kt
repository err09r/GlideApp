@file:Suppress("Unused")

package com.apsl.glideapp.core.ui.navigation

import androidx.annotation.MainThread
import androidx.navigation.NavController

@MainThread
fun <T> NavController.popBackStackWithArgument(key: String, value: T?) {
    previousBackStackEntry?.savedStateHandle?.set(key, value)
    popBackStack()
}

@MainThread
fun <T> NavController.popBackStackWithArgument(pair: Pair<String, T?>) {
    this.popBackStackWithArgument(key = pair.first, value = pair.second)
}

@MainThread
fun NavController.safePopBackStack() {
    if (this.currentBackStackEntry?.destination?.route != Screen.Loading.route) {
        this.popBackStack()
    }
}
