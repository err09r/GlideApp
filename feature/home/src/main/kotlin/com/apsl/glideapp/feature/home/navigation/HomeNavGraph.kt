package com.apsl.glideapp.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.apsl.glideapp.core.ui.navigation.AppNavGraph
import com.apsl.glideapp.core.ui.navigation.Dialog
import com.apsl.glideapp.core.ui.navigation.Screen
import com.apsl.glideapp.feature.home.dialogs.LocationPermissionDialog
import com.apsl.glideapp.feature.home.dialogs.LocationRationaleDialog
import com.apsl.glideapp.feature.home.dialogs.NotificationPermissionDialog
import com.apsl.glideapp.feature.home.preride.PreRideInfoScreen
import com.apsl.glideapp.feature.home.ridesummary.RideSummaryScreen
import com.apsl.glideapp.feature.home.screens.HomeScreen

fun NavGraphBuilder.homeGraph(navController: NavController) {
    navigation(
        startDestination = AppNavGraph.Home.startDestination,
        route = AppNavGraph.Home.route
    ) {
        composable(route = Screen.Home.Root.route) {
            HomeScreen(
                onNavigateToPreRideInfo = { navController.navigate(Screen.Home.PreRideInfo.route) },
                onNavigateToRideSummary = { distance, averageSpeed ->
                    navController.navigate(Screen.Home.RideSummary(distance, averageSpeed).route)
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

        composable(route = Screen.Home.PreRideInfo.route) {
            PreRideInfoScreen(onNavigateBack = { navController.navigateUp() })
        }

        composable(
            route = Screen.Home.RideSummary.route,
            arguments = listOf(
                navArgument("distance") { type = NavType.FloatType },
                navArgument("averageSpeed") { type = NavType.FloatType }
            )
        ) { backStackEntry ->
            val distance = backStackEntry.arguments?.getFloat("distance")
            val averageSpeed = backStackEntry.arguments?.getFloat("averageSpeed")
            RideSummaryScreen(
                distance = requireNotNull(distance),
                averageSpeed = requireNotNull(averageSpeed),
                onNavigateBack = { navController.navigateUp() }
            )
        }

        dialog(route = Dialog.Home.LocationPermission.route) {
            LocationPermissionDialog(onDismiss = { navController.navigateUp() })
        }

        dialog(route = Dialog.Home.LocationRationale.route) {
            LocationRationaleDialog(onDismiss = { navController.navigateUp() })
        }

        dialog(route = Dialog.Home.NotificationPermission.route) {
            NotificationPermissionDialog(onDismiss = { navController.navigateUp() })
        }
    }
}
