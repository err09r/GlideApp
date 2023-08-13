package com.apsl.glideapp.feature.wallet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Paid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.feature.wallet.models.AmountType
import com.apsl.glideapp.feature.wallet.models.TransactionUiModel

@Composable
fun TransactionItem(transaction: TransactionUiModel, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(color = Color.White, shape = CircleShape)
            .border(width = Dp.Hairline, color = Color.LightGray, shape = CircleShape)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            Icon(
                imageVector = Icons.Rounded.Paid,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = Color.DarkGray
            )
        }
        Spacer(Modifier.width(16.dp))
        Column {
            Text(text = transaction.title, fontSize = 16.sp)
            Text(text = transaction.dateTime, fontSize = 12.sp)
        }
        Spacer(Modifier.weight(1f))
        TransactionAmount(value = transaction.amount, type = transaction.amountType)
    }
}

@Preview
@Composable
fun TransactionItemPreview() {
    GlideAppTheme {
        TransactionItem(
            transaction = TransactionUiModel(
                id = "",
                amount = "+ 3.00 PLN",
                amountType = AmountType.Positive,
                title = "Account top up",
                separatorText = "Monday, February 25",
                dateTime = "26 Feb, 03:13"
            )
        )
    }
}
