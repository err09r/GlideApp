package com.apsl.glideapp.feature.home.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.ui.toPx

@Composable
fun NoParkingMarker(modifier: Modifier = Modifier) {
    val backgroundColor = MaterialTheme.colorScheme.surface
    val textStyle = MaterialTheme.typography.titleMedium.copy(
        color = MaterialTheme.colorScheme.onSurface,
        fontSize = 11.sp
    )
    val borderWidth = 1.5.dp.toPx()
    val borderColor = Color.Red.copy(alpha = 0.8f)
    val textMeasurer = rememberTextMeasurer()
    Spacer(
        modifier = modifier
            .size(16.dp)
            .padding(1.dp)
            .drawWithCache {
                val textSize = textMeasurer.measure(text = "P", style = textStyle).size
                val textOffset = Offset(
                    x = (size.width - textSize.width) / 2f,
                    y = (size.height - textSize.height) / 2f
                )

                onDrawBehind {
                    drawCircle(color = backgroundColor)
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
                    drawLine(
                        color = borderColor,
                        start = Offset(x = size.width, y = size.height),
                        end = Offset(x = -size.width, y = -size.height),
                        strokeWidth = borderWidth,
                        cap = StrokeCap.Round,
                        blendMode = BlendMode.SrcAtop
                    )
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
