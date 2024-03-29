package com.apsl.glideapp.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.apsl.glideapp.core.ui.navigation.AppNavGraph
import com.apsl.glideapp.core.ui.navigation.Screen
import com.apsl.glideapp.feature.auth.login.LoginScreen
import com.apsl.glideapp.feature.auth.register.RegisterScreen

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(
        startDestination = AppNavGraph.Auth.startDestination,
        route = AppNavGraph.Auth.route
    ) {
        composable(route = Screen.Auth.Login.route) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(Screen.Auth.Register.route) }
            )
        }

        composable(route = Screen.Auth.Register.route) {
            RegisterScreen(onNavigateBack = { navController.navigateUp() })
        }
    }
}
