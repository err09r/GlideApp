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

val GlideIcons.Help: ImageVector by lazy {
    ImageVector.Builder(
        name = "Help",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = null,
            fillAlpha = 1.0f,
            stroke = SolidColor(Color(0xFF1C274C)),
            strokeAlpha = 1.0f,
            strokeLineWidth = 1.5f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Miter,
            strokeLineMiter = 1.0f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(22f, 12f)
            arcTo(10f, 10f, 0f, isMoreThanHalf = false, isPositiveArc = true, 12f, 22f)
            arcTo(10f, 10f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2f, 12f)
            arcTo(10f, 10f, 0f, isMoreThanHalf = false, isPositiveArc = true, 22f, 12f)
            close()
        }
        path(
            fill = null,
            fillAlpha = 1.0f,
            stroke = SolidColor(Color(0xFF1C274C)),
            strokeAlpha = 1.0f,
            strokeLineWidth = 1.5f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Miter,
            strokeLineMiter = 1.0f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(10.125f, 8.875f)
            curveTo(10.125f, 7.8395f, 10.9645f, 7f, 12f, 7f)
            curveTo(13.0355f, 7f, 13.875f, 7.8395f, 13.875f, 8.875f)
            curveTo(13.875f, 9.5625f, 13.505f, 10.1635f, 12.9534f, 10.4899f)
            curveTo(12.478f, 10.7711f, 12f, 11.1977f, 12f, 11.75f)
            verticalLineTo(13f)
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
            moveTo(13f, 16f)
            arcTo(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 12f, 17f)
            arcTo(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 11f, 16f)
            arcTo(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 13f, 16f)
            close()
        }
    }.build()
}
