package com.apsl.glideapp.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import com.apsl.glideapp.core.navigation.AppNavGraph
import com.apsl.glideapp.core.navigation.Screen
import com.apsl.glideapp.feature.home.screens.HomeScreen
import com.apsl.glideapp.feature.home.screens.LocationPermissionDialog
import com.apsl.glideapp.feature.home.screens.LocationRationaleDialog
import com.apsl.glideapp.feature.home.screens.NotificationPermissionDialog

fun NavGraphBuilder.homeGraph(navController: NavController) {
    navigation(startDestination = Screen.Home.Root.route, route = AppNavGraph.Home.route) {

        composable(route = Screen.Home.Root.route) {
            HomeScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Auth.Login.route) {
                        popUpTo(Screen.Home.Root.route) { inclusive = true }
                    }
                },
                onNavigateToAllRides = {
                    navController.navigate(Screen.Rides.Root.route)
                },
                onNavigateToWallet = {
                    navController.navigate(Screen.Wallet.Root.route)
                },
                onNavigateToLocationPermission = {
                    navController.navigate(Screen.Home.LocationPermission.route)
                },
                onNavigateToLocationRationale = {
                    navController.navigate(Screen.Home.LocationRationale.route)
                },
                onNavigateToNotificationPermission = {
                    navController.navigate(Screen.Home.NotificationPermission.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        dialog(route = Screen.Home.LocationPermission.route) {
            LocationPermissionDialog(onNavigateBack = { navController.popBackStack() })
        }

        dialog(route = Screen.Home.LocationRationale.route) {
            LocationRationaleDialog(onNavigateBack = { navController.popBackStack() })
        }

        dialog(route = Screen.Home.NotificationPermission.route) {
            NotificationPermissionDialog(onNavigateBack = { navController.popBackStack() })
        }
    }
}
