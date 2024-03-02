@file:Suppress("Unused")

package com.apsl.glideapp.core.ui.pulltorefresh

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.apsl.glideapp.core.ui.GlideCircularLoadingIndicator
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

@Composable
fun Indicator(
    state: PullToRefreshState,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
) {
    Crossfade(
        targetState = state.isRefreshing,
        animationSpec = tween(durationMillis = CrossfadeDurationMs),
        label = "Refreshing"
    ) { refreshing ->
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (refreshing) {
                GlideCircularLoadingIndicator(
                    strokeWidth = StrokeWidth,
                    color = color,
                    modifier = Modifier.size(SpinnerSize),
                )
            } else {
                CircularArrowProgressIndicator(
                    progress = { state.progress },
                    color = color,
                )
            }
        }
    }
}

@Composable
private fun CircularArrowProgressIndicator(
    progress: () -> Float,
    color: Color,
) {
    val path = remember { Path().apply { fillType = PathFillType.EvenOdd } }
    // TODO: Consider refactoring this sub-component utilizing Modifier.Node
    val targetAlpha by remember {
        derivedStateOf { if (progress() >= 1f) MaxAlpha else MinAlpha }
    }
    val alphaState = animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = AlphaTween,
        label = "AlphaState"
    )
    Canvas(
        Modifier
            .semantics(mergeDescendants = true) {
                progressBarRangeInfo = ProgressBarRangeInfo(progress(), 0f..1f, 0)
            }
            .size(SpinnerSize)
    ) {
        val values = ArrowValues(progress())
        val alpha = alphaState.value
        rotate(degrees = values.rotation) {
            val arcRadius = ArcRadius.toPx() + StrokeWidth.toPx() / 2f
            val arcBounds = Rect(center = size.center, radius = arcRadius)
            drawCircularIndicator(color, alpha, values, arcBounds, StrokeWidth)
            drawArrow(path, arcBounds, color, alpha, values, StrokeWidth)
        }
    }
}

private fun DrawScope.drawCircularIndicator(
    color: Color,
    alpha: Float,
    values: ArrowValues,
    arcBounds: Rect,
    strokeWidth: Dp
) {
    drawArc(
        color = color,
        alpha = alpha,
        startAngle = values.startAngle,
        sweepAngle = values.endAngle - values.startAngle,
        useCenter = false,
        topLeft = arcBounds.topLeft,
        size = arcBounds.size,
        style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
    )
}

@Immutable
private class ArrowValues(
    val rotation: Float,
    val startAngle: Float,
    val endAngle: Float,
    val scale: Float
)

private fun ArrowValues(progress: Float): ArrowValues {
    // Discard first 40% of progress. Scale remaining progress to full range between 0 and 100%.
    val adjustedPercent = max(min(1f, progress) - 0.4f, 0f) * 5 / 3
    // How far beyond the threshold pull has gone, as a percentage of the threshold.
    val overshootPercent = abs(progress) - 1.0f
    // Limit the overshoot to 200%. Linear between 0 and 200.
    val linearTension = overshootPercent.coerceIn(0f, 2f)
    // Non-linear tension. Increases with linearTension, but at a decreasing rate.
    val tensionPercent = linearTension - linearTension.pow(2) / 4

    // Calculations based on SwipeRefreshLayout specification.
    val endTrim = adjustedPercent * MaxProgressArc
    val rotation = (-0.25f + 0.4f * adjustedPercent + tensionPercent) * 0.5f
    val startAngle = rotation * 360
    val endAngle = (rotation + endTrim) * 360
    val scale = min(1f, adjustedPercent)

    return ArrowValues(rotation, startAngle, endAngle, scale)
}

private fun DrawScope.drawArrow(
    arrow: Path,
    bounds: Rect,
    color: Color,
    alpha: Float,
    values: ArrowValues,
    strokeWidth: Dp,
) {
    arrow.reset()
    arrow.moveTo(0f, 0f) // Move to left corner
    // Line to tip of arrow
    arrow.lineTo(
        x = ArrowWidth.toPx() * values.scale / 2,
        y = ArrowHeight.toPx() * values.scale
    )
    arrow.lineTo(x = ArrowWidth.toPx() * values.scale, y = 0f) // Line to right corner

    val radius = min(bounds.width, bounds.height) / 2f
    val inset = ArrowWidth.toPx() * values.scale / 2f
    arrow.translate(
        Offset(
            x = radius + bounds.center.x - inset,
            y = bounds.center.y - strokeWidth.toPx()
        )
    )
    rotate(degrees = values.endAngle - strokeWidth.toPx()) {
        drawPath(
            path = arrow,
            color = color,
            alpha = alpha,
            style = Stroke(
                width = strokeWidth.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )
    }
}

private const val MaxProgressArc = 0.8f
private const val CrossfadeDurationMs = MotionTokens.DurationShort2.toInt()

/** The default stroke width for [Indicator] */
private val StrokeWidth = 2.5.dp
private val ArcRadius = 5.5.dp
private val SpinnerSize = 16.dp // (ArcRadius + PullRefreshIndicatorDefaults.StrokeWidth).times(2)
private val SpinnerContainerSize = 40.dp
private val Elevation = ElevationTokens.Level2
private val ArrowWidth = 10.dp
private val ArrowHeight = 5.dp

// Values taken from SwipeRefreshLayout
private const val MinAlpha = 0.3f
private const val MaxAlpha = 1f
private val AlphaTween = tween<Float>(MotionTokens.DurationMedium2.toInt(), easing = LinearEasing)

/**
 * The distance pulled is multiplied by this value to give us the adjusted distance pulled, which
 * is used in calculating the indicator position (when the adjusted distance pulled is less than
 * the refresh threshold, it is the indicator position, otherwise the indicator position is
 * derived from the progress).
 */
private const val DragMultiplier = 0.5f
