package com.apsl.glideapp.feature.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.apsl.glideapp.core.ui.icons.ElectricScooter
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import kotlin.math.abs

@Composable
fun VehicleMarker(
    selected: Boolean,
    charge: Float,
    modifier: Modifier = Modifier,
    outlineColor: Color = MaterialTheme.colorScheme.primary,
    outlineWidth: Dp = 2.dp
) {
    Surface(
        modifier = modifier
            .padding(start = outlineWidth, top = outlineWidth, end = outlineWidth, bottom = 7.dp)
            .drawWithCache {
                val (width, height) = size
                val (centerX, centerY) = size.center
                val borderWidth = outlineWidth.toPx()
                val borderOffset = Offset(x = -(borderWidth / 2), y = -(borderWidth / 2))
                val borderStyle = Stroke(width = borderWidth, cap = StrokeCap.Round)
                val borderSize = Size(width + borderWidth, height + borderWidth)

                val trianglePath = Path().apply {
                    this.moveTo(x = centerX - 5.dp.toPx(), y = height - 2.dp.toPx())
                    lineTo(x = centerX + 5.dp.toPx(), y = height - 2.dp.toPx())
                    lineTo(x = centerX, y = height + 4.dp.toPx())
                    lineTo(x = centerX - 5.dp.toPx(), y = height - 2.dp.toPx())
                }

                onDrawBehind {
                    drawPath(
                        color = outlineColor,
                        path = trianglePath,
                        style = Stroke(
                            width = 6.dp.toPx(),
                            join = StrokeJoin.Round,
                            cap = StrokeCap.Round
                        )
                    )
                    drawPath(
                        color = outlineColor,
                        path = trianglePath,
                    )
                    drawArc(
                        color = outlineColor,
                        topLeft = borderOffset,
                        size = borderSize,
                        style = borderStyle,
                        useCenter = false,
                        startAngle = -90f,
                        sweepAngle = -3.6f * abs(charge)
                    )
                }
            },
        shape = CircleShape,
        color = if (selected) MaterialTheme.colorScheme.inverseSurface else MaterialTheme.colorScheme.surface
    ) {
        Box(modifier = Modifier.padding(3.dp)) {
            Icon(
                imageVector = GlideIcons.ElectricScooter,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = outlineColor
            )
        }
    }
}

@Preview
@Composable
fun VehicleMarkerPreview() {
    GlideAppTheme {
        VehicleMarker(selected = false, charge = 25f)
    }
}

@Preview
@Composable
fun VehicleMarkerSelectedPreview() {
    GlideAppTheme {
        VehicleMarker(selected = true, charge = 25f)
    }
}
