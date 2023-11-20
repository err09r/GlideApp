package com.apsl.glideapp.feature.rides.components

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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.apsl.glideapp.core.ui.ComposePagingItems
import com.apsl.glideapp.core.ui.PagingSeparator
import com.apsl.glideapp.core.ui.components.GraphRoutePreviewParameterProvider
import com.apsl.glideapp.core.ui.components.RideRoute
import com.apsl.glideapp.core.ui.components.Separator
import com.apsl.glideapp.core.ui.paddingBeforeSeparator
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.ui.toComposePagingItems
import com.apsl.glideapp.feature.rides.models.RideUiModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun RideList(
    rides: ComposePagingItems<RideUiModel>?,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onRideClick: (String) -> Unit
) {
    val itemCount = rides?.itemCount ?: 0
    // Change once https://issuetracker.google.com/issues/264237280 is added
    val indicesBeforeSeparators = remember(rides) { mutableStateMapOf<Int, Int>() }

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        var lastSeparatorText: String? = null

        for (index in 0 until itemCount) {
            val ride = rides?.peekOrNull(index)

            if (ride != null) {
                val (separatorText, separatorId) = ride.separator

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

                item(key = ride.id) {
                    // Gets item, triggering page loads if needed
                    rides[index]?.let { ride ->
                        RideItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .paddingBeforeSeparator(apply = index in indicesBeforeSeparators),
                            overlineText = ride.address ?: "Address not defined",
                            headlineText = "${ride.startTime} - ${ride.finishTime}",
                            supportingText = "${ride.distance} meters",
                            trailingText = "${ride.fare} zł",
                            route = ride.route,
                            onClick = { onRideClick(ride.id) }
                        )
                    }
                }

                lastSeparatorText = separatorText
            }
        }
    }
}

@Preview
@Composable
fun RideListPreview(@PreviewParameter(GraphRoutePreviewParameterProvider::class) route: RideRoute) {
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
                        distance = 426,
                        fare = "3,75",
                        separator = PagingSeparator("Monday, February 25")
                    ),
                    RideUiModel(
                        id = "2",
                        startTime = "16:01",
                        finishTime = "16:02",
                        address = "Spacerowa 1A, Słupsk",
                        route = route,
                        distance = 426,
                        fare = "4,05",
                        separator = PagingSeparator("Monday, February 25")
                    ),
                    RideUiModel(
                        id = "3",
                        startTime = "16:01",
                        finishTime = "16:02",
                        address = "Spacerowa 1A, Słupsk",
                        route = route,
                        distance = 426,
                        fare = "8,99",
                        separator = PagingSeparator("Monday, February 26")
                    )
                )
            )
        )
            .collectAsLazyPagingItems()
            .toComposePagingItems()

        RideList(rides = rides, onRideClick = {})
    }
}
