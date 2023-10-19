package com.apsl.glideapp.core.ui

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import kotlin.math.roundToInt

@Composable
fun LoadingBar(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surfaceVariant
) {
    val circleRadius = 8.dp
    val circleRadiusPx = circleRadius.toPx()
    val size = remember { mutableStateOf(IntSize.Zero) }

    val animated = rememberInfiniteTransition(label = "")
    val offsetY = animated.animateFloat(
        initialValue = 0f,
        targetValue = size.value.width.toFloat() - circleRadiusPx * 2,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    Canvas(
        modifier = modifier
            .height(circleRadius * 2)
            .onGloballyPositioned { size.value = it.size }
            .offset {
                IntOffset(x = offsetY.value.roundToInt(), y = 0)
            }
    ) {
        drawCircle(
            radius = circleRadiusPx,
            color = color,
            center = Offset(x = circleRadiusPx, y = this.size.height / 2)
        )
    }
}

@Preview
@Composable
fun LoadingBarPreview() {
    GlideAppTheme {
        LoadingBar(modifier = Modifier.width(300.dp))
    }
}
