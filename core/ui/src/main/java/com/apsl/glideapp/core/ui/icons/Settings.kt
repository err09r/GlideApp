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

val GlideIcons.Settings: ImageVector by lazy {
    ImageVector.Builder(
        name = "Settings",
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
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Miter,
            strokeLineMiter = 1.0f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(15f, 12f)
            arcTo(3f, 3f, 0f, isMoreThanHalf = false, isPositiveArc = true, 12f, 15f)
            arcTo(3f, 3f, 0f, isMoreThanHalf = false, isPositiveArc = true, 9f, 12f)
            arcTo(3f, 3f, 0f, isMoreThanHalf = false, isPositiveArc = true, 15f, 12f)
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
            moveTo(13.7654f, 2.15224f)
            curveTo(13.3978f, 2f, 12.9319f, 2f, 12f, 2f)
            curveTo(11.0681f, 2f, 10.6022f, 2f, 10.2346f, 2.1522f)
            curveTo(9.7446f, 2.3552f, 9.3552f, 2.7446f, 9.1522f, 3.2346f)
            curveTo(9.0596f, 3.4583f, 9.0233f, 3.7185f, 9.0091f, 4.098f)
            curveTo(8.9883f, 4.6557f, 8.7023f, 5.1719f, 8.2189f, 5.4509f)
            curveTo(7.7356f, 5.73f, 7.1456f, 5.7195f, 6.6522f, 5.4588f)
            curveTo(6.3164f, 5.2813f, 6.073f, 5.1826f, 5.8329f, 5.151f)
            curveTo(5.307f, 5.0818f, 4.7752f, 5.2243f, 4.3544f, 5.5472f)
            curveTo(4.0387f, 5.7894f, 3.8058f, 6.1929f, 3.3398f, 6.9999f)
            curveTo(2.8739f, 7.807f, 2.6409f, 8.2105f, 2.589f, 8.6049f)
            curveTo(2.5198f, 9.1308f, 2.6623f, 9.6627f, 2.9852f, 10.0835f)
            curveTo(3.1326f, 10.2756f, 3.3397f, 10.437f, 3.6612f, 10.639f)
            curveTo(4.1338f, 10.936f, 4.4379f, 11.4419f, 4.4379f, 12f)
            curveTo(4.4378f, 12.5581f, 4.1338f, 13.0639f, 3.6612f, 13.3608f)
            curveTo(3.3396f, 13.5629f, 3.1325f, 13.7244f, 2.9851f, 13.9165f)
            curveTo(2.6622f, 14.3373f, 2.5197f, 14.8691f, 2.5889f, 15.395f)
            curveTo(2.6408f, 15.7894f, 2.8738f, 16.193f, 3.3397f, 17f)
            curveTo(3.8057f, 17.807f, 4.0386f, 18.2106f, 4.3543f, 18.4527f)
            curveTo(4.7751f, 18.7756f, 5.3069f, 18.9181f, 5.8328f, 18.8489f)
            curveTo(6.0729f, 18.8173f, 6.3163f, 18.7186f, 6.652f, 18.5412f)
            curveTo(7.1455f, 18.2804f, 7.7356f, 18.27f, 8.2189f, 18.549f)
            curveTo(8.7022f, 18.8281f, 8.9883f, 19.3443f, 9.0091f, 19.9021f)
            curveTo(9.0233f, 20.2815f, 9.0596f, 20.5417f, 9.1522f, 20.7654f)
            curveTo(9.3552f, 21.2554f, 9.7446f, 21.6448f, 10.2346f, 21.8478f)
            curveTo(10.6022f, 22f, 11.0681f, 22f, 12f, 22f)
            curveTo(12.9319f, 22f, 13.3978f, 22f, 13.7654f, 21.8478f)
            curveTo(14.2554f, 21.6448f, 14.6448f, 21.2554f, 14.8477f, 20.7654f)
            curveTo(14.9404f, 20.5417f, 14.9767f, 20.2815f, 14.9909f, 19.902f)
            curveTo(15.0117f, 19.3443f, 15.2977f, 18.8281f, 15.781f, 18.549f)
            curveTo(16.2643f, 18.2699f, 16.8544f, 18.2804f, 17.3479f, 18.5412f)
            curveTo(17.6836f, 18.7186f, 17.927f, 18.8172f, 18.167f, 18.8488f)
            curveTo(18.6929f, 18.9181f, 19.2248f, 18.7756f, 19.6456f, 18.4527f)
            curveTo(19.9612f, 18.2105f, 20.1942f, 17.807f, 20.6601f, 16.9999f)
            curveTo(21.1261f, 16.1929f, 21.3591f, 15.7894f, 21.411f, 15.395f)
            curveTo(21.4802f, 14.8691f, 21.3377f, 14.3372f, 21.0148f, 13.9164f)
            curveTo(20.8674f, 13.7243f, 20.6602f, 13.5628f, 20.3387f, 13.3608f)
            curveTo(19.8662f, 13.0639f, 19.5621f, 12.558f, 19.5621f, 11.9999f)
            curveTo(19.5621f, 11.4418f, 19.8662f, 10.9361f, 20.3387f, 10.6392f)
            curveTo(20.6603f, 10.4371f, 20.8675f, 10.2757f, 21.0149f, 10.0835f)
            curveTo(21.3378f, 9.6627f, 21.4803f, 9.1309f, 21.4111f, 8.605f)
            curveTo(21.3592f, 8.2105f, 21.1262f, 7.807f, 20.6602f, 7f)
            curveTo(20.1943f, 6.193f, 19.9613f, 5.7895f, 19.6457f, 5.5473f)
            curveTo(19.2249f, 5.2244f, 18.693f, 5.0819f, 18.1671f, 5.1511f)
            curveTo(17.9271f, 5.1827f, 17.6837f, 5.2814f, 17.3479f, 5.4588f)
            curveTo(16.8545f, 5.7196f, 16.2644f, 5.73f, 15.7811f, 5.451f)
            curveTo(15.2977f, 5.1719f, 15.0117f, 4.6557f, 14.9909f, 4.0979f)
            curveTo(14.9767f, 3.7185f, 14.9404f, 3.4583f, 14.8477f, 3.2346f)
            curveTo(14.6448f, 2.7446f, 14.2554f, 2.3552f, 13.7654f, 2.1522f)
            close()
        }
    }.build()
}
