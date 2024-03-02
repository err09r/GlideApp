package com.apsl.glideapp.feature.wallet.wallet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apsl.glideapp.core.ui.ComposableLifecycle
import com.apsl.glideapp.core.ui.FeatureScreen
import com.apsl.glideapp.core.ui.GlideCircularLoadingIndicator
import com.apsl.glideapp.core.ui.PagingSeparator
import com.apsl.glideapp.core.ui.Separator
import com.apsl.glideapp.core.ui.icons.Bonus
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.icons.TopUp
import com.apsl.glideapp.core.ui.icons.Voucher
import com.apsl.glideapp.core.ui.pulltorefresh.Indicator
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.feature.wallet.common.AmountType
import com.apsl.glideapp.feature.wallet.common.TransactionItem
import com.apsl.glideapp.feature.wallet.common.TransactionUiModel
import com.apsl.glideapp.core.ui.R as CoreR

@Composable
fun WalletScreen(
    onNavigateBack: () -> Unit,
    onNavigateToTransactions: () -> Unit,
    onNavigateToTopUp: () -> Unit,
    onNavigateToRedeemVoucher: () -> Unit,
    viewModel: WalletViewModel = hiltViewModel()
) {
    ComposableLifecycle { event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                viewModel.run {
                    getUserBalance()
                    getUserTransactions()
                }
            }

            else -> Unit
        }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    WalletScreenContent(
        uiState = uiState,
        onBackClick = onNavigateBack,
        onSeeAllTransactionsClick = onNavigateToTransactions,
        onRedeemVoucherClick = onNavigateToRedeemVoucher,
        onTopUpClick = onNavigateToTopUp,
        onPullToRefresh = viewModel::refresh
    )
}

@Composable
fun WalletScreenContent(
    uiState: WalletUiState,
    onBackClick: () -> Unit,
    onSeeAllTransactionsClick: () -> Unit,
    onRedeemVoucherClick: () -> Unit,
    onTopUpClick: () -> Unit,
    onPullToRefresh: () -> Unit
) {
    FeatureScreen(
        topBarText = stringResource(CoreR.string.wallet_screen_title),
        onBackClick = onBackClick
    ) {
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

        val pagerItems = remember {
            listOf(
                WalletPagerItem(
                    id = 1,
                    titleResId = CoreR.string.wallet_pager_item_title1,
                    textResId = CoreR.string.wallet_pager_item_text1,
                    imageResId = CoreR.drawable.img_gift_front,
                    onClick = onRedeemVoucherClick
                ),
                WalletPagerItem(
                    id = 2,
                    titleResId = CoreR.string.wallet_pager_item_title2,
                    textResId = CoreR.string.wallet_pager_item_text2,
                    imageResId = CoreR.drawable.img_card_front,
                    onClick = {}
                )
            ).toWalletPagerItems()
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(pullToRefreshState.nestedScrollConnection)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(16.dp))

                BalanceCard(
                    value = uiState.userBalance,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onAddMoneyClick = onTopUpClick
                )

                Spacer(Modifier.height(16.dp))

                WalletPager(
                    items = pagerItems,
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                )

                Spacer(Modifier.height(32.dp))

                Separator(
                    text = stringResource(CoreR.string.recent_transactions_header),
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 16.dp)
                )

                when {
                    uiState.isLoading -> {
                        Box(modifier = Modifier.weight(1f)) {
                            GlideCircularLoadingIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }

                    else -> {
                        Spacer(Modifier.height(8.dp))
                        for (transaction in uiState.recentTransactions) {
                            TransactionItem(
                                transaction = transaction,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        Spacer(Modifier.height(24.dp))
                        TextButton(onClick = onSeeAllTransactionsClick) {
                            Text(text = stringResource(CoreR.string.recent_transactions_footer))
                        }
                        Spacer(Modifier.height(32.dp))
                    }
                }
            }

            PullToRefreshContainer(
                state = pullToRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                indicator = { Indicator(state = it) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WalletScreenPreview() {
    GlideAppTheme {
        WalletScreenContent(
            uiState = WalletUiState(
                recentTransactions = listOf(
                    TransactionUiModel(
                        id = "1",
                        amount = "-3,00",
                        amountType = AmountType.Negative,
                        titleResId = CoreR.string.transaction_type_top_up,
                        image = GlideIcons.TopUp,
                        separator = PagingSeparator("Monday, February 25"),
                        dateTime = "26 Feb, 03:13"
                    ),
                    TransactionUiModel(
                        id = "2",
                        amount = "0,00",
                        amountType = AmountType.Normal,
                        titleResId = CoreR.string.transaction_type_top_up,
                        image = GlideIcons.Bonus,
                        separator = PagingSeparator("Monday, February 25"),
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
            ),
            onBackClick = {},
            onSeeAllTransactionsClick = {},
            onRedeemVoucherClick = {},
            onTopUpClick = {},
            onPullToRefresh = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WalletScreenLoadingPreview() {
    GlideAppTheme {
        WalletScreenContent(
            uiState = WalletUiState(isLoading = true),
            onBackClick = {},
            onSeeAllTransactionsClick = {},
            onRedeemVoucherClick = {},
            onTopUpClick = {},
            onPullToRefresh = {}
        )
    }
}
