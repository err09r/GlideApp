package com.apsl.glideapp.feature.home.map

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import timber.log.Timber

val Context.shouldOpenLocationSettingsDialog: Boolean
    get() {
        val activityManager =
            getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager ?: return false

        return if (activityManager.appTasks.isNullOrEmpty().not()) {
            val activity = activityManager.appTasks[0].taskInfo.topActivity
            return activity?.className?.contains("LocationSettingsCheckerActivity")?.not() ?: true
        } else {
            false
        }
    }

fun Activity.openLocationSettingsDialog(launcher: ActivityResultLauncher<IntentSenderRequest>) {
    // No matter what parameters will be passed to Builder, result will be ignored anyway
    val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000).build()

    val locationSettingsRequest = LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest)
        .setAlwaysShow(true)
        .build()

    val task = LocationServices.getSettingsClient(this)
        .checkLocationSettings(locationSettingsRequest)

    task.addOnFailureListener { exception ->
        if (exception is ResolvableApiException) {
            try {
                val intentSenderRequest = IntentSenderRequest.Builder(exception.resolution).build()
                launcher.launch(intentSenderRequest)
            } catch (e: Exception) {
                Timber.d(e)
            }
        }
    }
}
