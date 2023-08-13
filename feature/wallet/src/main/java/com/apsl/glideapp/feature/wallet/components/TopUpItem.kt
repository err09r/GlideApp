package com.apsl.glideapp.feature.wallet.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.apsl.glideapp.core.ui.theme.GlideAppTheme

@Composable
fun TopUpItem(onClick: () -> Unit) {
    WalletPagerItem(text = "Top up your account", image = Icons.Rounded.Payments, onClick = onClick)
}

@Preview
@Composable
fun TopUpItemPreview() {
    GlideAppTheme {
        TopUpItem(onClick = {})
    }
}
