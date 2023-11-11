package com.apsl.glideapp.feature.wallet.screens

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.apsl.glideapp.core.ui.components.GlideImage
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.ui.R as CoreR

@Composable
fun TopUpSuccessDialog(onDismiss: () -> Unit, modifier: Modifier = Modifier) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Continue")
            }
        },
        modifier = modifier,
        icon = { GlideImage(CoreR.drawable.img_dollar) },
        title = { Text(text = "Success!") },
        text = { Text(text = "Your balance has been successfully topped up. Have a nice ride!") }
    )
}

@Preview(showBackground = true)
@Composable
fun TopUpSuccessDialogPreview() {
    GlideAppTheme {
        Dialog(onDismissRequest = {}) {
            TopUpSuccessDialog(onDismiss = {})
        }
    }
}
