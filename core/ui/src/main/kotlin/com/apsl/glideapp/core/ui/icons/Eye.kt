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

val GlideIcons.Eye: ImageVector by lazy {
    ImageVector.Builder(
        name = "Eye",
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
            moveTo(3.27489f, 15.2957f)
            curveTo(2.425f, 14.1915f, 2f, 13.6394f, 2f, 12f)
            curveTo(2f, 10.3606f, 2.425f, 9.8085f, 3.2749f, 8.7043f)
            curveTo(4.972f, 6.4996f, 7.8181f, 4f, 12f, 4f)
            curveTo(16.1819f, 4f, 19.028f, 6.4996f, 20.7251f, 8.7043f)
            curveTo(21.575f, 9.8085f, 22f, 10.3606f, 22f, 12f)
            curveTo(22f, 13.6394f, 21.575f, 14.1915f, 20.7251f, 15.2957f)
            curveTo(19.028f, 17.5004f, 16.1819f, 20f, 12f, 20f)
            curveTo(7.8181f, 20f, 4.972f, 17.5004f, 3.2749f, 15.2957f)
            close()
        }
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
            moveTo(15f, 12f)
            curveTo(15f, 13.6569f, 13.6569f, 15f, 12f, 15f)
            curveTo(10.3431f, 15f, 9f, 13.6569f, 9f, 12f)
            curveTo(9f, 10.3431f, 10.3431f, 9f, 12f, 9f)
            curveTo(13.6569f, 9f, 15f, 10.3431f, 15f, 12f)
            close()
        }
    }.build()
}
