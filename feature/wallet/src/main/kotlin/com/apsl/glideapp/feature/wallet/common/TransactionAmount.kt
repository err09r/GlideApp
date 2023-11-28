package com.apsl.glideapp.feature.wallet.common

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.apsl.glideapp.core.ui.theme.GlideAppTheme

@Composable
fun TransactionAmount(value: String, type: AmountType) {
    Text(
        text = value,
        color = when (type) {
            AmountType.Negative -> Color(0xFFE34945)
            AmountType.Positive -> Color(0xFF088237)
            else -> MaterialTheme.colorScheme.onSurfaceVariant
        },
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    )
}

@Preview(showBackground = true)
@Composable
private fun TransactionAmountPreview() {
    GlideAppTheme {
        Column {
            TransactionAmount(value = "-3,00 zł", type = AmountType.Negative)
            TransactionAmount(value = "0,00 zł", type = AmountType.Normal)
            TransactionAmount(value = "-3,00 zł", type = AmountType.Positive)
        }
    }
}
