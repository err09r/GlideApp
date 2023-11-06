package com.apsl.glideapp.feature.rides.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.apsl.glideapp.core.ui.components.PreviewGraphRoute
import com.apsl.glideapp.core.ui.icons.ElectricScooter
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.icons.NotificationRemove
import com.apsl.glideapp.core.ui.icons.Route
import com.apsl.glideapp.core.ui.peekOrNull
import com.apsl.glideapp.core.ui.pullrefresh.PullRefreshIndicator
import com.apsl.glideapp.core.ui.pullrefresh.pullRefresh
import com.apsl.glideapp.core.ui.pullrefresh.rememberPullRefreshState
import com.apsl.glideapp.core.ui.receiveAsLazyPagingItems
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.feature.rides.models.RideUiModel
import com.apsl.glideapp.feature.rides.models.Separator
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
                val pullRefreshState = rememberPullRefreshState(
                    refreshing = refreshing,
                    onRefresh = onPullRefresh
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pullRefresh(pullRefreshState)
                ) {
                    val itemCount = uiState.rides?.itemCount ?: 0

                    if (itemCount == 0) {
                        AllRidesEmptyScreen()
                    } else {
                        Column {
                            RideStats(rides = 1244, meters = 1214)
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 32.dp
                                ),
                            ) {
                                var lastSeparatorText: String? = null

                                for (index in 0 until itemCount) {
                                    val ride = uiState.rides?.peekOrNull(index)

                                    if (ride != null) {
                                        val (separatorId, separatorText) = ride.separator

                                        if (separatorText != lastSeparatorText) {
                                            stickyHeader(key = separatorId) {
                                                Column(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .background(color = MaterialTheme.colorScheme.background)
                                                ) {
                                                    Text(
                                                        text = separatorText,
                                                        modifier = Modifier.padding(bottom = 8.dp),
                                                        fontSize = 24.sp,
                                                        fontWeight = FontWeight.SemiBold
                                                    )
                                                }
                                            }
                                        } else {
                                            item {
                                                Divider()
                                            }
                                        }

                                        item(key = ride.id) {
                                            // Gets item, triggering page loads if needed
                                            uiState.rides[index]?.let { ride ->
                                                RideItem(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    overlineText = "Spacerowa 1A, Słupsk",
                                                    headlineText = "${ride.startTime} - ${ride.finishTime}",
                                                    supportingText = "${ride.distance} meters",
                                                    trailingText = "- ${ride.fare} zł",
                                                    route = ride.route.ifEmpty { PreviewGraphRoute },
                                                    onClick = { onRideClick(ride.id) }
                                                )
                                            }
                                        }

                                        lastSeparatorText = separatorText
                                    }
                                }
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
    }
}

@Composable
fun RideStats(modifier: Modifier = Modifier, rides: Int, meters: Int) {
    Card(
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
                title = "Total travelled",
                text = "$meters m",
                imageVector = GlideIcons.Route
            )
//        Spacer(Modifier)
//            Divider(
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .width(1.dp)
//                    .padding(vertical = 8.dp)
//            )
            Spacer(Modifier.weight(1f))
            RideStatsItem(
                title = "Total rides",
                text = rides.toString(),
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

@Composable
fun RideStatsPreview() {
    GlideAppTheme {
        RideStats(rides = 12, meters = 1214)
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
            text = "No rides",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(8.dp))
        Text(
            text = "Start your first ride and it will show up here",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyLarge
        )
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
                        route = PreviewGraphRoute,
                        distance = 426,
                        fare = "4.05",
                        separator = Separator(text = "Monday, February 25")
                    ),
                    RideUiModel(
                        id = "2",
                        startTime = "16:01",
                        finishTime = "16:02",
                        route = PreviewGraphRoute,
                        distance = 426,
                        fare = "4.05",
                        separator = Separator(text = "Monday, February 25")
                    ),
                    RideUiModel(
                        id = "3",
                        startTime = "16:01",
                        finishTime = "16:02",
                        route = PreviewGraphRoute,
                        distance = 426,
                        fare = "4.05",
                        separator = Separator(text = "Monday, February 26")
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

@Preview(showBackground = true)
@Composable
fun AllRidesScreenEmptyPreview() {
    GlideAppTheme {
        val rides = MutableStateFlow(PagingData.empty<RideUiModel>()).collectAsLazyPagingItems()
        AllRidesScreenContent(
            uiState = AllRidesUiState(rides = rides),
            onBackClick = {},
            onRideClick = {},
            onPullRefresh = {}
        )
    }
}
