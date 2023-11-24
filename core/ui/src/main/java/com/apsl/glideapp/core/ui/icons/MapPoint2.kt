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

val GlideIcons.MapPoint2: ImageVector by lazy {
    ImageVector.Builder(
        name = "MapPoint2",
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
            moveTo(3.25f, 10.1433f)
            curveTo(3.25f, 5.2443f, 7.155f, 1.25f, 12f, 1.25f)
            curveTo(16.845f, 1.25f, 20.75f, 5.2443f, 20.75f, 10.1433f)
            curveTo(20.75f, 12.5084f, 20.076f, 15.0479f, 18.8844f, 17.2419f)
            curveTo(17.6944f, 19.4331f, 15.9556f, 21.3372f, 13.7805f, 22.3539f)
            curveTo(12.6506f, 22.882f, 11.3494f, 22.882f, 10.2195f, 22.3539f)
            curveTo(8.0444f, 21.3372f, 6.3056f, 19.4331f, 5.1156f, 17.2419f)
            curveTo(3.924f, 15.0479f, 3.25f, 12.5084f, 3.25f, 10.1433f)
            close()
            moveTo(12f, 2.75f)
            curveTo(8.0084f, 2.75f, 4.75f, 6.0475f, 4.75f, 10.1433f)
            curveTo(4.75f, 12.2404f, 5.3526f, 14.5354f, 6.4337f, 16.526f)
            curveTo(7.5162f, 18.5192f, 9.046f, 20.1496f, 10.8546f, 20.995f)
            curveTo(11.5821f, 21.335f, 12.4179f, 21.335f, 13.1454f, 20.995f)
            curveTo(14.954f, 20.1496f, 16.4838f, 18.5192f, 17.5663f, 16.526f)
            curveTo(18.6474f, 14.5354f, 19.25f, 12.2404f, 19.25f, 10.1433f)
            curveTo(19.25f, 6.0475f, 15.9916f, 2.75f, 12f, 2.75f)
            close()
            moveTo(12f, 7.75f)
            curveTo(10.7574f, 7.75f, 9.75f, 8.7574f, 9.75f, 10f)
            curveTo(9.75f, 11.2426f, 10.7574f, 12.25f, 12f, 12.25f)
            curveTo(13.2426f, 12.25f, 14.25f, 11.2426f, 14.25f, 10f)
            curveTo(14.25f, 8.7574f, 13.2426f, 7.75f, 12f, 7.75f)
            close()
            moveTo(8.25f, 10f)
            curveTo(8.25f, 7.9289f, 9.9289f, 6.25f, 12f, 6.25f)
            curveTo(14.0711f, 6.25f, 15.75f, 7.9289f, 15.75f, 10f)
            curveTo(15.75f, 12.0711f, 14.0711f, 13.75f, 12f, 13.75f)
            curveTo(9.9289f, 13.75f, 8.25f, 12.0711f, 8.25f, 10f)
            close()
        }
    }.build()
}
