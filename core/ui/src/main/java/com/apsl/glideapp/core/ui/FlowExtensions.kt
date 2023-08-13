@file:SuppressLint("ComposableNaming", "StateFlowValueCalledInComposition")

package com.apsl.glideapp.core.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

@Composable
fun <T> StateFlow<T>.collectWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext
) = collectWithLifecycle(
    initialValue = this.value,
    lifecycle = lifecycleOwner.lifecycle,
    minActiveState = minActiveState,
    context = context
)

@Composable
fun <T> StateFlow<T>.collectWithLifecycle(
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext
) = collectWithLifecycle(
    initialValue = this.value,
    lifecycle = lifecycle,
    minActiveState = minActiveState,
    context = context
)

@Composable
fun <T> Flow<T>.collectWithLifecycle(
    initialValue: Unit = Unit,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext
) = collectWithLifecycle(
    initialValue = initialValue,
    lifecycle = lifecycleOwner.lifecycle,
    minActiveState = minActiveState,
    context = context
)

@Composable
fun <T> Flow<T>.collectWithLifecycle(
    initialValue: T,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext
) = collectWithLifecycle(
    initialValue = initialValue,
    lifecycle = lifecycleOwner.lifecycle,
    minActiveState = minActiveState,
    context = context
)

@Composable
fun <T> Flow<T>.collectWithLifecycle(
    initialValue: T,
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext
) {
    val state = this.collectAsStateWithLifecycle(
        initialValue = initialValue,
        lifecycle = lifecycle,
        minActiveState = minActiveState,
        context = context
    )
    LaunchedEffect(state) {
        snapshotFlow(state::value).collect()
    }
}
