package com.apsl.glideapp.feature.home.screens

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import com.apsl.glideapp.core.ui.ComposableLifecycle
import com.apsl.glideapp.core.ui.ScreenActions
import com.apsl.glideapp.core.util.android.findActivity
import com.apsl.glideapp.core.util.android.showToast
import com.apsl.glideapp.feature.home.map.openLocationSettingsDialog
import com.apsl.glideapp.feature.home.map.shouldOpenLocationSettingsDialog
import com.apsl.glideapp.feature.home.rideservice.RideService
import com.apsl.glideapp.feature.home.viewmodels.HomeAction
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun HomeActionsHandler(
    actions: Flow<HomeAction>,
    onNavigateToLogin: () -> Unit,
    onNavigateToTopUp: () -> Unit,
    onNavigateToRideSummary: (Float, Float) -> Unit,
    onStartObservingUserLocation: () -> Unit,
    onRequestLocationPermissions: () -> Unit
) {
    val context = LocalContext.current
    val locationSettingsLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult()) { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK) {
                onStartObservingUserLocation()
            }
        }

    var rideService = remember<RideService?> { null }
    var serviceBound = remember { false }
    val serviceConnection = remember {
        object : ServiceConnection {

            override fun onServiceConnected(className: ComponentName, service: IBinder) {
                val binder = service as RideService.RideBinder
                rideService = binder.service
                serviceBound = true
            }

            override fun onServiceDisconnected(className: ComponentName) {
                serviceBound = false
            }
        }
    }

    ComposableLifecycle { event ->
        when (event) {
            Lifecycle.Event.ON_START -> {
                val intent = Intent(context, RideService::class.java)
                context.bindService(intent, serviceConnection, 0)
            }

            Lifecycle.Event.ON_STOP -> {
                context.unbindService(serviceConnection)
                serviceBound = false
            }

            else -> Unit
        }
    }

    val scope = rememberCoroutineScope()
    ScreenActions(actions) { action ->
        when (action) {
            is HomeAction.LogOut -> onNavigateToLogin()

            is HomeAction.StartRide -> {
                val intent = Intent(context, RideService::class.java).apply {
                    this.action = RideService.ACTION_START
                    putExtra(RideService.RIDE_ID, action.rideId)
                    putExtra(RideService.RIDE_START_DATETIME, action.startDateTime)
                }
                context.startForegroundService(intent)
            }

            is HomeAction.FinishRide -> {
                val intent = Intent(context, RideService::class.java).apply {
                    this.action = RideService.ACTION_STOP
                }

                context.startForegroundService(intent)

                scope.launch {
                    delay(200)
                    onNavigateToRideSummary(
                        action.distance.toFloat(), // Convert to float because there is no Double NavType in NavType.kt. No need to create one
                        action.averageSpeed.toFloat()
                    )
                }
            }

            is HomeAction.Toast -> context.showToast(action.textResId)

            is HomeAction.OpenLocationSettingsDialog -> {
                if (context.shouldOpenLocationSettingsDialog) {
                    context.findActivity()?.openLocationSettingsDialog(locationSettingsLauncher)
                }
            }

            is HomeAction.RestartUserLocation -> {
                if (serviceBound) {
                    rideService?.restartUserLocationFlow(
                        rideId = action.rideId,
                        rideStartDateTime = action.startDateTime
                    )
                }
            }

            is HomeAction.RequestLocationPermissions -> onRequestLocationPermissions()

            is HomeAction.OpenTopUpScreen -> onNavigateToTopUp()
        }
    }
}
