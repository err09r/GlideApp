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

val GlideIcons.Warning: ImageVector by lazy {
    ImageVector.Builder(
        name = "Warning",
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
            moveTo(12f, 7.25f)
            curveTo(12.4142f, 7.25f, 12.75f, 7.5858f, 12.75f, 8f)
            verticalLineTo(13f)
            curveTo(12.75f, 13.4142f, 12.4142f, 13.75f, 12f, 13.75f)
            curveTo(11.5858f, 13.75f, 11.25f, 13.4142f, 11.25f, 13f)
            verticalLineTo(8f)
            curveTo(11.25f, 7.5858f, 11.5858f, 7.25f, 12f, 7.25f)
            close()
        }
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
            moveTo(12f, 17f)
            curveTo(12.5523f, 17f, 13f, 16.5523f, 13f, 16f)
            curveTo(13f, 15.4477f, 12.5523f, 15f, 12f, 15f)
            curveTo(11.4477f, 15f, 11f, 15.4477f, 11f, 16f)
            curveTo(11f, 16.5523f, 11.4477f, 17f, 12f, 17f)
            close()
        }
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
            moveTo(8.2944f, 4.47643f)
            curveTo(9.3663f, 3.1149f, 10.5018f, 2.25f, 12f, 2.25f)
            curveTo(13.4981f, 2.25f, 14.6336f, 3.1149f, 15.7056f, 4.4764f)
            curveTo(16.7598f, 5.8154f, 17.8769f, 7.7962f, 19.3063f, 10.3305f)
            lineTo(19.7418f, 11.1027f)
            curveTo(20.9234f, 13.1976f, 21.8566f, 14.8523f, 22.3468f, 16.1804f)
            curveTo(22.8478f, 17.5376f, 22.9668f, 18.7699f, 22.209f, 19.8569f)
            curveTo(21.4736f, 20.9118f, 20.2466f, 21.3434f, 18.6991f, 21.5471f)
            curveTo(17.1576f, 21.75f, 15.0845f, 21.75f, 12.4248f, 21.75f)
            horizontalLineTo(11.5752f)
            curveTo(8.9155f, 21.75f, 6.8424f, 21.75f, 5.3008f, 21.5471f)
            curveTo(3.7533f, 21.3434f, 2.5264f, 20.9118f, 1.791f, 19.8569f)
            curveTo(1.0332f, 18.7699f, 1.1522f, 17.5376f, 1.6531f, 16.1804f)
            curveTo(2.1433f, 14.8523f, 3.0766f, 13.1977f, 4.2582f, 11.1027f)
            lineTo(4.69361f, 10.3307f)
            curveTo(6.123f, 7.7963f, 7.2402f, 5.8155f, 8.2944f, 4.4764f)
            close()
            moveTo(9.47297f, 5.40432f)
            curveTo(8.499f, 6.6415f, 7.437f, 8.5199f, 5.9649f, 11.1299f)
            lineTo(5.60129f, 11.7747f)
            curveTo(4.3751f, 13.9488f, 3.5037f, 15.4986f, 3.0603f, 16.6998f)
            curveTo(2.6227f, 17.8855f, 2.6834f, 18.5141f, 3.0215f, 18.9991f)
            curveTo(3.382f, 19.5163f, 4.0587f, 19.8706f, 5.4966f, 20.0599f)
            curveTo(6.9286f, 20.2484f, 8.9026f, 20.25f, 11.6363f, 20.25f)
            horizontalLineTo(12.3636f)
            curveTo(15.0974f, 20.25f, 17.0714f, 20.2484f, 18.5034f, 20.0599f)
            curveTo(19.9412f, 19.8706f, 20.6179f, 19.5163f, 20.9785f, 18.9991f)
            curveTo(21.3166f, 18.5141f, 21.3773f, 17.8855f, 20.9396f, 16.6998f)
            curveTo(20.4963f, 15.4986f, 19.6249f, 13.9488f, 18.3987f, 11.7747f)
            lineTo(18.035f, 11.1299f)
            curveTo(16.5629f, 8.5199f, 15.501f, 6.6415f, 14.527f, 5.4043f)
            curveTo(13.562f, 4.1787f, 12.8126f, 3.75f, 12f, 3.75f)
            curveTo(11.1874f, 3.75f, 10.4379f, 4.1787f, 9.473f, 5.4043f)
            close()
        }
    }.build()
}
