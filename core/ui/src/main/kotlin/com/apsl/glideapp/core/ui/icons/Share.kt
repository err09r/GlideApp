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

val GlideIcons.Share: ImageVector by lazy {
    ImageVector.Builder(
        name = "Share",
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
            moveTo(16.5f, 2.25f)
            curveTo(14.7051f, 2.25f, 13.25f, 3.7051f, 13.25f, 5.5f)
            curveTo(13.25f, 5.6959f, 13.2673f, 5.8878f, 13.3006f, 6.0741f)
            lineTo(8.56991f, 9.38558f)
            curveTo(8.5459f, 9.4024f, 8.5231f, 9.4204f, 8.5017f, 9.4394f)
            curveTo(7.9499f, 9.0075f, 7.255f, 8.75f, 6.5f, 8.75f)
            curveTo(4.7051f, 8.75f, 3.25f, 10.2051f, 3.25f, 12f)
            curveTo(3.25f, 13.7949f, 4.7051f, 15.25f, 6.5f, 15.25f)
            curveTo(7.255f, 15.25f, 7.9499f, 14.9925f, 8.5017f, 14.5606f)
            curveTo(8.5231f, 14.5796f, 8.5459f, 14.5976f, 8.5699f, 14.6144f)
            lineTo(13.3006f, 17.9259f)
            curveTo(13.2673f, 18.1122f, 13.25f, 18.3041f, 13.25f, 18.5f)
            curveTo(13.25f, 20.2949f, 14.7051f, 21.75f, 16.5f, 21.75f)
            curveTo(18.2949f, 21.75f, 19.75f, 20.2949f, 19.75f, 18.5f)
            curveTo(19.75f, 16.7051f, 18.2949f, 15.25f, 16.5f, 15.25f)
            curveTo(15.4472f, 15.25f, 14.5113f, 15.7506f, 13.9174f, 16.5267f)
            lineTo(9.43806f, 13.3911f)
            curveTo(9.6381f, 12.9694f, 9.75f, 12.4978f, 9.75f, 12f)
            curveTo(9.75f, 11.5022f, 9.6381f, 11.0306f, 9.4381f, 10.6089f)
            lineTo(13.9174f, 7.4733f)
            curveTo(14.5113f, 8.2494f, 15.4472f, 8.75f, 16.5f, 8.75f)
            curveTo(18.2949f, 8.75f, 19.75f, 7.2949f, 19.75f, 5.5f)
            curveTo(19.75f, 3.7051f, 18.2949f, 2.25f, 16.5f, 2.25f)
            close()
            moveTo(14.75f, 5.5f)
            curveTo(14.75f, 4.5335f, 15.5335f, 3.75f, 16.5f, 3.75f)
            curveTo(17.4665f, 3.75f, 18.25f, 4.5335f, 18.25f, 5.5f)
            curveTo(18.25f, 6.4665f, 17.4665f, 7.25f, 16.5f, 7.25f)
            curveTo(15.5335f, 7.25f, 14.75f, 6.4665f, 14.75f, 5.5f)
            close()
            moveTo(6.5f, 10.25f)
            curveTo(5.5335f, 10.25f, 4.75f, 11.0335f, 4.75f, 12f)
            curveTo(4.75f, 12.9665f, 5.5335f, 13.75f, 6.5f, 13.75f)
            curveTo(7.4665f, 13.75f, 8.25f, 12.9665f, 8.25f, 12f)
            curveTo(8.25f, 11.0335f, 7.4665f, 10.25f, 6.5f, 10.25f)
            close()
            moveTo(16.5f, 16.75f)
            curveTo(15.5335f, 16.75f, 14.75f, 17.5335f, 14.75f, 18.5f)
            curveTo(14.75f, 19.4665f, 15.5335f, 20.25f, 16.5f, 20.25f)
            curveTo(17.4665f, 20.25f, 18.25f, 19.4665f, 18.25f, 18.5f)
            curveTo(18.25f, 17.5335f, 17.4665f, 16.75f, 16.5f, 16.75f)
            close()
        }
    }.build()
}
