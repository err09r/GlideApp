package com.apsl.glideapp.feature.home.rideservice

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.apsl.glideapp.feature.home.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class RideService : Service() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val binder = RideBinder()

    @Inject
    lateinit var controllerFactory: RideServiceControllerFactory
    private var controller: RideServiceController? = null

    private val notificationManager get() = NotificationManagerCompat.from(this)

    private val pendingContentIntent by lazy {
        val contentIntent = Intent(this, Class.forName(MAIN_ACTIVITY_CLASSNAME)).apply {
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

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onCreate() {
        super.onCreate()
        Timber.d("onCreate")
        controller = controllerFactory.create(scope = scope)
        controller?.run {
            currentAddress
                .filterNotNull()
                .onEach { updateNotification(address = it) }
                .launchIn(scope)
        }
    }

    @SuppressLint("MissingPermission")
    private fun updateNotification(address: String) {
        notificationManager.notify(
            NOTIFICATION_ID,
            notificationBuilder.setContentText("Driving around: $address").build()
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> {
                val rideId = checkNotNull(intent.extras?.getString(RIDE_ID))
                start(rideId)
            }

            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start(rideId: String) {
        startForeground(NOTIFICATION_ID, notificationBuilder.build())
        controller?.onServiceStart(rideId = rideId)
    }

    private fun stop() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    fun restartUserLocationFlow(rideId: String) {
        controller?.startObservingUserLocation(rideId)
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
        Timber.d("onDestroy")
    }

    inner class RideBinder : Binder() {
        val service get() = this@RideService
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
        const val RIDE_ID = "RIDE_ID"
        private const val NOTIFICATION_ID = 1
        private const val MAIN_ACTIVITY_CLASSNAME = "com.apsl.glideapp.app.MainActivity"
    }
}
