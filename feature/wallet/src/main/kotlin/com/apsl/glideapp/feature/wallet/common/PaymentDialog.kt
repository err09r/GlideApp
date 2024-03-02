package com.apsl.glideapp.feature.wallet.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.apsl.glideapp.core.ui.GlideCircularLoadingIndicator
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.ui.R as CoreR

@Composable
fun PaymentDialog(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = AlertDialogDefaults.shape,
        color = AlertDialogDefaults.containerColor,
        tonalElevation = AlertDialogDefaults.TonalElevation
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(text = stringResource(CoreR.string.payment_dialog_description))
            Spacer(Modifier.height(24.dp))
            GlideCircularLoadingIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PaymentDialogPreview() {
    GlideAppTheme {
        Dialog(onDismissRequest = {}) {
            PaymentDialog()
        }
    }
}
