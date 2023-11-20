package com.apsl.glideapp.feature.wallet.screens

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apsl.glideapp.core.ui.ComposableLifecycle
import com.apsl.glideapp.core.ui.FeatureScreen
import com.apsl.glideapp.core.ui.GlideCircularLoadingIndicator
import com.apsl.glideapp.core.ui.PagingSeparator
import com.apsl.glideapp.core.ui.components.Separator
import com.apsl.glideapp.core.ui.icons.Bonus
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.icons.TopUp
import com.apsl.glideapp.core.ui.icons.Voucher
import com.apsl.glideapp.core.ui.pullrefresh.PullRefreshIndicator
import com.apsl.glideapp.core.ui.pullrefresh.pullRefresh
import com.apsl.glideapp.core.ui.pullrefresh.rememberPullRefreshState
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.feature.wallet.components.BalanceCard
import com.apsl.glideapp.feature.wallet.components.TransactionItem
import com.apsl.glideapp.feature.wallet.components.WalletPager
import com.apsl.glideapp.feature.wallet.components.WalletPagerItem
import com.apsl.glideapp.feature.wallet.models.AmountType
import com.apsl.glideapp.feature.wallet.models.TransactionUiModel
import com.apsl.glideapp.feature.wallet.viewmodels.WalletUiState
import com.apsl.glideapp.feature.wallet.viewmodels.WalletViewModel
import com.apsl.glideapp.core.ui.R as CoreR

@Composable
fun WalletScreen(
    viewModel: WalletViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToTransactions: () -> Unit,
    onNavigateToTopUp: () -> Unit,
    onNavigateToRedeemVoucher: () -> Unit
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
        onPullRefresh = viewModel::refresh
    )
}

@Composable
fun WalletScreenContent(
    uiState: WalletUiState,
    onBackClick: () -> Unit,
    onSeeAllTransactionsClick: () -> Unit,
    onRedeemVoucherClick: () -> Unit,
    onTopUpClick: () -> Unit,
    onPullRefresh: () -> Unit
) {
    FeatureScreen(
        topBarText = "My wallet",
        onBackClick = onBackClick
    ) {
        val refreshing = uiState.isRefreshing
        val pullRefreshState =
            rememberPullRefreshState(
                refreshing = uiState.isRefreshing,
                onRefresh = onPullRefresh
            )

        val pagerItems = remember {
            listOf(
                WalletPagerItem(
                    id = 1,
                    title = "Redeem voucher",
                    text = "Have a code? Activate it and enjoy the ride!",
                    imageResId = CoreR.drawable.img_gift_front,
                    onClick = onRedeemVoucherClick
                ),
                WalletPagerItem(
                    id = 2,
                    title = "Add payment method",
                    text = "More methods for more convenience",
                    imageResId = CoreR.drawable.img_card_front,
                    onClick = {}
                )
            )
        }

        Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
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
                    text = "Recent transactions",
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
                            Text(text = "See all transactions")
                        }
                        Spacer(Modifier.height(32.dp))
                    }
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

@Preview(showBackground = true)
@Composable
fun WalletScreenPreview() {
    GlideAppTheme {
        WalletScreenContent(
            uiState = WalletUiState(
                recentTransactions = listOf(
                    TransactionUiModel(
                        id = "1",
                        amount = "-3,00 zł",
                        amountType = AmountType.Negative,
                        title = "Account top up",
                        image = GlideIcons.TopUp,
                        separator = PagingSeparator("Monday, February 25"),
                        dateTime = "26 Feb, 03:13"
                    ),
                    TransactionUiModel(
                        id = "2",
                        amount = "0,00 zł",
                        amountType = AmountType.Normal,
                        title = "Account top up",
                        image = GlideIcons.Bonus,
                        separator = PagingSeparator("Monday, February 25"),
                        dateTime = "26 Feb, 03:13"
                    ),
                    TransactionUiModel(
                        id = "3",
                        amount = "+3,00 zł",
                        amountType = AmountType.Positive,
                        title = "Account top up",
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
            onPullRefresh = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WalletScreenLoadingPreview() {
    GlideAppTheme {
        WalletScreenContent(
            uiState = WalletUiState(isLoading = true),
            onBackClick = {},
            onSeeAllTransactionsClick = {},
            onRedeemVoucherClick = {},
            onTopUpClick = {},
            onPullRefresh = {}
        )
    }
}
