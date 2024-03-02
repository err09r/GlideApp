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

val GlideIcons.Cross: ImageVector by lazy {
    ImageVector.Builder(
        name = "Cross",
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
            strokeLineWidth = 10.363f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Miter,
            strokeLineMiter = 1.0f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(4.398602f, 4.398602f)
            curveTo(4.6924f, 4.1048f, 5.1619f, 4.1013f, 5.4513f, 4.3907f)
            lineTo(19.609318f, 18.548658f)
            curveToRelative(0.2894f, 0.2894f, 0.2859f, 0.7589f, -0.0079f, 1.0527f)
            curveToRelative(-0.2938f, 0.2938f, -0.7633f, 0.2973f, -1.0527f, 0.0079f)
            lineTo(4.3906817f, 5.451342f)
            curveTo(4.1013f, 5.1619f, 4.1048f, 4.6924f, 4.3986f, 4.3986f)
            close()
            moveToRelative(15.202796f, 0f)
            curveToRelative(0.2938f, 0.2938f, 0.2973f, 0.7633f, 0.0079f, 1.0527f)
            lineTo(5.451342f, 19.609318f)
            curveToRelative(-0.2894f, 0.2894f, -0.7589f, 0.2859f, -1.0527f, -0.0079f)
            curveToRelative(-0.2938f, -0.2938f, -0.2973f, -0.7633f, -0.0079f, -1.0527f)
            lineTo(18.548658f, 4.3906817f)
            curveToRelative(0.2894f, -0.2894f, 0.7589f, -0.2859f, 1.0527f, 0.0079f)
            close()
        }
    }.build()
}
