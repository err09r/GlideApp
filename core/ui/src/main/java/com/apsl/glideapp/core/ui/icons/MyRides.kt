package com.apsl.glideapp.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _myRides: ImageVector? = null

val GlideIcons.MyRides: ImageVector
    get() {
        if (_myRides != null) {
            return _myRides!!
        }
        _myRides = ImageVector.Builder(
            name = "MyRides",
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
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(18f, 16.9208f)
                curveTo(19.1395f, 16.8215f, 19.9218f, 16.5974f, 20.5376f, 16.092f)
                curveTo(20.7401f, 15.9258f, 20.9258f, 15.7401f, 21.092f, 15.5376f)
                curveTo(22f, 14.4312f, 22f, 12.7875f, 22f, 9.5f)
                curveTo(22f, 6.2125f, 22f, 4.5688f, 21.092f, 3.4624f)
                curveTo(20.9258f, 3.2599f, 20.7401f, 3.0742f, 20.5376f, 2.908f)
                curveTo(19.4312f, 2f, 17.7875f, 2f, 14.5f, 2f)
                horizontalLineTo(9.5f)
                curveTo(6.2125f, 2f, 4.5688f, 2f, 3.4624f, 2.908f)
                curveTo(3.2599f, 3.0742f, 3.0742f, 3.2599f, 2.908f, 3.4624f)
                curveTo(2f, 4.5688f, 2f, 6.2125f, 2f, 9.5f)
                curveTo(2f, 12.7875f, 2f, 14.4312f, 2.908f, 15.5376f)
                curveTo(3.0742f, 15.7401f, 3.2599f, 15.9258f, 3.4624f, 16.092f)
                curveTo(4.0782f, 16.5974f, 4.8605f, 16.8215f, 6f, 16.9208f)
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
                moveTo(20.5f, 15.5f)
                lineTo(14f, 10.5f)
                moveTo(3.5f, 3f)
                lineTo(14f, 10.5f)
                moveTo(20.5f, 3.5f)
                lineTo(14f, 10.5f)
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
                moveTo(15.2673f, 19.2006f)
                lineTo(13.932f, 16.5295f)
                curveTo(13.089f, 14.8432f, 12.6675f, 14f, 12f, 14f)
                curveTo(11.3325f, 14f, 10.911f, 14.8432f, 10.068f, 16.5295f)
                lineTo(8.73273f, 19.2006f)
                curveTo(8.2209f, 20.2245f, 7.9649f, 20.7365f, 8.0039f, 21.0588f)
                curveTo(8.0601f, 21.5245f, 8.4104f, 21.9006f, 8.8708f, 21.9897f)
                curveTo(9.1895f, 22.0514f, 9.7182f, 21.8324f, 10.7757f, 21.3943f)
                curveTo(11.1669f, 21.2322f, 11.3625f, 21.1512f, 11.5629f, 21.1097f)
                curveTo(11.8513f, 21.0501f, 12.1487f, 21.0501f, 12.4371f, 21.1097f)
                curveTo(12.6375f, 21.1512f, 12.8331f, 21.2322f, 13.2243f, 21.3943f)
                curveTo(14.2818f, 21.8324f, 14.8105f, 22.0514f, 15.1292f, 21.9897f)
                curveTo(15.5896f, 21.9006f, 15.9399f, 21.5245f, 15.9961f, 21.0588f)
                curveTo(16.0351f, 20.7365f, 15.7791f, 20.2245f, 15.2673f, 19.2006f)
                close()
            }
        }.build()
        return _myRides!!
    }
