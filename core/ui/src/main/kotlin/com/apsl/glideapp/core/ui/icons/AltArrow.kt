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

val GlideIcons.AltArrow: ImageVector by lazy {
    ImageVector.Builder(
        name = "AltArrow",
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
            moveTo(8.51192f, 4.43057f)
            curveTo(8.8264f, 4.161f, 9.2999f, 4.1974f, 9.5695f, 4.5119f)
            lineTo(15.5695f, 11.5119f)
            curveTo(15.8102f, 11.7928f, 15.8102f, 12.2072f, 15.5695f, 12.4881f)
            lineTo(9.56946f, 19.4881f)
            curveTo(9.2999f, 19.8026f, 8.8264f, 19.839f, 8.5119f, 19.5695f)
            curveTo(8.1974f, 19.2999f, 8.161f, 18.8264f, 8.4306f, 18.5119f)
            lineTo(14.0122f, 12f)
            lineTo(8.43057f, 5.48811f)
            curveTo(8.161f, 5.1736f, 8.1974f, 4.7001f, 8.5119f, 4.4306f)
            close()
        }
    }.build()
}
