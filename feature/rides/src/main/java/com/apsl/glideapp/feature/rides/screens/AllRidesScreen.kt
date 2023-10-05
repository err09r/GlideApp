package com.apsl.glideapp.feature.rides.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.apsl.glideapp.core.ui.components.Graph
import com.apsl.glideapp.core.ui.receiveAsLazyPagingItems
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.feature.rides.models.RideUiModel
import com.apsl.glideapp.feature.rides.viewmodels.AllRidesUiState
import com.apsl.glideapp.feature.rides.viewmodels.AllRidesViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun AllRidesScreen(
    viewModel: AllRidesViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToRide: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    viewModel.pagingData.receiveAsLazyPagingItems(action = viewModel::onNewPagerLoadState)
    AllRidesScreenContent(
        uiState = uiState,
        onBackClick = onNavigateBack,
        onRideClick = onNavigateToRide,
        onPullRefresh = viewModel::refresh
    )
}

@Composable
fun AllRidesScreenContent(
    uiState: AllRidesUiState,
    onBackClick: () -> Unit,
    onRideClick: (String) -> Unit,
    onPullRefresh: () -> Unit
) {
    FeatureScreen(
        topBarText = "My Rides",
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

                Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
                    LazyColumn(contentPadding = PaddingValues(16.dp)) {
                        val itemCount = uiState.rides?.itemCount ?: 0
                        var lastSeparatorText: String? = null

                        for (index in 0 until itemCount) {
                            val ride = uiState.rides?.peek(index)
                            val separatorText = ride?.separatorText

                            if (ride != null && separatorText != lastSeparatorText) {
                                item(key = separatorText) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp)
                                    ) {
                                        Text(
                                            text = separatorText.toString(),
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            } else {
                                item {
                                    Spacer(Modifier.height(8.dp))
                                }
                            }

                            item(key = ride?.id) {
                                // Gets item, triggering page loads if needed
                                uiState.rides?.peek(index)?.let { ride ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(color = Color.White)
                                            .clickable(onClick = { onRideClick(ride.id) })
                                            .padding(16.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(
                                                text = "${ride.startTime} - ${ride.finishTime}",
                                                color = Color.Black.copy(alpha = 0.7f)
                                            )
                                            Spacer(Modifier.height(16.dp))
                                            Text(text = "${ride.distance} meters")
                                        }
                                        Graph(
                                            points = ride.route,
                                            modifier = Modifier.size(64.dp),
                                            contentPadding = PaddingValues(2.dp)
                                        )
                                        Text(
                                            text = "${ride.fare} PLN", fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }

                            lastSeparatorText = separatorText
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
fun AllRidesScreenPreview() {
    GlideAppTheme {
        val rides = MutableStateFlow(
            PagingData.from(
                listOf(
                    RideUiModel(
                        id = "1",
                        startTime = "16:01",
                        finishTime = "16:02",
                        route = listOf(1f to 1f),
                        distance = 426,
                        fare = "4.05",
                        separatorText = "Monday, February 25"
                    ),
                    RideUiModel(
                        id = "2",
                        startTime = "16:01",
                        finishTime = "16:02",
                        route = listOf(1f to 1f),
                        distance = 426,
                        fare = "4.05",
                        separatorText = "Monday, February 25"
                    ),
                    RideUiModel(
                        id = "3",
                        startTime = "16:01",
                        finishTime = "16:02",
                        route = listOf(1f to 1f),
                        distance = 426,
                        fare = "4.05",
                        separatorText = "Monday, February 26"
                    )
                )
            )
        ).collectAsLazyPagingItems()

        AllRidesScreenContent(
            uiState = AllRidesUiState(rides = rides),
            onBackClick = {},
            onRideClick = {},
            onPullRefresh = {}
        )
    }
}
