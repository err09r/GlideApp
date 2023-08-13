package com.apsl.glideapp.feature.wallet.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apsl.glideapp.core.ui.FeatureScreen
import com.apsl.glideapp.core.ui.LoadingScreen
import com.apsl.glideapp.core.ui.noRippleClickable
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.feature.wallet.components.BalanceItem
import com.apsl.glideapp.feature.wallet.components.TopUpItem
import com.apsl.glideapp.feature.wallet.components.TransactionItem
import com.apsl.glideapp.feature.wallet.components.VoucherItem
import com.apsl.glideapp.feature.wallet.components.WalletPager
import com.apsl.glideapp.feature.wallet.models.AmountType
import com.apsl.glideapp.feature.wallet.models.TransactionUiModel
import com.apsl.glideapp.feature.wallet.viewmodels.WalletUiState
import com.apsl.glideapp.feature.wallet.viewmodels.WalletViewModel

@Composable
fun WalletScreen(
    viewModel: WalletViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToTransactions: () -> Unit,
    onNavigateToTopUp: () -> Unit,
    onNavigateToRedeemVoucher: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.run {
            getUserBalance()
            getUserTransactions()
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
    onPullRefresh: () -> Unit,
) {
    FeatureScreen(
        topBarText = "My Wallet",
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
                    rememberPullRefreshState(
                        refreshing = uiState.isRefreshing,
                        onRefresh = onPullRefresh
                    )

                Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(Modifier.height(48.dp))

                        WalletPager(
                            {
                                BalanceItem(
                                    balance = uiState.userBalance,
                                    isRentalAvailable = uiState.isRentalAvailable,
                                    onClick = {}
                                )
                            },
                            { VoucherItem(onClick = onRedeemVoucherClick) },
                            { TopUpItem(onClick = onTopUpClick) }
                        )

                        Spacer(Modifier.height(64.dp))

                        Text(
                            text = "Recent Transactions",
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(start = 16.dp),
                            color = Color.Black.copy(alpha = 0.7f)
                        )

                        Spacer(Modifier.height(16.dp))

                        uiState.recentTransactions.forEachIndexed { index, transaction ->
                            TransactionItem(
                                transaction = transaction,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            )
                            if (index != uiState.recentTransactions.lastIndex) {
                                Spacer(Modifier.height(8.dp))
                            } else {
                                Spacer(Modifier.height(32.dp))
//                            Text(
//                                text = "See all transactions",
//                                modifier = Modifier.noRippleClickable(onClick = onSeeAllTransactionsClick),
//                                fontWeight = FontWeight.Medium
//                            )
                            }
                        }

                        Text(
                            text = "See all transactions",
                            modifier = Modifier.noRippleClickable(onClick = onSeeAllTransactionsClick),
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(Modifier.height(32.dp))
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
fun WalletScreenPreview() {
    GlideAppTheme {
        WalletScreenContent(
            uiState = WalletUiState(
                recentTransactions = listOf(
                    TransactionUiModel(
                        id = "1",
                        amount = "- 3.00 PLN",
                        amountType = AmountType.Negative,
                        title = "Account top up",
                        separatorText = "Monday, February 25",
                        dateTime = "26 Feb, 03:13"
                    ),
                    TransactionUiModel(
                        id = "2",
                        amount = "0.00 PLN",
                        amountType = AmountType.Normal,
                        title = "Account top up",
                        separatorText = "Monday, February 25",
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
            ),
            onBackClick = {},
            onSeeAllTransactionsClick = {},
            onRedeemVoucherClick = {},
            onTopUpClick = {},
            onPullRefresh = {}
        )
    }
}
