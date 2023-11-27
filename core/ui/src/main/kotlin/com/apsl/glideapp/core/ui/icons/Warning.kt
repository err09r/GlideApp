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

val GlideIcons.Warning: ImageVector by lazy {
    ImageVector.Builder(
        name = "Warning",
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
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(12f, 6.25f)
            curveTo(12.4142f, 6.25f, 12.75f, 6.5858f, 12.75f, 7f)
            verticalLineTo(13f)
            curveTo(12.75f, 13.4142f, 12.4142f, 13.75f, 12f, 13.75f)
            curveTo(11.5858f, 13.75f, 11.25f, 13.4142f, 11.25f, 13f)
            verticalLineTo(7f)
            curveTo(11.25f, 6.5858f, 11.5858f, 6.25f, 12f, 6.25f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF1C274C)),
            fillAlpha = 1.0f,
            stroke = null,
            strokeAlpha = 1.0f,
            strokeLineWidth = 1.0f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Miter,
            strokeLineMiter = 1.0f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(12f, 17f)
            curveTo(12.5523f, 17f, 13f, 16.5523f, 13f, 16f)
            curveTo(13f, 15.4477f, 12.5523f, 15f, 12f, 15f)
            curveTo(11.4477f, 15f, 11f, 15.4477f, 11f, 16f)
            curveTo(11f, 16.5523f, 11.4477f, 17f, 12f, 17f)
            close()
        }
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
            moveTo(1.25f, 12f)
            curveTo(1.25f, 6.0629f, 6.0629f, 1.25f, 12f, 1.25f)
            curveTo(17.9371f, 1.25f, 22.75f, 6.0629f, 22.75f, 12f)
            curveTo(22.75f, 17.9371f, 17.9371f, 22.75f, 12f, 22.75f)
            curveTo(6.0629f, 22.75f, 1.25f, 17.9371f, 1.25f, 12f)
            close()
            moveTo(12f, 2.75f)
            curveTo(6.8914f, 2.75f, 2.75f, 6.8914f, 2.75f, 12f)
            curveTo(2.75f, 17.1086f, 6.8914f, 21.25f, 12f, 21.25f)
            curveTo(17.1086f, 21.25f, 21.25f, 17.1086f, 21.25f, 12f)
            curveTo(21.25f, 6.8914f, 17.1086f, 2.75f, 12f, 2.75f)
            close()
        }
    }.build()
}
