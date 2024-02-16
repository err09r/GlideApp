package com.apsl.glideapp.feature.wallet.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apsl.glideapp.core.ui.PagingSeparator
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.icons.TopUp
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.ui.R as CoreR

@Composable
fun TransactionItem(transaction: TransactionUiModel, modifier: Modifier = Modifier) {
    ListItem(
        headlineContent = { Text(text = stringResource(transaction.titleResId)) },
        modifier = modifier.clickable {},
        supportingContent = { Text(text = transaction.dateTime) },
        leadingContent = {
            Icon(
                imageVector = transaction.image,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        },
        trailingContent = {
            TransactionAmount(value = transaction.amount, type = transaction.amountType)
        }
    )
}

@Preview
@Composable
private fun TransactionItemPreview() {
    GlideAppTheme {
        TransactionItem(
            transaction = TransactionUiModel(
                id = "1",
                amount = "+3,00",
                amountType = AmountType.Positive,
                titleResId = CoreR.string.transaction_type_top_up,
                image = GlideIcons.TopUp,
                separator = PagingSeparator("Monday, February 25"),
                dateTime = "26 Feb, 03:13"
            )
        )
    }
}
