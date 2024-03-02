package com.apsl.glideapp.feature.home.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.apsl.glideapp.core.ui.GlideImage
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.util.android.openAppSettings
import com.apsl.glideapp.core.ui.R as CoreR

@Composable
fun LocationPermissionDialog(onDismiss: () -> Unit, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    context.openAppSettings()
                    onDismiss()
                }
            ) {
                Text(text = stringResource(CoreR.string.location_permission_dialog_button))
            }
        },
        modifier = modifier,
        icon = { GlideImage(CoreR.drawable.img_toggle) },
        title = { Text(text = stringResource(CoreR.string.location_permission_dialog_title)) },
        text = { Text(text = stringResource(CoreR.string.location_permission_dialog_text)) }
    )
}

@Preview(showBackground = true)
@Composable
private fun LocationPermissionDialogPreview() {
    GlideAppTheme {
        Dialog(onDismissRequest = {}) {
            LocationPermissionDialog(onDismiss = {})
        }
    }
}
