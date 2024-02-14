package com.apsl.glideapp.feature.wallet.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.ui.R as CoreR

@Composable
fun TransactionAmount(value: String, type: AmountType) {
    Text(
        text = stringResource(CoreR.string.value_zloty, value),
        color = when (type) {
            AmountType.Negative -> Color(0xFFE34945) // TODO: Replace hardcoded colors with adaptive custom colors
            AmountType.Positive -> Color(0xFF088237) //
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
        Column(modifier = Modifier.padding(16.dp)) {
            TransactionAmount(value = "-3,00", type = AmountType.Negative)
            TransactionAmount(value = "0,00", type = AmountType.Normal)
            TransactionAmount(value = "-3,00", type = AmountType.Positive)
        }
    }
}
