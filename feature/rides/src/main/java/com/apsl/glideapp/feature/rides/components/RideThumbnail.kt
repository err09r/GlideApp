package com.apsl.glideapp.feature.rides.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.isSpecified
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import kotlin.math.ceil

@Composable
fun RideThumbnail(
    points: List<Pair<Float, Float>>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    pointRadius: Dp = 1.dp,
    lineStrokeWidth: Dp = 2.dp,
    showCells: Boolean = false, //TODO rename
    cellLineRadius: Dp = 1.dp,
    startPointRadius: Dp = pointRadius,
    endPointRadius: Dp = startPointRadius
) {
    //TODO design as 'remember' variables
    val selectorX: (Pair<Float, Float>) -> Float = remember { { it.first } }
    val minX = points.minOf(selectorX)
    val maxX = points.maxOf(selectorX)
    val xCount = ceil(maxX - minX).toInt()

    //TODO design as 'remember' variables
    val selectorY: (Pair<Float, Float>) -> Float = remember { { it.second } }
    val minY = points.minOf(selectorY)
    val maxY = points.maxOf(selectorY)
    val yCount = ceil(maxY - minY).toInt()

    val pointRadiusPx = with(LocalDensity.current) { pointRadius.toPx() }
    val startPointRadiusPx = with(LocalDensity.current) { startPointRadius.toPx() }
    val endPointRadiusPx = with(LocalDensity.current) { endPointRadius.toPx() }
    val lineStrokeWidthPx = with(LocalDensity.current) { lineStrokeWidth.toPx() }
    val cellStrokeWidthPx = with(LocalDensity.current) { cellLineRadius.toPx() }

    Box(
        modifier = modifier
            .background(color = Color.Blue.copy(alpha = 0.2f), shape = RoundedCornerShape(16.dp))
            .size(200.dp)
            .padding(contentPadding)
            .drawBehind {
                if (showCells) {
                    drawCells(
                        xCount = xCount,
                        yCount = yCount,
                        strokeWidthPx = cellStrokeWidthPx
                    )
                }

                var prevOffset = Offset.Unspecified
                var i = 0
                for ((x, y) in points) {
                    val currentOffset = Offset(
                        x = (size.width / xCount * (x - 3)) - pointRadiusPx,
                        y = (size.height / yCount * (y - 2)) - pointRadiusPx
                    )

                    if (prevOffset.isSpecified) {
                        drawLine(
                            color = Color.Blue,
                            start = currentOffset,
                            end = prevOffset,
                            strokeWidth = lineStrokeWidthPx,
                            cap = StrokeCap.Round
                        )
                        drawCircle(
                            color = when (i - 1) {
                                0 -> Color.White
                                else -> Color.Blue
                            },
                            radius = when (i - 1) {
                                0 -> startPointRadiusPx
                                points.lastIndex -> endPointRadiusPx
                                else -> pointRadiusPx
                            },
                            center = prevOffset
                        )
                    }

                    if (i == points.lastIndex) {
                        drawCircle(
                            color = Color.White,
                            radius = endPointRadiusPx,
                            center = currentOffset
                        )
                    }

                    prevOffset = currentOffset
                    i++
                }
            }
    )
}

private fun DrawScope.drawCells(xCount: Int, yCount: Int, strokeWidthPx: Float) {
    //TODO control 'step' parameter
    for (x in 0..xCount step xCount / 3) {
        val startOffset = Offset(x = size.width / xCount * x, y = 0f)
        val endOffset = Offset(x = startOffset.x, y = size.height)

        if (x != 0 && x != xCount) {
            drawLine(
                color = Color.Blue.copy(alpha = 0.15f),
                start = startOffset,
                end = endOffset,
                strokeWidth = strokeWidthPx,
                cap = StrokeCap.Round
            )
        }
    }

    //TODO control 'step' parameter
    for (y in 0..yCount step yCount / 3) {
        val startOffset = Offset(x = 0f, y = size.height / yCount * y)
        val endOffset = Offset(x = size.width, y = startOffset.y)

        if (y != 0 && y != yCount) {
            drawLine(
                color = Color.Blue.copy(alpha = 0.15f),
                start = startOffset,
                end = endOffset,
                strokeWidth = strokeWidthPx,
                cap = StrokeCap.Round
            )
        }
    }
}

@Preview
@Composable
fun RideThumbnailPreview() {
    GlideAppTheme {
        val points = listOf(
            4f to 2f,
            3f to 4f,
            5f to 6f,
            7f to 8f,
            9f to 5f,
            7f to 20.86f,
            11f to 3f,
            15f to 33f,
            16f to 34f,
            17f to 36f
        )
        Box(
            modifier = Modifier
                .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            //TODO make sure ceil round to the closest greater int !!!
            RideThumbnail(
                points = points,
                contentPadding = PaddingValues(16.dp),
                startPointRadius = 4.dp,
                showCells = true,
                cellLineRadius = 2.dp,
                lineStrokeWidth = 4.dp,
                pointRadius = 2.dp
            )
        }
    }
}
