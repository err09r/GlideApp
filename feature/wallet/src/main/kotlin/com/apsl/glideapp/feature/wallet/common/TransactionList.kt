package com.apsl.glideapp.feature.wallet.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.apsl.glideapp.core.ui.ComposePagingItems
import com.apsl.glideapp.core.ui.PagingSeparator
import com.apsl.glideapp.core.ui.Separator
import com.apsl.glideapp.core.ui.icons.Bonus
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.icons.TopUp
import com.apsl.glideapp.core.ui.icons.Voucher
import com.apsl.glideapp.core.ui.paddingBeforeSeparator
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.ui.toComposePagingItems
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun TransactionList(
    transactions: ComposePagingItems<TransactionUiModel>?,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val itemCount = transactions?.itemCount ?: 0
    // Change once https://issuetracker.google.com/issues/264237280 is added
    val indicesBeforeSeparators = remember(transactions) { mutableStateMapOf<Int, Int>() }

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        var lastSeparatorText: String? = null

        for (index in 0 until itemCount) {
            val transaction = transactions?.peekOrNull(index)

            if (transaction != null) {
                val (separatorText, separatorId) = transaction.separator

                if (separatorText != lastSeparatorText) {
                    stickyHeader(key = separatorId) {
                        indicesBeforeSeparators[index - 1] = index - 1
                        Separator(
                            text = separatorText,
                            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                        )
                    }
                } else {
                    item {
                        Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    }
                }

                item(key = transaction.id) {
                    // Gets item, triggering page loads if needed
                    transactions[index]?.let { transaction ->
                        TransactionItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .paddingBeforeSeparator(apply = index in indicesBeforeSeparators),
                            transaction = transaction
                        )
                    }
                }

                lastSeparatorText = separatorText
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionListPreview() {
    GlideAppTheme {
        val transactions = MutableStateFlow(
            PagingData.from(
                listOf(
                    TransactionUiModel(
                        id = "1",
                        amount = "-3,00 zł",
                        amountType = AmountType.Negative,
                        title = "Account top up",
                        image = GlideIcons.TopUp,
                        separator = PagingSeparator("Monday, February 26"),
                        dateTime = "26 Feb, 03:13"
                    ),
                    TransactionUiModel(
                        id = "2",
                        amount = "0,00 zł",
                        amountType = AmountType.Normal,
                        title = "Account top up",
                        image = GlideIcons.Bonus,
                        separator = PagingSeparator("Monday, February 26"),
                        dateTime = "26 Feb, 03:13"
                    ),
                    TransactionUiModel(
                        id = "3",
                        amount = "+3,00 zł",
                        amountType = AmountType.Positive,
                        title = "Account top up",
                        image = GlideIcons.Voucher,
                        separator = PagingSeparator("Monday, February 26"),
                        dateTime = "25 Feb, 03:13"
                    ),
                    TransactionUiModel(
                        id = "4",
                        amount = "-3,00 zł",
                        amountType = AmountType.Negative,
                        title = "Account top up",
                        image = GlideIcons.TopUp,
                        separator = PagingSeparator("Monday, February 26"),
                        dateTime = "26 Feb, 03:13"
                    ),
                    TransactionUiModel(
                        id = "5",
                        amount = "0,00 zł",
                        amountType = AmountType.Normal,
                        title = "Account top up",
                        image = GlideIcons.Bonus,
                        separator = PagingSeparator("Monday, February 26"),
                        dateTime = "26 Feb, 03:13"
                    ),
                    TransactionUiModel(
                        id = "6",
                        amount = "+3,00 zł",
                        amountType = AmountType.Positive,
                        title = "Account top up",
                        image = GlideIcons.Voucher,
                        separator = PagingSeparator("Monday, February 20"),
                        dateTime = "25 Feb, 03:13"
                    ),
                    TransactionUiModel(
                        id = "7",
                        amount = "+3,00 zł",
                        amountType = AmountType.Positive,
                        title = "Account top up",
                        image = GlideIcons.Voucher,
                        separator = PagingSeparator("Monday, February 20"),
                        dateTime = "25 Feb, 03:13"
                    ),
                    TransactionUiModel(
                        id = "8",
                        amount = "+3,00 zł",
                        amountType = AmountType.Positive,
                        title = "Account top up",
                        image = GlideIcons.Voucher,
                        separator = PagingSeparator("Monday, February 20"),
                        dateTime = "25 Feb, 03:13"
                    )
                )
            )
        )
            .collectAsLazyPagingItems()
            .toComposePagingItems()

        TransactionList(transactions = transactions)
    }
}
