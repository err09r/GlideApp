package com.apsl.glideapp.feature.rides.allrides

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.apsl.glideapp.core.ui.FeatureScreen
import com.apsl.glideapp.core.ui.LoadingScreen
import com.apsl.glideapp.core.ui.PagingSeparator
import com.apsl.glideapp.core.ui.icons.ElectricScooter
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.icons.NotificationRemove
import com.apsl.glideapp.core.ui.icons.Route
import com.apsl.glideapp.core.ui.pulltorefresh.Indicator
import com.apsl.glideapp.core.ui.receiveAsLazyPagingItems
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.ui.toComposePagingItems
import com.apsl.glideapp.core.util.android.NumberFormatter
import kotlinx.coroutines.flow.MutableStateFlow
import com.apsl.glideapp.core.ui.R as CoreR

@Composable
fun AllRidesScreen(
    viewModel: AllRidesViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToRide: (String) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.refreshRidesSummary()
    }

    viewModel.pagingData.receiveAsLazyPagingItems(action = viewModel::onNewPagerLoadState)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AllRidesScreenContent(
        uiState = uiState,
        onBackClick = onNavigateBack,
        onRideClick = onNavigateToRide,
        onPullToRefresh = viewModel::refresh
    )
}

@Composable
fun AllRidesScreenContent(
    uiState: AllRidesUiState,
    onBackClick: () -> Unit,
    onRideClick: (String) -> Unit,
    onPullToRefresh: () -> Unit
) {
    FeatureScreen(
        topBarText = stringResource(CoreR.string.all_rides_screen_title),
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
                    val itemCount = uiState.rides?.itemCount ?: 0

                    if (itemCount == 0) {
                        AllRidesEmptyScreen()
                    } else {
                        Column {
                            RideStats(
                                rides = uiState.totalRides,
                                distance = uiState.totalDistanceMeters
                            )
                            RideList(
                                rides = uiState.rides,
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(bottom = 32.dp),
                                onRideClick = onRideClick
                            )
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
    }
}

@Composable
fun RideStats(modifier: Modifier = Modifier, rides: String, distance: String) {
    ElevatedCard(
        onClick = {},
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RideStatsItem(
                title = stringResource(CoreR.string.all_rides_stats_travelled),
                text = stringResource(CoreR.string.value_kilometers, distance),
                imageVector = GlideIcons.Route
            )
            Spacer(Modifier.weight(1f))
            RideStatsItem(
                title = stringResource(CoreR.string.all_rides_stats_rides),
                text = rides,
                imageVector = GlideIcons.ElectricScooter
            )
        }
    }
}

@Composable
fun RideStatsItem(
    modifier: Modifier = Modifier,
    title: String,
    text: String,
    imageVector: ImageVector
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = CardDefaults.shape
                )
                .padding(8.dp)
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(Modifier.width(20.dp))
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview
@Composable
private fun RideStatsPreview() {
    GlideAppTheme {
        RideStats(
            rides = NumberFormatter.format(12),
            distance = NumberFormatter.format(12.5)
        )
    }
}

@Composable
fun AllRidesEmptyScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = CircleShape
                )
                .padding(8.dp)
        ) {
            Icon(
                imageVector = GlideIcons.NotificationRemove,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = stringResource(CoreR.string.all_rides_empty_title),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(CoreR.string.all_rides_empty_text),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AllRidesScreenPreview(@PreviewParameter(RideRoutePreviewParameterProvider::class) route: RideRoute) {
    GlideAppTheme {
        val rides = MutableStateFlow(
            PagingData.from(
                listOf(
                    RideUiModel(
                        id = "1",
                        startTime = "16:01",
                        finishTime = "16:02",
                        address = "Spacerowa 1A, Słupsk",
                        route = route,
                        distanceMeters = "426",
                        fare = "3,75",
                        separator = PagingSeparator("Monday, February 25")
                    ),
                    RideUiModel(
                        id = "2",
                        startTime = "16:01",
                        finishTime = "16:02",
                        address = "Spacerowa 1A, Słupsk",
                        route = route,
                        distanceMeters = "426",
                        fare = "4,05",
                        separator = PagingSeparator("Monday, February 25")
                    ),
                    RideUiModel(
                        id = "3",
                        startTime = "16:01",
                        finishTime = "16:02",
                        address = "Spacerowa 1A, Słupsk",
                        route = route,
                        distanceMeters = "426",
                        fare = "8,99",
                        separator = PagingSeparator("Monday, February 26")
                    )
                )
            )
        )
            .collectAsLazyPagingItems()
            .toComposePagingItems()

        AllRidesScreenContent(
            uiState = AllRidesUiState(
                rides = rides,
                totalRides = "12",
                totalDistanceMeters = "19,5"
            ),
            onBackClick = {},
            onRideClick = {},
            onPullToRefresh = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AllRidesScreenEmptyPreview() {
    GlideAppTheme {
        val rides = MutableStateFlow(PagingData.empty<RideUiModel>())
            .collectAsLazyPagingItems()
            .toComposePagingItems()

        AllRidesScreenContent(
            uiState = AllRidesUiState(rides = rides),
            onBackClick = {},
            onRideClick = {},
            onPullToRefresh = {}
        )
    }
}
