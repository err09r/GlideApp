package com.apsl.glideapp.feature.wallet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apsl.glideapp.core.ui.theme.GlideAppTheme

@Composable
fun BalanceItem(balance: String, isRentalAvailable: Boolean, onClick: () -> Unit) {
    WalletPagerItem(
        text = "Your balance $balance",
        image = Icons.Rounded.AccountBalanceWallet,
        onClick = onClick
    ) {
        if (!isRentalAvailable) {
            Box(
                modifier = Modifier.background(
                    color = Color.Red,
                    shape = CircleShape
                )
            ) {
                Text(
                    text = "RENTAL BLOCKED",
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = Color.White,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WalletItemPreview() {
    GlideAppTheme {
        BalanceItem(balance = "12345.67 PLN", isRentalAvailable = false, onClick = {})
    }
}
