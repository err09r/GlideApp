@file:SuppressLint("RestrictedApi")

package com.apsl.glideapp.app

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.apsl.glideapp.core.domain.auth.ObserveUserAuthenticationStateUseCase
import com.apsl.glideapp.core.model.UserAuthState
import com.apsl.glideapp.core.ui.BaseViewModel
import com.apsl.glideapp.core.ui.LoadingScreen
import com.apsl.glideapp.core.ui.ScreenActions
import com.apsl.glideapp.core.ui.navigation.AppNavHost
import com.apsl.glideapp.core.ui.navigation.Screen
import com.apsl.glideapp.feature.auth.navigation.authGraph
import com.apsl.glideapp.feature.home.navigation.homeGraph
import com.apsl.glideapp.feature.rides.navigation.ridesGraph
import com.apsl.glideapp.feature.wallet.navigation.walletGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import timber.log.Timber

@Composable
fun Navigation(navHostController: NavHostController, modifier: Modifier = Modifier) {
    DisposableEffect(navHostController) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            Timber.d("Navigating to route: ${destination.route}, current backstack size: ${navHostController.currentBackStack.value.size}")
            val routes = navHostController.currentBackStack.value.map {
                it.destination.route.toString()
            }
            Timber.d(routes.toString())
        }
        navHostController.addOnDestinationChangedListener(listener)
        onDispose {
            navHostController.removeOnDestinationChangedListener(listener)
        }
    }

    AuthStateInterceptor(
        onLogIn = remember(navHostController) {
            {
                navHostController.safeNavigate(
                    navigateToRoute = Screen.Home.Root.route,
                    popUpToRoute = Screen.Auth.Login.route
                )
            }
        },
        onLogOut = remember(navHostController) {
            {
                navHostController.safeNavigate(
                    navigateToRoute = Screen.Auth.Login.route,
                    popUpToRoute = Screen.Home.Root.route
                )
            }
        }
    )

    NavHost(
        navController = navHostController,
        startDestination = Screen.Loading.route,
        modifier = modifier,
        route = AppNavHost.Root.route,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) }
    ) {
        loadingScreen()
        authGraph(navHostController)
        homeGraph(navHostController)
        ridesGraph(navHostController)
        walletGraph(navHostController)
    }
}

private fun NavGraphBuilder.loadingScreen() {
    composable(route = Screen.Loading.route) {
        LoadingScreen()
    }
}

@Composable
private fun AuthStateInterceptor(
    onLogIn: () -> Unit,
    onLogOut: () -> Unit,
    viewModel: AuthStateViewModel = hiltViewModel()
) {
    ScreenActions(viewModel.actions) { authState ->
        when (authState) {
            UserAuthState.Authenticated -> onLogIn()
            UserAuthState.NotAuthenticated -> onLogOut()
            else -> Unit
        }
    }
}

private fun NavController.safeNavigate(navigateToRoute: String, popUpToRoute: String) {
    val isLoadingScreenInBackstack = this.currentBackStack.value
        .mapNotNull { it.destination.route }
        .contains(Screen.Loading.route)

    val popUpRoute = if (isLoadingScreenInBackstack) Screen.Loading.route else popUpToRoute

    val builder: NavOptionsBuilder.() -> Unit = {
        popUpTo(popUpRoute) { inclusive = true }
        launchSingleTop = true
    }

    this.navigate(navigateToRoute, builder)
}

@HiltViewModel
class AuthStateViewModel @Inject constructor(
    observeUserAuthenticationStateUseCase: ObserveUserAuthenticationStateUseCase
) : BaseViewModel() {

    private val _actions = Channel<UserAuthState>()
    val actions = _actions.receiveAsFlow()

    init {
        observeUserAuthenticationStateUseCase()
            .distinctUntilChanged()
            .onEach { authState ->
                Timber.d("User authentication state: $authState")
                _actions.send(authState)
            }
            .launchIn(viewModelScope)
    }
}
