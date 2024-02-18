package com.apsl.glideapp.feature.wallet.transactions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.apsl.glideapp.core.ui.FeatureScreen
import com.apsl.glideapp.core.ui.LoadingScreen
import com.apsl.glideapp.core.ui.PagingSeparator
import com.apsl.glideapp.core.ui.icons.Bonus
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.icons.TopUp
import com.apsl.glideapp.core.ui.icons.Voucher
import com.apsl.glideapp.core.ui.pulltorefresh.Indicator
import com.apsl.glideapp.core.ui.receiveAsLazyPagingItems
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.ui.toComposePagingItems
import com.apsl.glideapp.feature.wallet.common.AmountType
import com.apsl.glideapp.feature.wallet.common.TransactionList
import com.apsl.glideapp.feature.wallet.common.TransactionUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import com.apsl.glideapp.core.ui.R as CoreR

@Composable
fun AllTransactionsScreen(
    viewModel: AllTransactionsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    viewModel.pagingData.receiveAsLazyPagingItems(action = viewModel::onNewPagerLoadState)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    AllTransactionsScreenContent(
        uiState = uiState,
        onBackClick = onNavigateBack,
        onPullToRefresh = viewModel::refresh
    )
}

@Composable
fun AllTransactionsScreenContent(
    uiState: AllTransactionsUiState,
    onBackClick: () -> Unit,
    onPullToRefresh: () -> Unit
) {
    FeatureScreen(
        topBarText = stringResource(CoreR.string.all_transactions_screen_title),
        onBackClick = onBackClick
    ) {
        when {
            uiState.isLoading -> LoadingScreen()
            uiState.error != null -> {
                Text(
                    text = stringResource(uiState.error.textResId),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {
                val pullToRefreshState = rememberPullToRefreshState()
                if (pullToRefreshState.isRefreshing) {
                    LaunchedEffect(Unit) {
                        onPullToRefresh()
                    }
                }

                if (!uiState.isRefreshing) {
                    LaunchedEffect(Unit) {
                        pullToRefreshState.endRefresh()
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(pullToRefreshState.nestedScrollConnection)
                ) {
                    TransactionList(
                        transactions = uiState.transactions,
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp)
                    )
                }

                PullToRefreshContainer(
                    state = pullToRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter),
                    indicator = { Indicator(state = it) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AllTransactionsScreenPreview() {
    GlideAppTheme {
        val transactions = MutableStateFlow(
            PagingData.from(
                listOf(
                    TransactionUiModel(
                        id = "1",
                        amount = "-3,00",
                        amountType = AmountType.Negative,
                        titleResId = CoreR.string.transaction_type_top_up,
                        image = GlideIcons.TopUp,
                        separator = PagingSeparator("Monday, February 26"),
                        dateTime = "26 Feb, 03:13"
                    ),
                    TransactionUiModel(
                        id = "2",
                        amount = "0,00",
                        amountType = AmountType.Normal,
                        titleResId = CoreR.string.transaction_type_top_up,
                        image = GlideIcons.Bonus,
                        separator = PagingSeparator("Monday, February 26"),
                        dateTime = "26 Feb, 03:13"
                    ),
                    TransactionUiModel(
                        id = "3",
                        amount = "+3,00",
                        amountType = AmountType.Positive,
                        titleResId = CoreR.string.transaction_type_top_up,
                        image = GlideIcons.Voucher,
                        separator = PagingSeparator("Monday, February 25"),
                        dateTime = "25 Feb, 03:13"
                    )
                )
            )
        )
            .collectAsLazyPagingItems()
            .toComposePagingItems()

        AllTransactionsScreenContent(
            uiState = AllTransactionsUiState(transactions = transactions),
            onBackClick = {},
            onPullToRefresh = {}
        )
    }
}
