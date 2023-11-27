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

val GlideIcons.ArrowBack: ImageVector by lazy {
    ImageVector.Builder(
        name = "ArrowBack",
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
            moveTo(10.5303f, 5.46967f)
            curveTo(10.8232f, 5.7626f, 10.8232f, 6.2374f, 10.5303f, 6.5303f)
            lineTo(5.81066f, 11.25f)
            horizontalLineTo(20f)
            curveTo(20.4142f, 11.25f, 20.75f, 11.5858f, 20.75f, 12f)
            curveTo(20.75f, 12.4142f, 20.4142f, 12.75f, 20f, 12.75f)
            horizontalLineTo(5.81066f)
            lineTo(10.5303f, 17.4697f)
            curveTo(10.8232f, 17.7626f, 10.8232f, 18.2374f, 10.5303f, 18.5303f)
            curveTo(10.2374f, 18.8232f, 9.7626f, 18.8232f, 9.4697f, 18.5303f)
            lineTo(3.46967f, 12.5303f)
            curveTo(3.1768f, 12.2374f, 3.1768f, 11.7626f, 3.4697f, 11.4697f)
            lineTo(9.46967f, 5.46967f)
            curveTo(9.7626f, 5.1768f, 10.2374f, 5.1768f, 10.5303f, 5.4697f)
            close()
        }
    }.build()
}
