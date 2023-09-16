package com.apsl.glideapp.core.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.apsl.glideapp.core.util.findActivity
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

class RequestPermissionState(initRequest: Boolean, val permission: String) {
    var requestPermission by mutableStateOf(initRequest)
}

@Composable
fun rememberRequestPermissionState(
    initRequest: Boolean = false,
    permission: String
): RequestPermissionState {
    return remember { RequestPermissionState(initRequest, permission) }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermission(
    context: Context = LocalContext.current,
    requestState: RequestPermissionState,
    onGranted: () -> Unit,
    onShowRationale: () -> Unit,
    onPermanentlyDenied: () -> Unit
) {
    val permissionState =
        rememberPermissionState(permission = requestState.permission) { isGranted ->
            // This block will be triggered after the user chooses to grant or deny the permission
            // and we can tell if the user permanently declines or if we need to show rational
            val activity = checkNotNull(context.findActivity())
            val permissionPermanentlyDenied = !ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                requestState.permission
            ) && !isGranted

            if (permissionPermanentlyDenied) {
                onPermanentlyDenied()
            } else if (!isGranted) {
                onShowRationale()
            }
        }

    // If requestPermission, then launchPermissionRequest and the user will be able to choose
    // to grant or deny the permission.
    // After that, the RequestPermission will recompose and permissionState above will be triggered
    // and we can differentiate whether the permission is permanently declined or whether rational
    // should be shown
    if (requestState.requestPermission) {
        requestState.requestPermission = false
        if (permissionState.status.isGranted) {
            onGranted()
        } else {
            LaunchedEffect(Unit) {
                permissionState.launchPermissionRequest()
            }
        }
    }
}
