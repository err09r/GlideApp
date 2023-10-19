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

val GlideIcons.Bell: ImageVector by lazy {
    ImageVector.Builder(
        name = "Bell",
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
            moveTo(12f, 1.25f)
            curveTo(7.7198f, 1.25f, 4.25f, 4.7198f, 4.25f, 9f)
            verticalLineTo(9.7041f)
            curveTo(4.25f, 10.401f, 4.0438f, 11.0824f, 3.6572f, 11.6622f)
            lineTo(2.50856f, 13.3851f)
            curveTo(1.1755f, 15.3848f, 2.1932f, 18.1028f, 4.5118f, 18.7351f)
            curveTo(5.2674f, 18.9412f, 6.0294f, 19.1155f, 6.7958f, 19.2581f)
            lineTo(6.79768f, 19.2632f)
            curveTo(7.5667f, 21.3151f, 9.622f, 22.75f, 12f, 22.75f)
            curveTo(14.378f, 22.75f, 16.4333f, 21.3151f, 17.2023f, 19.2632f)
            lineTo(17.2042f, 19.2581f)
            curveTo(17.9706f, 19.1155f, 18.7327f, 18.9412f, 19.4883f, 18.7351f)
            curveTo(21.8069f, 18.1028f, 22.8246f, 15.3848f, 21.4915f, 13.3851f)
            lineTo(20.3429f, 11.6622f)
            curveTo(19.9563f, 11.0824f, 19.75f, 10.401f, 19.75f, 9.7041f)
            verticalLineTo(9f)
            curveTo(19.75f, 4.7198f, 16.2802f, 1.25f, 12f, 1.25f)
            close()
            moveTo(15.3764f, 19.537f)
            curveTo(13.1335f, 19.805f, 10.8664f, 19.8049f, 8.6235f, 19.5369f)
            curveTo(9.3344f, 20.5585f, 10.571f, 21.25f, 12f, 21.25f)
            curveTo(13.4289f, 21.25f, 14.6655f, 20.5585f, 15.3764f, 19.537f)
            close()
            moveTo(5.75004f, 9f)
            curveTo(5.75f, 5.5482f, 8.5483f, 2.75f, 12f, 2.75f)
            curveTo(15.4518f, 2.75f, 18.25f, 5.5482f, 18.25f, 9f)
            verticalLineTo(9.7041f)
            curveTo(18.25f, 10.6972f, 18.544f, 11.668f, 19.0948f, 12.4943f)
            lineTo(20.2434f, 14.2172f)
            curveTo(21.0086f, 15.3649f, 20.4245f, 16.925f, 19.0936f, 17.288f)
            curveTo(14.4494f, 18.5546f, 9.5507f, 18.5546f, 4.9064f, 17.288f)
            curveTo(3.5756f, 16.925f, 2.9915f, 15.3649f, 3.7566f, 14.2172f)
            lineTo(4.90524f, 12.4943f)
            curveTo(5.4561f, 11.668f, 5.75f, 10.6972f, 5.75f, 9.7041f)
            verticalLineTo(9f)
            close()
        }
    }.build()
}

