package com.apsl.glideapp.core.ui

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.apsl.glideapp.core.ui.theme.GlideAppTheme

@Composable
fun LoadingBar(modifier: Modifier = Modifier) {
    val animated = rememberInfiniteTransition(label = "")
    val circleRadius = remember { mutableStateOf(6.dp) }
    val size = remember { mutableStateOf(IntSize.Zero) }
    val endPadding = LocalDensity.current.run { circleRadius.value.toPx() * 2 }
    val offsetY = animated.animateFloat(
        initialValue = 0f,
        targetValue = size.value.width.toFloat() - endPadding,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )
    Box(
        modifier
            .height(circleRadius.value * 2)
            .onGloballyPositioned { size.value = it.size }
            .drawBehind {
                drawCircle(
                    radius = circleRadius.value.toPx(),
                    color = Color.White,
                    center = Offset(
                        x = circleRadius.value.toPx() + offsetY.value,
                        y = this.size.height / 2
                    )
                )
            }
    )
}

@Preview
@Composable
fun LoadingBarPreview() {
    GlideAppTheme {
        LoadingBar(modifier = Modifier.width(300.dp))
    }
}
