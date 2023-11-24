package com.apsl.glideapp.feature.home.screens

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.apsl.glideapp.core.ui.R
import com.apsl.glideapp.core.ui.components.GlideImage
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.util.android.openAppSettings

@Composable
fun LocationRationaleDialog(onDismiss: () -> Unit, modifier: Modifier = Modifier) {
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
                Text(text = "Open app settings")
            }
        },
        modifier = modifier,
        icon = { GlideImage(R.drawable.img_location) },
        title = { Text(text = "Enable location") },
        text = { Text(text = "Location permission has to be granted to be able to use ride functionality") }
    )
}

@Preview(showBackground = true)
@Composable
fun LocationRationaleDialogPreview() {
    GlideAppTheme {
        Dialog(onDismissRequest = {}) {
            LocationRationaleDialog(onDismiss = {})
        }
    }
}
