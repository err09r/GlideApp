package com.apsl.glideapp.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _wallet: ImageVector? = null

val GlideIcons.Wallet: ImageVector
    get() {
        if (_wallet != null) {
            return _wallet!!
        }
        _wallet = ImageVector.Builder(
            name = "Wallet",
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
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(7f, 15f)
                lineTo(7f, 9f)
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
                moveTo(20.8333f, 9f)
                horizontalLineTo(18.2308f)
                curveTo(16.4465f, 9f, 15f, 10.3431f, 15f, 12f)
                curveTo(15f, 13.6569f, 16.4465f, 15f, 18.2308f, 15f)
                horizontalLineTo(20.8333f)
                curveTo(20.9167f, 15f, 20.9583f, 15f, 20.9935f, 14.9979f)
                curveTo(21.5328f, 14.965f, 21.9623f, 14.5662f, 21.9977f, 14.0654f)
                curveTo(22f, 14.0327f, 22f, 13.994f, 22f, 13.9167f)
                verticalLineTo(10.0833f)
                curveTo(22f, 10.006f, 22f, 9.9673f, 21.9977f, 9.9346f)
                curveTo(21.9623f, 9.4338f, 21.5328f, 9.035f, 20.9935f, 9.0021f)
                curveTo(20.9583f, 9f, 20.9167f, 9f, 20.8333f, 9f)
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
                moveTo(20.965f, 9f)
                curveTo(20.8873f, 7.1277f, 20.6366f, 5.9798f, 19.8284f, 5.1716f)
                curveTo(18.6569f, 4f, 16.7712f, 4f, 13f, 4f)
                lineTo(10f, 4f)
                curveTo(6.2288f, 4f, 4.3431f, 4f, 3.1716f, 5.1716f)
                curveTo(2f, 6.3431f, 2f, 8.2288f, 2f, 12f)
                curveTo(2f, 15.7712f, 2f, 17.6569f, 3.1716f, 18.8284f)
                curveTo(4.3431f, 20f, 6.2288f, 20f, 10f, 20f)
                horizontalLineTo(13f)
                curveTo(16.7712f, 20f, 18.6569f, 20f, 19.8284f, 18.8284f)
                curveTo(20.6366f, 18.0203f, 20.8873f, 16.8723f, 20.965f, 15f)
            }
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFF1C274C)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(17.9912f, 12f)
                horizontalLineTo(18.0002f)
            }
        }.build()
        return _wallet!!
    }
