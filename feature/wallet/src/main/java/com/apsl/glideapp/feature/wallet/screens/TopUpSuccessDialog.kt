package com.apsl.glideapp.feature.wallet.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.apsl.glideapp.core.ui.theme.GlideAppTheme

@Composable
fun TopUpSuccessDialog() {
    Surface(
        color = Color.White,
        elevation = 16.dp
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Your balance has been successfully topped up",
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(16.dp))
            Text(text = "Have a nice ride!", textAlign = TextAlign.Center)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopUpSuccessDialogPreview() {
    GlideAppTheme {
        Dialog(onDismissRequest = {}) {
            TopUpSuccessDialog()
        }
    }
}
