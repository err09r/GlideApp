package com.apsl.glideapp.feature.rides.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.apsl.glideapp.core.ui.peekOrNull
import com.apsl.glideapp.feature.rides.models.RideUiModel

@Composable
fun RideList(
    rides: LazyPagingItems<RideUiModel>?,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onRideClick: (String) -> Unit
) {
    val itemCount = rides?.itemCount ?: 0
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
                    rides[index]?.let { ride ->
                        RideItem(
                            modifier = Modifier.fillMaxWidth(),
                            overlineText = "Spacerowa 1A, Słupsk",
                            headlineText = "${ride.startTime} - ${ride.finishTime}",
                            supportingText = "${ride.distance} meters",
                            trailingText = "-${ride.fare} zł",
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
