package com.apsl.glideapp.feature.home.screens

import android.Manifest
import android.os.Build
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import com.apsl.glideapp.core.ui.ComposableLifecycle
import com.apsl.glideapp.core.ui.RequestPermission
import com.apsl.glideapp.core.ui.RequestPermissionState
import com.apsl.glideapp.core.ui.icons.Bell
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.rememberRequestPermissionState
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.util.android.areNotificationsEnabled
import com.apsl.glideapp.core.util.android.openAppSettings
import timber.log.Timber

@Composable
fun NotificationPermissionDialog(modifier: Modifier = Modifier, onNavigateBack: () -> Unit) {
    val context = LocalContext.current

    SideEffect {
        if (context.areNotificationsEnabled) {
            onNavigateBack()
        }
    }

    var resumed by remember { mutableStateOf(false) }

    LaunchedEffect(resumed) {
        if (resumed && context.areNotificationsEnabled) {
            onNavigateBack()
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
                onNavigateBack()
            },
            onShowRationale = {
                Timber.d("onShowRationale")
                onNavigateBack()
            },
            onPermanentlyDenied = {
                Timber.d("onPermanentlyDenied")
                context.openAppSettings()
            }
        )
    }

    AlertDialog(
        onDismissRequest = onNavigateBack,
        confirmButton = {
            TextButton(onClick = { requestPermissionsState?.requestPermission = true }) {
                Text(text = "Allow")
            }
        },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onNavigateBack) {
                Text(text = "Dismiss")
            }
        },
        icon = { Icon(imageVector = GlideIcons.Bell, contentDescription = null) },
        title = { Text(text = "Allow notifications") },
        text = {
            Text(text = "Notifications are disabled for our app. To provide the best ride experience, enable notifications in app settings")
        }
    )
}

@Preview
@Composable
fun NotificationPermissionDialogPreview() {
    GlideAppTheme {
        NotificationPermissionDialog(onNavigateBack = {})
    }
}
