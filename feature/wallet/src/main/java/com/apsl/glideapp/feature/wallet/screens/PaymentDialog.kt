package com.apsl.glideapp.feature.wallet.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.apsl.glideapp.core.ui.theme.GlideAppTheme

@Composable
fun PaymentDialog() {
    Surface(
        color = Color.White,
        elevation = 16.dp
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Processing payment...")
            Spacer(Modifier.height(32.dp))
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentDialogPreview() {
    GlideAppTheme {
        Dialog(onDismissRequest = {}) {
            PaymentDialog()
        }
    }
}
