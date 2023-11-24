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

val GlideIcons.Question: ImageVector by lazy {
    ImageVector.Builder(
        name = "Question",
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
            moveTo(12f, 7.75f)
            curveTo(11.3787f, 7.75f, 10.875f, 8.2537f, 10.875f, 8.875f)
            curveTo(10.875f, 9.2892f, 10.5392f, 9.625f, 10.125f, 9.625f)
            curveTo(9.7108f, 9.625f, 9.375f, 9.2892f, 9.375f, 8.875f)
            curveTo(9.375f, 7.4253f, 10.5503f, 6.25f, 12f, 6.25f)
            curveTo(13.4497f, 6.25f, 14.625f, 7.4253f, 14.625f, 8.875f)
            curveTo(14.625f, 9.8383f, 14.1056f, 10.6796f, 13.3353f, 11.1354f)
            curveTo(13.1385f, 11.2518f, 12.9761f, 11.3789f, 12.8703f, 11.5036f)
            curveTo(12.7675f, 11.6246f, 12.75f, 11.7036f, 12.75f, 11.75f)
            verticalLineTo(13f)
            curveTo(12.75f, 13.4142f, 12.4142f, 13.75f, 12f, 13.75f)
            curveTo(11.5858f, 13.75f, 11.25f, 13.4142f, 11.25f, 13f)
            verticalLineTo(11.75f)
            curveTo(11.25f, 11.2441f, 11.4715f, 10.8336f, 11.7266f, 10.533f)
            curveTo(11.9786f, 10.236f, 12.2929f, 10.0092f, 12.5715f, 9.8444f)
            curveTo(12.9044f, 9.6474f, 13.125f, 9.2866f, 13.125f, 8.875f)
            curveTo(13.125f, 8.2537f, 12.6213f, 7.75f, 12f, 7.75f)
            close()
            moveTo(12f, 17f)
            curveTo(12.5523f, 17f, 13f, 16.5523f, 13f, 16f)
            curveTo(13f, 15.4477f, 12.5523f, 15f, 12f, 15f)
            curveTo(11.4477f, 15f, 11f, 15.4477f, 11f, 16f)
            curveTo(11f, 16.5523f, 11.4477f, 17f, 12f, 17f)
            close()
        }
    }.build()
}
