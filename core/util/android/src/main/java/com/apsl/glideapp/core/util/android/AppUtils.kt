@file:Suppress("Unused")

package com.apsl.glideapp.core.util.android

import android.Manifest
import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver

val Context.areLocationPermissionsGranted: Boolean
    get() = arePermissionsGranted(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

fun Context.arePermissionsGranted(permissions: Iterable<String>): Boolean {
    return permissions.all { isPermissionGranted(it) }
}

fun Context.isPermissionGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

val Context.areNotificationsEnabled: Boolean
    get() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        return notificationManager.areNotificationsEnabled()
    }

fun Context.shouldShowRequestPermissionRationale(permission: String): Boolean {
    val activity = this.findActivity()
    return if (activity != null) {
        ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
    } else {
        false
    }
}

fun Context.isPermissionPermanentlyDenied(permission: String): Boolean {
    val isGranted = isPermissionGranted(permission)
    val activity = this.findActivity()
    return if (activity != null) {
        !ActivityCompat.shouldShowRequestPermissionRationale(
            activity,
            permission
        ) && !isGranted
    } else {
        false
    }
}

fun Context.openAppSettings() {
    this.startActivity(
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        )
    )
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

fun Lifecycle.addObservers(observers: Iterable<LifecycleObserver>) {
    observers.forEach(::addObserver)
}

fun Lifecycle.addObservers(vararg observers: LifecycleObserver) {
    observers.forEach(::addObserver)
}
