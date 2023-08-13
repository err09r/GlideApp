package com.apsl.glideapp.feature.wallet.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.feature.wallet.models.AmountType

@Composable
fun TransactionAmount(value: String, type: AmountType) {
    Text(
        text = value,
        color = when (type) {
            AmountType.Negative -> Color.Red
            AmountType.Positive -> Color(0xFF00B300)
            else -> Color.DarkGray
        },
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold
    )
}

@Preview(showBackground = true)
@Composable
fun TransactionAmountPreview() {
    GlideAppTheme {
        Column {
            TransactionAmount(value = "- 3.00 PLN", type = AmountType.Normal)
            TransactionAmount(value = "0.00 PLN", type = AmountType.Normal)
            TransactionAmount(value = "- 3.00 PLN", type = AmountType.Normal)
        }
    }
}
