package com.apsl.glideapp.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _route: ImageVector? = null

public val GlideIcons.Route: ImageVector
    get() {
        if (_route != null) {
            return _route!!
        }
        _route = ImageVector.Builder(
            name = "Route",
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
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(20f, 19f)
                lineTo(20.5303f, 19.5303f)
                curveTo(20.8232f, 19.2374f, 20.8232f, 18.7626f, 20.5303f, 18.4697f)
                lineTo(20f, 19f)
                close()
                moveTo(8f, 4.25f)
                curveTo(7.5858f, 4.25f, 7.25f, 4.5858f, 7.25f, 5f)
                curveTo(7.25f, 5.4142f, 7.5858f, 5.75f, 8f, 5.75f)
                verticalLineTo(4.25f)
                close()
                moveTo(18.5303f, 16.4697f)
                curveTo(18.2374f, 16.1768f, 17.7626f, 16.1768f, 17.4697f, 16.4697f)
                curveTo(17.1768f, 16.7626f, 17.1768f, 17.2374f, 17.4697f, 17.5303f)
                lineTo(18.5303f, 16.4697f)
                close()
                moveTo(17.4697f, 20.4697f)
                curveTo(17.1768f, 20.7626f, 17.1768f, 21.2374f, 17.4697f, 21.5303f)
                curveTo(17.7626f, 21.8232f, 18.2374f, 21.8232f, 18.5303f, 21.5303f)
                lineTo(17.4697f, 20.4697f)
                close()
                moveTo(20f, 18.25f)
                horizontalLineTo(7.5f)
                verticalLineTo(19.75f)
                horizontalLineTo(20f)
                verticalLineTo(18.25f)
                close()
                moveTo(7.5f, 12.75f)
                horizontalLineTo(16.5f)
                verticalLineTo(11.25f)
                horizontalLineTo(7.5f)
                verticalLineTo(12.75f)
                close()
                moveTo(16.5f, 4.25f)
                horizontalLineTo(8f)
                verticalLineTo(5.75f)
                horizontalLineTo(16.5f)
                verticalLineTo(4.25f)
                close()
                moveTo(20.5303f, 18.4697f)
                lineTo(18.5303f, 16.4697f)
                lineTo(17.4697f, 17.5303f)
                lineTo(19.4697f, 19.5303f)
                lineTo(20.5303f, 18.4697f)
                close()
                moveTo(19.4697f, 18.4697f)
                lineTo(17.4697f, 20.4697f)
                lineTo(18.5303f, 21.5303f)
                lineTo(20.5303f, 19.5303f)
                lineTo(19.4697f, 18.4697f)
                close()
                moveTo(20.75f, 8.5f)
                curveTo(20.75f, 6.1528f, 18.8472f, 4.25f, 16.5f, 4.25f)
                verticalLineTo(5.75f)
                curveTo(18.0188f, 5.75f, 19.25f, 6.9812f, 19.25f, 8.5f)
                horizontalLineTo(20.75f)
                close()
                moveTo(16.5f, 12.75f)
                curveTo(18.8472f, 12.75f, 20.75f, 10.8472f, 20.75f, 8.5f)
                horizontalLineTo(19.25f)
                curveTo(19.25f, 10.0188f, 18.0188f, 11.25f, 16.5f, 11.25f)
                verticalLineTo(12.75f)
                close()
                moveTo(4.75f, 15.5f)
                curveTo(4.75f, 13.9812f, 5.9812f, 12.75f, 7.5f, 12.75f)
                verticalLineTo(11.25f)
                curveTo(5.1528f, 11.25f, 3.25f, 13.1528f, 3.25f, 15.5f)
                horizontalLineTo(4.75f)
                close()
                moveTo(7.5f, 18.25f)
                curveTo(5.9812f, 18.25f, 4.75f, 17.0188f, 4.75f, 15.5f)
                horizontalLineTo(3.25f)
                curveTo(3.25f, 17.8472f, 5.1528f, 19.75f, 7.5f, 19.75f)
                verticalLineTo(18.25f)
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
                moveTo(8f, 5f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 6f, 7f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 4f, 5f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 8f, 5f)
                close()
            }
        }.build()
        return _route!!
    }
