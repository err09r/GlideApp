@file:Suppress("Unused")

package com.apsl.glideapp.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.SheetState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.random.Random

fun Color.Companion.random(): Color {
    return Color(
        red = Random.nextInt(0, 256),
        green = Random.nextInt(0, 256),
        blue = Random.nextInt(0, 256),
    )
}

fun RoundedCornerShape(top: Dp = 0.dp, bottom: Dp = 0.dp) = RoundedCornerShape(
    topStart = CornerSize(top),
    topEnd = CornerSize(top),
    bottomEnd = CornerSize(bottom),
    bottomStart = CornerSize(bottom)
)

fun Modifier.rippleClickable(enabled: Boolean = true, onClick: () -> Unit): Modifier = composed {
    this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple(color = if (isSystemInDarkTheme()) Color.White else Color.Black),
        enabled = enabled,
        onClick = onClick
    )
}

fun Modifier.noRippleClickable(enabled: Boolean = true, onClick: () -> Unit): Modifier = composed {
    this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        enabled = enabled,
        onClick = onClick
    )
}

val SheetState.offset: Float?
    get() = try {
        requireOffset()
    } catch (e: IllegalStateException) {
        null
    }
