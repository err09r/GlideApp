package com.apsl.glideapp.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import com.apsl.glideapp.core.ui.navigation.AppNavGraph
import com.apsl.glideapp.core.ui.navigation.Dialog
import com.apsl.glideapp.core.ui.navigation.Screen
import com.apsl.glideapp.feature.home.dialogs.LocationPermissionDialog
import com.apsl.glideapp.feature.home.dialogs.LocationRationaleDialog
import com.apsl.glideapp.feature.home.dialogs.NotificationPermissionDialog
import com.apsl.glideapp.feature.home.screens.HomeScreen

fun NavGraphBuilder.homeGraph(navController: NavController) {
    navigation(startDestination = Screen.Home.Root.route, route = AppNavGraph.Home.route) {

        composable(route = Screen.Home.Root.route) {
            HomeScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Auth.Login.route) {
                        popUpTo(Screen.Home.Root.route) { inclusive = true }
                    }
                },
                onNavigateToAllRides = { navController.navigate(Screen.Rides.Root.route) },
                onNavigateToWallet = { navController.navigate(Screen.Wallet.Root.route) },
                onNavigateToTopUp = { navController.navigate(Screen.Wallet.TopUp.route) },
                onNavigateToLocationPermission = {
                    navController.navigate(Dialog.Home.LocationPermission.route)
                },
                onNavigateToLocationRationale = {
                    navController.navigate(Dialog.Home.LocationRationale.route)
                },
                onNavigateToNotificationPermission = {
                    navController.navigate(Dialog.Home.NotificationPermission.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        dialog(route = Dialog.Home.LocationPermission.route) {
            LocationPermissionDialog(onDismiss = { navController.popBackStack() })
        }

        dialog(route = Dialog.Home.LocationRationale.route) {
            LocationRationaleDialog(onDismiss = { navController.popBackStack() })
        }

        dialog(route = Dialog.Home.NotificationPermission.route) {
            NotificationPermissionDialog(onDismiss = { navController.popBackStack() })
        }
    }
}
