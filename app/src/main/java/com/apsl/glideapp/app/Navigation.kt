package com.apsl.glideapp.app

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.apsl.glideapp.core.navigation.AppNavGraph
import com.apsl.glideapp.core.navigation.AppNavHost
import com.apsl.glideapp.feature.auth.navigation.authGraph
import com.apsl.glideapp.feature.home.navigation.homeGraph
import com.apsl.glideapp.feature.rides.navigation.ridesGraph
import com.apsl.glideapp.feature.wallet.navigation.walletGraph
import timber.log.Timber

@Composable
fun Navigation(navHostController: NavHostController, modifier: Modifier = Modifier) {
    DisposableEffect(navHostController) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            Timber.d("Navigating to route: ${destination.route}")
        }
        navHostController.addOnDestinationChangedListener(listener)
        onDispose {
            navHostController.removeOnDestinationChangedListener(listener)
        }
    }

    NavHost(
        navController = navHostController,
        startDestination = AppNavGraph.Home.route,
        modifier = modifier,
        route = AppNavHost.Root.route,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) }
    ) {
        authGraph(navHostController)
        homeGraph(navHostController)
        ridesGraph(navHostController)
        walletGraph(navHostController)
    }
}
