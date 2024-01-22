@file:Suppress("Unused")

package com.apsl.glideapp.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val GlideIcons.Flag: ImageVector by lazy {
    ImageVector.Builder(
        name = "Flag",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF1C274C)),
            fillAlpha = 1.0f,
            stroke = null,
            strokeAlpha = 1.0f,
            strokeLineWidth = 1.0f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Miter,
            strokeLineMiter = 1.0f,
            pathFillType = PathFillType.EvenOdd
        ) {
            moveTo(5f, 1.25f)
            curveTo(5.4142f, 1.25f, 5.75f, 1.5858f, 5.75f, 2f)
            verticalLineTo(3.08515f)
            lineTo(7.32358f, 2.77043f)
            curveTo(9.1168f, 2.4118f, 10.9756f, 2.5825f, 12.6735f, 3.2616f)
            lineTo(12.8771f, 3.34307f)
            curveTo(14.2919f, 3.909f, 15.849f, 4.0147f, 17.3273f, 3.6451f)
            curveTo(18.5579f, 3.3374f, 19.75f, 4.2682f, 19.75f, 5.5367f)
            verticalLineTo(12.9037f)
            curveTo(19.75f, 13.8922f, 19.0773f, 14.7538f, 18.1183f, 14.9935f)
            lineTo(17.9039f, 15.0471f)
            curveTo(15.9814f, 15.5277f, 13.9563f, 15.3903f, 12.1164f, 14.6543f)
            curveTo(10.6886f, 14.0832f, 9.1256f, 13.9397f, 7.6178f, 14.2413f)
            lineTo(5.75f, 14.6149f)
            verticalLineTo(22f)
            curveTo(5.75f, 22.4142f, 5.4142f, 22.75f, 5f, 22.75f)
            curveTo(4.5858f, 22.75f, 4.25f, 22.4142f, 4.25f, 22f)
            verticalLineTo(2f)
            curveTo(4.25f, 1.5858f, 4.5858f, 1.25f, 5f, 1.25f)
            close()
            moveTo(5.75f, 13.0851f)
            lineTo(7.32358f, 12.7704f)
            curveTo(9.1168f, 12.4118f, 10.9756f, 12.5825f, 12.6735f, 13.2616f)
            curveTo(14.2206f, 13.8805f, 15.9235f, 13.996f, 17.5401f, 13.5919f)
            lineTo(17.7545f, 13.5383f)
            curveTo(18.0457f, 13.4655f, 18.25f, 13.2039f, 18.25f, 12.9037f)
            verticalLineTo(5.53669f)
            curveTo(18.25f, 5.244f, 17.975f, 5.0293f, 17.6911f, 5.1003f)
            curveTo(15.9069f, 5.5464f, 14.0276f, 5.4188f, 12.32f, 4.7358f)
            lineTo(12.1164f, 4.65433f)
            curveTo(10.6886f, 4.0832f, 9.1256f, 3.9397f, 7.6178f, 4.2413f)
            lineTo(5.75f, 4.61485f)
            verticalLineTo(13.0851f)
            close()
        }
    }.build()
}
