package com.apsl.glideapp.feature.wallet.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.apsl.glideapp.core.ui.FeatureScreen
import com.apsl.glideapp.core.ui.LoadingScreen
import com.apsl.glideapp.core.ui.pullrefresh.PullRefreshIndicator
import com.apsl.glideapp.core.ui.pullrefresh.pullRefresh
import com.apsl.glideapp.core.ui.pullrefresh.rememberPullRefreshState
import com.apsl.glideapp.core.ui.receiveAsLazyPagingItems
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.feature.wallet.components.TransactionItem
import com.apsl.glideapp.feature.wallet.models.AmountType
import com.apsl.glideapp.feature.wallet.models.TransactionUiModel
import com.apsl.glideapp.feature.wallet.viewmodels.AllTransactionsUiState
import com.apsl.glideapp.feature.wallet.viewmodels.AllTransactionsViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber

@Composable
fun AllTransactionsScreen(
    viewModel: AllTransactionsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    viewModel.pagingData.receiveAsLazyPagingItems(action = viewModel::onNewPagerLoadState)
    AllTransactionsScreenContent(
        uiState = uiState,
        onBackClick = onNavigateBack,
        onPullRefresh = viewModel::refresh
    )
}

@Composable
fun AllTransactionsScreenContent(
    uiState: AllTransactionsUiState,
    onBackClick: () -> Unit,
    onPullRefresh: () -> Unit
) {
    FeatureScreen(
        topBarText = "My Transactions",
        onBackClick = onBackClick
    ) {
        when {
            uiState.isLoading -> LoadingScreen()
            uiState.error != null -> {
                Text(text = uiState.error.text, modifier = Modifier.align(Alignment.Center))
            }

            else -> {
                val refreshing = uiState.isRefreshing
                val pullRefreshState =
                    rememberPullRefreshState(refreshing = refreshing, onRefresh = onPullRefresh)

                val map = remember { mutableStateMapOf<String, Double>() }

                LaunchedEffect(map) {
                    map.forEach { Timber.d("key ${it.key} || value: ${it.value}") }
                }

                Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
                    LazyColumn(contentPadding = PaddingValues(16.dp)) {
                        val itemCount = uiState.transactions?.itemCount ?: 0

                        // Workaround for Paging3 separator implementation
                        var lastSeparatorText: String? = null

                        for (index in 0 until itemCount) {
                            val transaction = uiState.transactions?.peek(index)
                            val separatorText = transaction?.separatorText
                            val sum = transaction?.value ?: 0.0

                            if (transaction != null && separatorText != lastSeparatorText) {
                                item(key = separatorText) {
                                    Row(modifier = Modifier.padding(vertical = 16.dp)) {
                                        Text(
                                            text = separatorText.toString(),
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Spacer(Modifier.weight(1f))
                                        Text(
                                            text = "${map.getOrDefault(separatorText, null) ?: ""}",
                                            fontSize = 14.sp,
                                            color = Color.DarkGray
                                        )
                                    }
                                }
                            }

                            item(key = transaction?.id) {
                                // Gets item, triggering page loads if needed
                                LaunchedEffect(Unit) {
                                    Timber.d("composition entered")
                                    val prevValue = map.getOrDefault(separatorText, 0.0)
                                    if (prevValue == 0.0) {
                                        map[separatorText ?: ""] = prevValue + sum
                                    }
                                }
                                uiState.transactions?.peek(index)?.let { transaction ->
                                    TransactionItem(transaction = transaction)
                                }
                            }

                            lastSeparatorText = separatorText
//
//                            if (index != 0) {
//                                Timber.d(itemCount.toString())
//
//                            }
//                            sum2 += sum

                        }
                    }

                    PullRefreshIndicator(
                        refreshing = refreshing,
                        state = pullRefreshState,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AllTransactionsScreenPreview() {
    GlideAppTheme {
        val transactions = MutableStateFlow(
            PagingData.from(
                listOf(
                    TransactionUiModel(
                        id = "1",
                        amount = "- 3.00 PLN",
                        amountType = AmountType.Negative,
                        title = "Account top up",
                        separatorText = "Monday, February 26",
                        dateTime = "26 Feb, 03:13"
                    ),
                    TransactionUiModel(
                        id = "2",
                        amount = "0.00 PLN",
                        amountType = AmountType.Normal,
                        title = "Account top up",
                        separatorText = "Monday, February 26",
                        dateTime = "26 Feb, 03:13"
                    ),
                    TransactionUiModel(
                        id = "3",
                        amount = "+ 3.00 PLN",
                        amountType = AmountType.Positive,
                        title = "Account top up",
                        separatorText = "Monday, February 25",
                        dateTime = "25 Feb, 03:13"
                    )
                )
            )
        ).collectAsLazyPagingItems()

//        AllTransactionsScreenContent(
//            uiState = AllTransactionsUiState(transactions = transactions),
//            onBackClick = {},
//            onPullRefresh = {}
//        )
    }
}
