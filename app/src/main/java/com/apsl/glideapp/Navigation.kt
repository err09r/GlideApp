package com.apsl.glideapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.apsl.glideapp.core.navigation.AppNavGraph
import com.apsl.glideapp.core.navigation.AppNavHost
import com.apsl.glideapp.feature.auth.navigation.authGraph
import com.apsl.glideapp.feature.home.navigation.homeGraph
import com.apsl.glideapp.feature.rides.navigation.ridesGraph
import com.apsl.glideapp.feature.wallet.navigation.walletGraph

@Composable
fun Navigation(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = AppNavGraph.Home.route,
        route = AppNavHost.Root.route
    ) {
        authGraph(navHostController)
        homeGraph(navHostController)
        ridesGraph(navHostController)
        walletGraph(navHostController)
    }
}
