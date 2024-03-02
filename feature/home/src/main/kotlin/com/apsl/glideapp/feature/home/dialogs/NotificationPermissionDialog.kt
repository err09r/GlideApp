package com.apsl.glideapp.feature.home.dialogs

import android.Manifest
import android.os.Build
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import com.apsl.glideapp.core.ui.ComposableLifecycle
import com.apsl.glideapp.core.ui.GlideImage
import com.apsl.glideapp.core.ui.RequestPermission
import com.apsl.glideapp.core.ui.RequestPermissionState
import com.apsl.glideapp.core.ui.rememberRequestPermissionState
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.util.android.areNotificationsEnabled
import com.apsl.glideapp.core.util.android.openAppSettings
import timber.log.Timber
import com.apsl.glideapp.core.ui.R as CoreR

@Composable
fun NotificationPermissionDialog(onDismiss: () -> Unit, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    SideEffect {
        if (context.areNotificationsEnabled) {
            onDismiss()
        }
    }

    var resumed by remember { mutableStateOf(false) }

    LaunchedEffect(resumed) {
        if (resumed && context.areNotificationsEnabled) {
            onDismiss()
        }
    }

    ComposableLifecycle { event ->
        resumed = when (event) {
            Lifecycle.Event.ON_RESUME -> true
            else -> false
        }
    }

    val requestPermissionsState: RequestPermissionState? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            rememberRequestPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
        } else {
            null
        }

    if (requestPermissionsState != null) {
        RequestPermission(
            requestState = requestPermissionsState,
            onGranted = {
                Timber.d("onGranted")
                onDismiss()
            },
            onShowRationale = {
                Timber.d("onShowRationale")
                onDismiss()
            },
            onPermanentlyDenied = {
                Timber.d("onPermanentlyDenied")
                context.openAppSettings()
            }
        )
    }

    NotificationPermissionDialogContent(
        modifier = modifier,
        onDismiss = onDismiss,
        onConfirmButtonClick = { requestPermissionsState?.requestPermission = true })
}

@Composable
fun NotificationPermissionDialogContent(
    onDismiss: () -> Unit,
    onConfirmButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirmButtonClick) {
                Text(text = stringResource(CoreR.string.notification_permission_dialog_button_confirm))
            }
        },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(CoreR.string.notification_permission_dialog_button_dismiss))
            }
        },
        icon = { GlideImage(CoreR.drawable.img_bell) },
        title = { Text(text = stringResource(CoreR.string.notification_permission_dialog_title)) },
        text = {
            Text(text = stringResource(CoreR.string.notification_permission_dialog_text))
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun NotificationPermissionDialogPreview() {
    GlideAppTheme {
        Dialog(onDismissRequest = {}) {
            NotificationPermissionDialogContent(onDismiss = {}, onConfirmButtonClick = {})
        }
    }
}
