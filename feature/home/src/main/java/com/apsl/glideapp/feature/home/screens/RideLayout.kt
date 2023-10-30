package com.apsl.glideapp.feature.home.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apsl.glideapp.common.util.toEpochMilliseconds
import com.apsl.glideapp.core.ui.icons.Clock
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.icons.Route
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.util.android.GlideComposeStopwatch
import com.apsl.glideapp.core.util.android.StopwatchUtils
import timber.log.Timber

@Composable
fun BoxScope.RideLayout(rideState: RideState? = null) {
    var stopwatch = remember<GlideComposeStopwatch?> { null }

    LaunchedEffect(rideState) {
        Timber.d(rideState.toString())
        stopwatch = if (rideState?.isActive == true && stopwatch == null) {
            GlideComposeStopwatch(
                initialMillis = rideState.startDateTime.toEpochMilliseconds(),
                startImmediately = true,
                onTick = { valueMillis ->
                    Timber.d("Stopwatch millis: $valueMillis")
                }
            )
        } else {
            stopwatch?.stop()
            null
        }
    }

    Surface(
        modifier = Modifier
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
            Text(text = "Active Ride")
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = GlideIcons.Clock, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    RideStopwatchText(valueProvider = { stopwatch?.valueMillis ?: 0L })
                }
                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .padding(vertical = 4.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = GlideIcons.Route, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(text = "2.9 km", style = MaterialTheme.typography.headlineLarge)
                }
            }
        }
    }
}

@Composable
fun RideStopwatchText(valueProvider: () -> Long, modifier: Modifier = Modifier) {
    val oldValue = remember { valueProvider() }
    Row(modifier = modifier) {
        val countString = StopwatchUtils.millisToTimeFormat(valueProvider())
        val oldCountString = StopwatchUtils.millisToTimeFormat(oldValue)
        for (i in countString.indices) {
            val oldChar = oldCountString.getOrNull(i)
            val newChar = countString[i]
            val char = if (oldChar == newChar) {
                oldCountString[i]
            } else {
                countString[i]
            }
            AnimatedContent(
                targetState = char,
                transitionSpec = { slideInVertically { it } togetherWith slideOutVertically { -it } },
                label = ""
            ) {
                Text(text = it.toString(), style = MaterialTheme.typography.headlineLarge)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStopwatchText() {
    GlideAppTheme {
        RideStopwatchText(valueProvider = { System.currentTimeMillis() })
    }
}

@Preview(showBackground = true)
@Composable
fun RideLayoutPreview() {
    GlideAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Gray)
        ) {
            RideLayout()
        }
    }
}
