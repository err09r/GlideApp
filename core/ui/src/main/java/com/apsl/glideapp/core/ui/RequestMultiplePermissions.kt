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
import com.google.accompanist.permissions.rememberMultiplePermissionsState

class RequestMultiplePermissionsState(initRequest: Boolean, val permissions: List<String>) {
    var requestPermissions by mutableStateOf(initRequest)
}

@Composable
fun rememberRequestMultiplePermissionState(
    initRequest: Boolean = false,
    permissions: List<String>
): RequestMultiplePermissionsState {
    return remember { RequestMultiplePermissionsState(initRequest, permissions) }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestMultiplePermissions(
    context: Context = LocalContext.current,
    requestState: RequestMultiplePermissionsState,
    onGranted: () -> Unit,
    onShowRationale: () -> Unit,
    onPermanentlyDenied: () -> Unit
) {
    val permissionState =
        rememberMultiplePermissionsState(permissions = requestState.permissions) { permissionStatuses ->
            // This block will be triggered after the user chooses to grant or deny the permission
            // and we can tell if the user permanently declines or if we need to show rational
            val activity = checkNotNull(context.findActivity())
            val areGranted = permissionStatuses.all { it.value }
            val permissionsPermanentlyDenied = permissionStatuses
                .map { (permission, isGranted) ->
                    !ActivityCompat.shouldShowRequestPermissionRationale(
                        activity,
                        permission
                    ) && !isGranted
                }
                .any { !it }

            if (permissionsPermanentlyDenied) {
                onPermanentlyDenied()
            } else if (!areGranted) {
                onShowRationale()
            }
        }

    // If requestPermission, then launchPermissionRequest and the user will be able to choose
    // to grant or deny the permission.
    // After that, the RequestPermission will recompose and permissionState above will be triggered
    // and we can differentiate whether the permission is permanently declined or whether rational
    // should be shown
    if (requestState.requestPermissions) {
        requestState.requestPermissions = false
        if (permissionState.permissions.all { it.status.isGranted }) {
            onGranted()
        } else {
            LaunchedEffect(Unit) {
                permissionState.launchMultiplePermissionRequest()
            }
        }
    }
}
