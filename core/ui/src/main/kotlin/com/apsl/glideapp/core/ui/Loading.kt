package com.apsl.glideapp.core.ui

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import kotlin.math.roundToInt

@Composable
fun LoadingBar(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    elevation: Dp = 0.dp
) {
    val circleRadius = 8.dp
    val circleRadiusPx = circleRadius.toPx()
    val size = remember { mutableStateOf(IntSize.Zero) }

    val animated = rememberInfiniteTransition(label = "InfiniteTransition")
    val offsetY = animated.animateFloat(
        initialValue = 0f,
        targetValue = size.value.width.toFloat() - circleRadiusPx * 2,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "OffsetY"
    )

    Box(modifier = modifier.onGloballyPositioned { size.value = it.size }) {
        Surface(
            modifier = Modifier.offset {
                IntOffset(x = offsetY.value.roundToInt(), y = 0)
            },
            shape = CircleShape,
            color = color,
            tonalElevation = elevation,
            shadowElevation = elevation
        ) {
            Spacer(modifier = Modifier.size(circleRadius * 2))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingBarPreview() {
    GlideAppTheme {
        LoadingBar(modifier = Modifier.fillMaxWidth())
    }
}

@NonRestartableComposable
@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        GlideCircularLoadingIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingScreenPreview() {
    GlideAppTheme {
        LoadingScreen()
    }
}

@NonRestartableComposable
@Composable
fun GlideCircularLoadingIndicator(
    modifier: Modifier = Modifier,
    color: Color = ProgressIndicatorDefaults.circularColor,
    strokeWidth: Dp = ProgressIndicatorDefaults.CircularStrokeWidth,
    trackColor: Color = ProgressIndicatorDefaults.circularTrackColor
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = color,
        strokeWidth = strokeWidth,
        trackColor = trackColor,
        strokeCap = StrokeCap.Round
    )
}

@Preview
@Composable
private fun GlideCircularLoadingIndicatorPreview() {
    GlideAppTheme {
        GlideCircularLoadingIndicator()
    }
}
