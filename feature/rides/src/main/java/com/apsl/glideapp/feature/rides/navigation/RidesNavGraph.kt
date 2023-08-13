package com.apsl.glideapp.feature.rides.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.apsl.glideapp.core.navigation.AppNavGraph
import com.apsl.glideapp.core.navigation.Screen
import com.apsl.glideapp.feature.rides.screens.AllRidesScreen
import com.apsl.glideapp.feature.rides.screens.RideDetailsScreen

fun NavGraphBuilder.ridesGraph(navController: NavController) {
    navigation(startDestination = Screen.Rides.Root.route, route = AppNavGraph.Rides.route) {

        composable(route = Screen.Rides.Root.route) {
            AllRidesScreen(
                onNavigateBack = { navController.popBackStack() },
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
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
