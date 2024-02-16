@file:Suppress("Unused")

package com.apsl.glideapp.core.util.android

import android.Manifest
import android.app.Activity
import android.app.NotificationManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import com.apsl.glideapp.core.android.R
import timber.log.Timber

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

fun Context.showToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}

fun Context.showToast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, resId, duration).show()
}

fun Context.openAppSettings() {
    try {
        this.startActivity(
            Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", packageName, null)
            )
        )
    } catch (e: ActivityNotFoundException) {
        Timber.w(e)
        this.showToast(R.string.toast_app_not_found)
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun Context.openAppLanguageSettings() {
    try {
        this.startActivity(
            Intent(
                Settings.ACTION_APP_LOCALE_SETTINGS,
                Uri.fromParts("package", packageName, null)
            )
        )
    } catch (e: ActivityNotFoundException) {
        Timber.w(e)
        this.showToast(R.string.toast_app_not_found)
    }
}

fun Context.getVersionName(): String? {
    return try {
        this.packageManager.getPackageInfo(this.packageName, 0)?.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        Timber.w(e)
        null
    }
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
