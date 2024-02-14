package com.apsl.glideapp.feature.home.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apsl.glideapp.common.util.toEpochMilliseconds
import com.apsl.glideapp.core.ui.AnimatedStopwatchText
import com.apsl.glideapp.core.ui.AnimatedText
import com.apsl.glideapp.core.ui.icons.Clock
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.icons.Route
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.util.android.GlideStopwatch
import com.apsl.glideapp.feature.home.viewmodels.BatteryState
import com.apsl.glideapp.feature.home.viewmodels.RideState
import com.apsl.glideapp.feature.home.viewmodels.VehicleUiModel
import kotlinx.datetime.LocalDateTime
import timber.log.Timber
import com.apsl.glideapp.core.ui.R as CoreR

@Composable
fun BoxScope.RideLayout(rideState: RideState, modifier: Modifier = Modifier) {
    var stopwatch by remember { mutableStateOf<GlideStopwatch?>(null) }
    val stopwatchValue = stopwatch?.currentValueMillis?.collectAsState()

    LaunchedEffect(rideState.isActive) {
        Timber.d(rideState.toString())
        stopwatch = if (rideState.isActive && stopwatch == null) {
            GlideStopwatch(
                initialMillis = rideState.startDateTime.toEpochMilliseconds(),
                startImmediately = true
            )
        } else {
            stopwatch?.stop()
            null
        }
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .align(Alignment.TopCenter),
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(CoreR.string.ride_layout_header))
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(Modifier.weight(1f))
                    Icon(
                        imageVector = GlideIcons.Clock,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                    Spacer(Modifier.width(8.dp))
                    AnimatedStopwatchText(
                        valueProvider = { stopwatchValue?.value ?: 0L },
                        maxLines = 1,
                        style = MaterialTheme.typography.headlineLarge
                    )
                    Spacer(Modifier.weight(1f))
                }
                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .padding(vertical = 4.dp)
                )
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(Modifier.weight(1f))
                    Icon(
                        imageVector = GlideIcons.Route,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                    Spacer(Modifier.width(8.dp))

                    val context = LocalContext.current
                    AnimatedText(
                        textProvider = {
                            context.getString(CoreR.string.value_kilometers, rideState.distance)
                        },
                        maxLines = 1,
                        style = MaterialTheme.typography.headlineLarge
                    )

                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RideLayoutPreview() {
    GlideAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Gray)
        ) {
            RideLayout(
                rideState = RideState(
                    startDateTime = LocalDateTime.parse("2023-11-11T11:11:11"),
                    vehicle = VehicleUiModel("", "", 1, 0.0, 0.0, 0, BatteryState.Undefined),
                    distance = "2,9"
                )
            )
        }
    }
}
