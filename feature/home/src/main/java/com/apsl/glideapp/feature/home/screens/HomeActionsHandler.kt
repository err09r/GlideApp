package com.apsl.glideapp.feature.home.screens

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import com.apsl.glideapp.core.ui.ComposableLifecycle
import com.apsl.glideapp.core.util.android.findActivity
import com.apsl.glideapp.feature.home.rideservice.RideService
import com.apsl.glideapp.feature.home.viewmodels.HomeAction
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeActionsHandler(
    actions: Flow<HomeAction>,
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

    LaunchedEffect(actions) {
        actions.collect { action ->
            when (action) {

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
                }

                is HomeAction.Toast -> {
                    Toast.makeText(context, action.message, Toast.LENGTH_LONG).show()
                }

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

                else -> Unit
            }
        }
    }
}
