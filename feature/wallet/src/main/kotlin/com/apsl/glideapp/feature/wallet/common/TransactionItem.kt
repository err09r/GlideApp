package com.apsl.glideapp.feature.wallet.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apsl.glideapp.core.ui.PagingSeparator
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.icons.TopUp
import com.apsl.glideapp.core.ui.theme.GlideAppTheme

@Composable
fun TransactionItem(transaction: TransactionUiModel, modifier: Modifier = Modifier) {
    ListItem(
        headlineContent = { Text(text = transaction.title) },
        modifier = modifier.clickable {},
        supportingContent = { Text(text = transaction.dateTime) },
        leadingContent = {
            Icon(
                imageVector = transaction.image,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.secondary
            )
        },
        trailingContent = {
            TransactionAmount(value = transaction.amount, type = transaction.amountType)
        }
    )
//    Card(onClick = {}, modifier = modifier) {
//        Row(
//            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Box {
//                Icon(
//                    imageVector = transaction.image,
//                    contentDescription = null,
//                    modifier = Modifier.size(48.dp),
//                    tint = MaterialTheme.colorScheme.tertiary
//                )
//            }
//            Spacer(Modifier.width(16.dp))
//            Column {
//                Text(text = transaction.title, fontSize = 16.sp)
//                Text(text = transaction.dateTime, fontSize = 12.sp)
//            }
//            Spacer(Modifier.weight(1f))
//            TransactionAmount(value = transaction.amount, type = transaction.amountType)
//        }
//    }
}

@Preview
@Composable
private fun TransactionItemPreview() {
    GlideAppTheme {
        TransactionItem(
            transaction = TransactionUiModel(
                id = "",
                amount = "+3,00 zł",
                amountType = AmountType.Positive,
                title = "Account top up",
                image = GlideIcons.TopUp,
                separator = PagingSeparator("Monday, February 25"),
                dateTime = "26 Feb, 03:13"
            )
        )
    }
}
