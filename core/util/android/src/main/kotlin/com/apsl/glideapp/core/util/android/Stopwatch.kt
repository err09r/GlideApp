@file:Suppress("Unused", "MemberVisibilityCanBePrivate")

package com.apsl.glideapp.core.util.android

import kotlin.time.Duration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import timber.log.Timber

class GlideStopwatch(
    private val initialMillis: Long = System.currentTimeMillis(),
    startImmediately: Boolean = false,
    private val onTick: (Long) -> Unit = {}
) {
    private val scope = CoroutineScope(Job())

    private val _currentValueMillis = MutableStateFlow(DEFAULT_INITIAL_VALUE_MS)
    val currentValueMillis = _currentValueMillis.asStateFlow()

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
        isStared = true
        Timber.d("Stopwatch started")

        _currentValueMillis.update { System.currentTimeMillis() - initialMillis }
        onTick(_currentValueMillis.value)

        scope.launch {
            while (isActive && isStared) {
                delay(INTERVAL_MS)
                _currentValueMillis.update { it + INTERVAL_MS }
                onTick(_currentValueMillis.value)
            }
        }
    }

    fun stop() {
        isStared = false
        scope.cancel()
        Timber.d("Stopwatch stopped")
    }

    private companion object {
        private const val DEFAULT_INITIAL_VALUE_MS = 0L
        private const val INTERVAL_MS: Long = 1000L
    }
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
