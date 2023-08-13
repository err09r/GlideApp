package com.apsl.glideapp.core.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.ContextCompat

val Context.locationPermissionsGranted: Boolean
    get() {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

val Context.showLocationRequestPermissionRationale: Boolean
    get() = this.findActivity()?.run {
        shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) &&
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
    } ?: false

fun Context.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).run(::startActivity)
}

private typealias LocationActivityResultLauncher = ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>

fun LocationActivityResultLauncher.launchForLocationPermissions(): Result<Unit> {
    return runCatching {
        this.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
