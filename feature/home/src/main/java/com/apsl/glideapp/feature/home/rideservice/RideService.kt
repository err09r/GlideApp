package com.apsl.glideapp.feature.home.rideservice

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.apsl.glideapp.common.models.RideAction
import com.apsl.glideapp.core.datastore.AppDataStore
import com.apsl.glideapp.core.domain.location.AddressDecoder
import com.apsl.glideapp.core.domain.location.LocationRepository
import com.apsl.glideapp.core.network.WebSocketClient
import com.apsl.glideapp.feature.home.R
import com.apsl.glideapp.feature.home.maps.toCoordinates
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class RideService : Service() {

    @Inject
    lateinit var locationRepository: LocationRepository

    @Inject
    lateinit var webSocketClient: WebSocketClient

    @Inject
    lateinit var addressDecoder: AddressDecoder

    @Inject
    lateinit var appDataStore: AppDataStore

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val notificationManager get() = NotificationManagerCompat.from(this)

    private val pendingContentIntent by lazy {
        val contentIntent = Intent(
            this,
            Class.forName("com.apsl.glideapp.MainActivity")
        ).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        PendingIntent.getActivity(
            this,
            0,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private val notificationBuilder by lazy {
        NotificationCompat.Builder(this, "location")
            .setContentTitle("Tracking location...")
            .setContentText("Location")
            .setSmallIcon(R.drawable.ic_scooter)
            .setOngoing(true)
            .setAutoCancel(false)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingContentIntent)
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        Timber.d("Service created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> {
                val rideId = intent.extras?.getString(RIDE_ID)
                if (rideId != null) {
                    start(rideId)
                }
            }

            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start(rideId: String) {
        startForeground(NOTIFICATION_ID, notificationBuilder.build())
        startReceivingLocationUpdates()
        observeUserLocation(rideId = rideId)
    }

    private fun startReceivingLocationUpdates() {
        scope.launch {
            appDataStore.saveLocationUpdateInterval(LOCATION_REQUEST_INTERVAL_MS)
            locationRepository.startReceivingLocationUpdates()
        }
    }

    private fun observeUserLocation(rideId: String) {
        scope.launch {
            locationRepository.userLocation.collectLatest { userLocation ->
                val coordinates = userLocation.toCoordinates()

                webSocketClient.sendRideAction(
                    RideAction.UpdateRoute(
                        rideId = rideId,
                        coordinates = coordinates
                    )
                )

                val address = addressDecoder.decodeFromCoordinates(coordinates)
                if (address != null) {
                    updateNotification(address)
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun updateNotification(address: String) {
        notificationManager.notify(
            NOTIFICATION_ID,
            notificationBuilder.setContentText("Driving around: $address").build()
        )
    }

    private fun stop() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    override fun onDestroy() {
        stopReceivingLocationUpdates()
        scope.cancel()
        super.onDestroy()
    }

    private fun stopReceivingLocationUpdates() {
        scope.launch {
            locationRepository.stopReceivingLocationUpdates()
        }
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
        const val RIDE_ID = "RIDE_ID"
        private const val NOTIFICATION_ID = 1
        private const val LOCATION_REQUEST_INTERVAL_MS = 1000L
    }
}
