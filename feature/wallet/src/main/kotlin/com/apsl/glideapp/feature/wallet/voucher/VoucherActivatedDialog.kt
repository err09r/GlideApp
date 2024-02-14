package com.apsl.glideapp.feature.wallet.voucher

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.apsl.glideapp.core.ui.GlideImage
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.ui.R as CoreR

@Composable
fun VoucherActivatedDialog(onDismiss: () -> Unit, modifier: Modifier = Modifier) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(CoreR.string.voucher_activated_dialog_button))
            }
        },
        modifier = modifier,
        icon = { GlideImage(CoreR.drawable.img_gift) },
        title = { Text(text = stringResource(CoreR.string.voucher_activated_dialog_title)) },
        text = { Text(text = stringResource(CoreR.string.voucher_activated_dialog_text)) }
    )
}

@Preview(showBackground = true)
@Composable
private fun VoucherActivatedDialogPreview() {
    GlideAppTheme {
        Dialog(onDismissRequest = {}) {
            VoucherActivatedDialog(onDismiss = {})
        }
    }
}
