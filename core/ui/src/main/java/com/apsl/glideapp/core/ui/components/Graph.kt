@file:Suppress("UnnecessaryVariable", "LocalVariableName")

package com.apsl.glideapp.core.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.apsl.glideapp.core.ui.theme.GlideAppTheme

@Composable
fun Graph(
    points: List<Pair<Float, Float>>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    color: Color = MaterialTheme.colors.primary,
    thickness: Dp = 1.dp
) {
    val (minX, maxX) = remember(points) {
        points.minOf { it.first } to points.maxOf { it.first }
    }
    val (minY, maxY) = remember(points) {
        points.minOf { it.second } to points.maxOf { it.second }
    }
    val xDiff = maxX - minX
    val yDiff = maxY - minY

    val path = remember { Path() }
    Canvas(modifier.padding(contentPadding)) {
        path.reset()

        for (i in 0..<points.lastIndex) {
            val (_x, _y) = points[i]
            val (_nextX, _nextY) = points[i + 1]

            val x = size.width / xDiff * (_x - minX)
            val y = size.height / yDiff * (_y - minY)
            val nextX = size.width / xDiff * (_nextX - minX)
            val nextY = size.height / yDiff * (_nextY - minY)

            if (i == 0) {
                path.moveTo(x = x, y = y)
            }

            val firstControlX = (x + nextX) / 2
            val firstControlY = y

            val secondControlX = firstControlX
            val secondControlY = nextY

            path.cubicTo(
                x1 = firstControlX, y1 = firstControlY,
                x2 = secondControlX, y2 = secondControlY,
                x3 = nextX, y3 = nextY
            )

            if (i == 0) {
                drawCircle(
                    color = color,
                    radius = thickness.toPx(),
                    center = Offset(x = x, y = y)
                )
            }
            if (i == points.lastIndex - 1) {
                drawCircle(
                    color = color,
                    radius = thickness.toPx(),
                    center = Offset(x = nextX, y = nextY)
                )
            }
        }

        drawPath(
            path = path,
            color = color,
            style = Stroke(width = thickness.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GraphPreview() {
    GlideAppTheme {
        Graph(
            modifier = Modifier.size(120.dp),
            contentPadding = PaddingValues(4.dp),
            thickness = 2.dp,
            points = listOf(
                1f to 150f,
                2f to 100f,
                3f to 150f,
                4f to 25f,
                5f to 325f,
                6f to 275f
            )
        )
    }
}
