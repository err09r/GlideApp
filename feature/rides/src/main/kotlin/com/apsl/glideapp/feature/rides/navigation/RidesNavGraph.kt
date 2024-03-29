package com.apsl.glideapp.feature.rides.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.apsl.glideapp.core.ui.navigation.AppNavGraph
import com.apsl.glideapp.core.ui.navigation.Screen
import com.apsl.glideapp.feature.rides.allrides.AllRidesScreen
import com.apsl.glideapp.feature.rides.details.RideDetailsScreen

fun NavGraphBuilder.ridesGraph(navController: NavController) {
    navigation(
        startDestination = AppNavGraph.Rides.startDestination,
        route = AppNavGraph.Rides.route
    ) {
        composable(route = Screen.Rides.Root.route) {
            AllRidesScreen(
                onNavigateBack = { navController.navigateUp() },
                onNavigateToRide = { rideId ->
                    navController.navigate(Screen.Rides.RideDetails(id = rideId).route)
                }
            )
        }

        composable(
            route = Screen.Rides.RideDetails.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val rideId = backStackEntry.arguments?.getString("id")
            RideDetailsScreen(
                rideId = requireNotNull(rideId),
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}
