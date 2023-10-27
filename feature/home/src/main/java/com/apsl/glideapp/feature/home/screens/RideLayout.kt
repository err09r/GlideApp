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
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apsl.glideapp.core.ui.icons.Clock
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.icons.Route
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import timber.log.Timber

@Composable
fun BoxScope.RideLayout(rideState: RideState? = null) {
    val timeValue = remember { mutableLongStateOf(0L) }
    var stopwatch = remember<GlideStopwatch?> { null }

    LaunchedEffect(rideState) {
        Timber.d(rideState.toString())
        if (rideState?.isActive == true && stopwatch == null) {
            stopwatch = GlideStopwatch(
                initialMs = rideState.startDateTime.toInstant(TimeZone.currentSystemDefault())
                    .toEpochMilliseconds()
            ) { ms ->
                Timber.d("ms: $ms")
                timeValue.longValue = ms
            }
            stopwatch?.start()
        } else {
            stopwatch?.stop()
            stopwatch = null
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
                    RideStopwatchText(valueProvider = { timeValue.longValue })
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

class GlideStopwatch(
    private val initialMs: Long = 0,
    private val onTick: (Long) -> Unit
) {
    private val scope = CoroutineScope(Job())
    private var currentValueMs: Long = 0L
    private var isStared: Boolean = false

    fun start() {
        if (isStared) {
            Timber.d("Stopwatch has already started")
            return
        }
        currentValueMs = System.currentTimeMillis() - initialMs
        isStared = true
        Timber.d("Stopwatch started")
        onTick(currentValueMs)
        scope.launch {
            while (isActive && isStared) {
                delay(TIME_PER_TICK_MS)
                currentValueMs += TIME_PER_TICK_MS
                onTick(currentValueMs)
            }
        }
    }

    fun stop() {
        isStared = false
        scope.cancel()
        Timber.d("Stopwatch stopped")
    }

    private companion object {
        private const val TIME_PER_TICK_MS: Long = 1000L
    }
}

@Composable
fun RideStopwatchText(valueProvider: () -> Long, modifier: Modifier = Modifier) {
    val oldValue = remember { valueProvider() }
    Row(modifier = modifier) {
        val countString = toTimerFormat(valueProvider())
        val oldCountString = toTimerFormat(oldValue)
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

private fun toTimerFormat(millis: Long): String {
    var remainMillis = millis
    val wholeHours = remainMillis / 3600_000
    remainMillis %= 3600_000
    val wholeMinutes = remainMillis / 1000 / 60
    remainMillis = if (wholeMinutes <= 0L) {
        remainMillis
    } else {
        remainMillis % (wholeMinutes * 1000 * 60)
    }
    val wholeSeconds = remainMillis / 1000

    return buildString {
        if (wholeHours > 0L) {
            append(wholeHours.coerceIn(0, 99).toString().padStart(2, '0'))
            append(':')
        }
        append(wholeMinutes.coerceIn(0, 59).toString().padStart(2, '0'))
        append(':')
        append(wholeSeconds.coerceIn(0, 59).toString().padStart(2, '0'))
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
