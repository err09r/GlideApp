@file:Suppress("Unused")

package com.apsl.glideapp.core.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.random.Random

fun Color.Companion.random(): Color {
    return Color(
        red = Random.nextInt(0, 256),
        green = Random.nextInt(0, 256),
        blue = Random.nextInt(0, 256)
    )
}

fun RoundedCornerShape(top: Dp = 0.dp, bottom: Dp = 0.dp) = RoundedCornerShape(
    topStart = CornerSize(top),
    topEnd = CornerSize(top),
    bottomEnd = CornerSize(bottom),
    bottomStart = CornerSize(bottom)
)

val SheetState.offset: Float?
    get() = try {
        requireOffset()
    } catch (e: IllegalStateException) {
        null
    }

val screenHeight: Dp
    @Composable
    get() = LocalConfiguration.current.screenHeightDp.dp

val screenWidth: Dp
    @Composable
    get() = LocalConfiguration.current.screenWidthDp.dp

val WindowInsets.Companion.None: WindowInsets get() = WindowInsets(0, 0, 0, 0)

val WindowInsets.Companion.statusBarHeight: Dp
    @Composable
    get() = WindowInsets.systemBars.getTop(LocalDensity.current).toDp()

val WindowInsets.Companion.navigationBarHeight: Dp
    @Composable
    get() = WindowInsets.systemBars.getBottom(LocalDensity.current).toDp()

@Composable
fun Float.toDp(density: Density = LocalDensity.current): Dp {
    return with(density) { this@toDp.toDp() }
}

@Composable
fun Float?.toDp(density: Density = LocalDensity.current): Dp {
    return with(density) { this@toDp?.toDp() ?: 0.dp }
}

@Composable
fun Int.toDp(density: Density = LocalDensity.current): Dp {
    return with(density) { this@toDp.toDp() }
}

@Composable
fun Int?.toDp(density: Density = LocalDensity.current): Dp {
    return with(density) { this@toDp?.toDp() ?: 0.dp }
}
