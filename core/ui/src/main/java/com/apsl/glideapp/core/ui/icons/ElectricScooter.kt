package com.apsl.glideapp.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _electricScooter: ImageVector? = null

val GlideIcons.ElectricScooter: ImageVector
    get() {
        if (_electricScooter != null) {
            return _electricScooter!!
        }
        _electricScooter = ImageVector.Builder(
            name = "ElectricScooter",
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
                moveTo(14.381f, 15.037322f)
                verticalLineToRelative(0.75f)
                curveToRelative(0.4142f, 0f, 0.75f, -0.3358f, 0.75f, -0.75f)
                close()
                moveToRelative(5.238f, -5.1765004f)
                verticalLineToRelative(0.7500004f)
                curveToRelative(0.2208f, 0f, 0.4303f, -0.0972f, 0.5728f, -0.2658f)
                curveToRelative(0.1425f, -0.1685f, 0.2035f, -0.3913f, 0.1668f, -0.6089f)
                close()
                moveToRelative(-1.0041f, -5.9539699f)
                lineToRelative(-0.7395f, 0.12473f)
                close()
                moveTo(14.381f, 0.64022177f)
                curveToRelative(-0.4143f, 0f, -0.75f, 0.3358f, -0.75f, 0.75f)
                curveToRelative(0f, 0.4142f, 0.3357f, 0.75f, 0.75f, 0.75f)
                close()
                moveToRelative(3.782f, 1.59002993f)
                lineToRelative(-0.6223f, 0.41871f)
                verticalLineToRelative(0f)
                close()
                moveToRelative(-0.8065f, -0.67513f)
                lineToRelative(0.304f, -0.68563993f)
                verticalLineToRelative(0f)
                close()
                moveTo(21.25f, 15.037322f)
                curveToRelative(0f, 0.8769f, -0.7218f, 1.6029f, -1.631f, 1.6029f)
                verticalLineToRelative(1.5f)
                curveToRelative(1.7208f, 0f, 3.131f, -1.3809f, 3.131f, -3.1029f)
                close()
                moveToRelative(-1.631f, 1.6029f)
                curveToRelative(-0.9091f, 0f, -1.6309f, -0.726f, -1.6309f, -1.6029f)
                horizontalLineToRelative(-1.5f)
                curveToRelative(0f, 1.722f, 1.4102f, 3.1029f, 3.1309f, 3.1029f)
                close()
                moveToRelative(-1.6309f, -1.6029f)
                curveToRelative(0f, -0.877f, 0.7218f, -1.603f, 1.6309f, -1.603f)
                verticalLineToRelative(-1.5f)
                curveToRelative(-1.7207f, 0f, -3.1309f, 1.3809f, -3.1309f, 3.103f)
                close()
                moveToRelative(1.6309f, -1.603f)
                curveToRelative(0.9092f, 0f, 1.631f, 0.726f, 1.631f, 1.603f)
                horizontalLineToRelative(1.5f)
                curveToRelative(0f, -1.7221f, -1.4102f, -3.103f, -3.131f, -3.103f)
                close()
                moveToRelative(-4.488f, 1.603f)
                curveToRelative(0f, -2.4364f, 2.0009f, -4.4265f, 4.488f, -4.4265f)
                verticalLineTo(9.1108217f)
                curveToRelative(-3.2987f, 0f, -5.988f, 2.645f, -5.988f, 5.9265f)
                close()
                moveToRelative(-0.75f, -0.75f)
                horizontalLineTo(6.7619f)
                verticalLineToRelative(1.5f)
                horizontalLineToRelative(7.6191f)
                close()
                moveToRelative(5.9776f, -4.5512004f)
                lineToRelative(-1.0041f, -5.9539899f)
                lineToRelative(-1.4791f, 0.24945f)
                lineToRelative(1.0041f, 5.9539403f)
                close()
                moveTo(15.6088f, 0.64022177f)
                horizontalLineTo(14.381f)
                verticalLineTo(2.1402217f)
                horizontalLineToRelative(1.2278f)
                close()
                moveToRelative(3.7457f, 3.14190993f)
                curveToRelative(-0.0735f, -0.4358f, -0.1351f, -0.8041f, -0.2096f, -1.1038f)
                curveToRelative(-0.0767f, -0.3091f, -0.1789f, -0.5981f, -0.3597f, -0.8668f)
                lineToRelative(-1.2445f, 0.83742f)
                curveToRelative(0.045f, 0.0668f, 0.0934f, 0.1691f, 0.1485f, 0.3908f)
                curveToRelative(0.0574f, 0.2312f, 0.1089f, 0.5335f, 0.1862f, 0.9918f)
                close()
                moveToRelative(-3.7457f, -1.64191f)
                curveToRelative(0.4697f, 0f, 0.781f, 0.0005f, 1.0224f, 0.0185f)
                curveToRelative(0.2322f, 0.0174f, 0.3449f, 0.0481f, 0.4214f, 0.082f)
                lineToRelative(0.6079f, -1.37127993f)
                curveToRelative(-0.2955f, -0.131f, -0.5981f, -0.1827f, -0.9175f, -0.2066f)
                curveToRelative(-0.3102f, -0.0232f, -0.6868f, -0.0227f, -1.1342f, -0.0227f)
                close()
                moveToRelative(3.1764f, -0.32868f)
                curveToRelative(-0.2783f, -0.4137f, -0.6686f, -0.7399f, -1.1247f, -0.9421f)
                lineTo(17.0526f, 2.2407617f)
                curveToRelative(0.1996f, 0.0885f, 0.3685f, 0.2304f, 0.4881f, 0.4082f)
                close()
                moveTo(6.0119f, 15.037322f)
                curveToRelative(0f, 0.8769f, -0.7218f, 1.6029f, -1.6309f, 1.6029f)
                verticalLineToRelative(1.5f)
                curveToRelative(1.7208f, 0f, 3.1309f, -1.3809f, 3.1309f, -3.1029f)
                close()
                moveToRelative(-1.63095f, 1.6029f)
                curveToRelative(-0.9091f, 0f, -1.6309f, -0.726f, -1.6309f, -1.6029f)
                horizontalLineToRelative(-1.5f)
                curveToRelative(0f, 1.722f, 1.4102f, 3.1029f, 3.1309f, 3.1029f)
                close()
                moveTo(2.75f, 15.037322f)
                curveToRelative(0f, -0.877f, 0.7218f, -1.603f, 1.6309f, -1.603f)
                verticalLineToRelative(-1.5f)
                curveToRelative(-1.7208f, 0f, -3.1309f, 1.3809f, -3.1309f, 3.103f)
                close()
                moveToRelative(1.63095f, -1.603f)
                curveToRelative(0.9092f, 0f, 1.6309f, 0.726f, 1.6309f, 1.603f)
                horizontalLineToRelative(1.5f)
                curveToRelative(0f, -1.7221f, -1.4102f, -3.103f, -3.1309f, -3.103f)
                close()
            }
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFF1C274C)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.43337f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(12f, 17.865191f)
                lineToRelative(-1.433371f, 2.388951f)
                horizontalLineToRelative(2.866743f)
                lineTo(12f, 22.643094f)
            }
        }.build()
        return _electricScooter!!
    }
