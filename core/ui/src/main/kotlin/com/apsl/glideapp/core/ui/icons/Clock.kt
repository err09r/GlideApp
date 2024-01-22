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

val GlideIcons.Clock: ImageVector by lazy {
    ImageVector.Builder(
        name = "Clock",
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
            moveTo(12f, 2.75f)
            curveTo(6.8914f, 2.75f, 2.75f, 6.8914f, 2.75f, 12f)
            curveTo(2.75f, 17.1086f, 6.8914f, 21.25f, 12f, 21.25f)
            curveTo(17.1086f, 21.25f, 21.25f, 17.1086f, 21.25f, 12f)
            curveTo(21.25f, 6.8914f, 17.1086f, 2.75f, 12f, 2.75f)
            close()
            moveTo(1.25f, 12f)
            curveTo(1.25f, 6.0629f, 6.0629f, 1.25f, 12f, 1.25f)
            curveTo(17.9371f, 1.25f, 22.75f, 6.0629f, 22.75f, 12f)
            curveTo(22.75f, 17.9371f, 17.9371f, 22.75f, 12f, 22.75f)
            curveTo(6.0629f, 22.75f, 1.25f, 17.9371f, 1.25f, 12f)
            close()
            moveTo(12f, 7.25f)
            curveTo(12.4142f, 7.25f, 12.75f, 7.5858f, 12.75f, 8f)
            verticalLineTo(11.6893f)
            lineTo(15.0303f, 13.9697f)
            curveTo(15.3232f, 14.2626f, 15.3232f, 14.7374f, 15.0303f, 15.0303f)
            curveTo(14.7374f, 15.3232f, 14.2626f, 15.3232f, 13.9697f, 15.0303f)
            lineTo(11.4697f, 12.5303f)
            curveTo(11.329f, 12.3897f, 11.25f, 12.1989f, 11.25f, 12f)
            verticalLineTo(8f)
            curveTo(11.25f, 7.5858f, 11.5858f, 7.25f, 12f, 7.25f)
            close()
        }
    }.build()
}
