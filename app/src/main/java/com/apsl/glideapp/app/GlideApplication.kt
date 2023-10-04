package com.apsl.glideapp.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GlideApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val locationChannel =
            NotificationChannel("location", "Location", NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(locationChannel)
    }
}
