@file:Suppress("Unused", "MemberVisibilityCanBePrivate")

package com.apsl.glideapp.core.util.android

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.time.Duration

open class GlideStopwatch(
    private val initialMillis: Long = DEFAULT_INITIAL_VALUE_MILLIS,
    startImmediately: Boolean = false,
    private val onTick: (Long) -> Unit
) {
    private val scope = CoroutineScope(Job())

    protected var currentValueMillis: Long = DEFAULT_INITIAL_VALUE_MILLIS
    private var isStared: Boolean = false

    init {
        if (startImmediately) {
            start()
        }
    }

    fun start() {
        if (isStared) {
            Timber.d("Stopwatch has already started")
            return
        }
        currentValueMillis = System.currentTimeMillis() - initialMillis
        isStared = true
        Timber.d("Stopwatch started")
        onTick(currentValueMillis)
        scope.launch {
            while (isActive && isStared) {
                delay(INTERVAL_MILLIS)
                currentValueMillis += INTERVAL_MILLIS
                onTick(currentValueMillis)
            }
        }
    }

    fun stop() {
        isStared = false
        scope.cancel()
        Timber.d("Stopwatch stopped")
    }

    companion object {
        const val DEFAULT_INITIAL_VALUE_MILLIS: Long = 0L
        private const val INTERVAL_MILLIS: Long = 1000L
    }
}

class GlideComposeStopwatch(
    initialMillis: Long = DEFAULT_INITIAL_VALUE_MILLIS,
    startImmediately: Boolean = false,
    onTick: (Long) -> Unit
) : GlideStopwatch(initialMillis, startImmediately, onTick) {

    var valueMillis by mutableStateOf(currentValueMillis)
        private set
}

object StopwatchUtils {

    fun durationToTimeFormat(duration: Duration): String {
        return millisToTimeFormat(duration.inWholeMilliseconds)
    }

    fun millisToTimeFormat(millis: Long): String {
        var remainMillis = millis
        val wholeHours = remainMillis / 3_600_000
        remainMillis %= 3_600_000
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
}
