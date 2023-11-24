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
fun VoucherActivatedDialog(onDismiss: () -> Unit, modifier: Modifier = Modifier) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Continue")
            }
        },
        modifier = modifier,
        icon = { GlideImage(CoreR.drawable.img_gift) },
        title = { Text(text = "Success!") },
        text = { Text(text = "Voucher code has been activated. Funds will be added to your account") }
    )
}

@Preview(showBackground = true)
@Composable
fun VoucherActivatedDialogPreview() {
    GlideAppTheme {
        Dialog(onDismissRequest = {}) {
            VoucherActivatedDialog(onDismiss = {})
        }
    }
}
