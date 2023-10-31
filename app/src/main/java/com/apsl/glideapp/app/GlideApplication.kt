package com.apsl.glideapp.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import com.apsl.glideapp.core.ui.R as CoreR

@HiltAndroidApp
class GlideApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val rideSessionChannel = NotificationChannel(
            getString(CoreR.string.ride_session),
            getString(CoreR.string.ride),
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(rideSessionChannel)
    }
}
