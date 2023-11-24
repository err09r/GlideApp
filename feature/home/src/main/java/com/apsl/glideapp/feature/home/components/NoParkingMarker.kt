package com.apsl.glideapp.feature.home.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apsl.glideapp.core.ui.theme.Colors
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.ui.toPx

@Composable
fun NoParkingMarker(modifier: Modifier = Modifier) {
    val backgroundColor = MaterialTheme.colorScheme.surface
    val textStyle = MaterialTheme.typography.titleMedium.copy(
        color = contentColorFor(backgroundColor = backgroundColor),
        fontSize = 11.sp
    )
    val borderWidth = 1.5.dp.toPx()
    val borderColor = Colors.NoParking
    val textMeasurer = rememberTextMeasurer()
    Spacer(
        modifier = modifier
            .padding(1.dp)
            .size(16.dp)
            .padding(1.dp)
            .drawWithCache {
                val textSize = textMeasurer.measure(text = "P", style = textStyle).size
                val textOffset = Offset(
                    x = (size.width - textSize.width) / 2f,
                    y = (size.height - textSize.height) / 2f
                )

                onDrawBehind {
                    drawCircle(
                        color = backgroundColor,
                        radius = size.maxDimension / 2 + 2.dp.toPx()
                    )
                    drawCircle(
                        color = borderColor,
                        style = Stroke(width = borderWidth)
                    )
                    drawText(
                        textMeasurer = textMeasurer,
                        text = "P",
                        topLeft = textOffset,
                        style = textStyle
                    )
                    rotate(-45f) {
                        drawLine(
                            color = borderColor,
                            start = size.center.copy(y = 0f),
                            end = size.center.copy(y = size.height),
                            strokeWidth = borderWidth,
                            cap = StrokeCap.Round,
                            alpha = 0.8f
                        )
                    }
                }
            }
    )
}

@Preview
@Composable
fun NoParkingMarkerPreview() {
    GlideAppTheme {
        NoParkingMarker()
    }
}
