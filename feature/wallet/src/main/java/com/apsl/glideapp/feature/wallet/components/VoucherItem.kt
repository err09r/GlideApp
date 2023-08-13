package com.apsl.glideapp.feature.wallet.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Redeem
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.apsl.glideapp.core.ui.theme.GlideAppTheme

@Composable
fun VoucherItem(onClick: () -> Unit) {
    WalletPagerItem(text = "Redeem voucher", image = Icons.Rounded.Redeem, onClick = onClick)
}

@Preview(showBackground = true)
@Composable
fun VoucherItemPreview() {
    GlideAppTheme {
        VoucherItem(onClick = {})
    }
}
