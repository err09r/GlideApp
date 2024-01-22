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

val GlideIcons.Battery: ImageVector by lazy {
    ImageVector.Builder(
        name = "Battery",
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
            moveTo(12.0762f, 9.48014f)
            curveTo(12.3413f, 9.1619f, 12.2984f, 8.689f, 11.9801f, 8.4238f)
            curveTo(11.6619f, 8.1587f, 11.189f, 8.2017f, 10.9238f, 8.5199f)
            lineTo(8.42384f, 11.5199f)
            curveTo(8.2375f, 11.7434f, 8.1974f, 12.0546f, 8.3208f, 12.3181f)
            curveTo(8.4443f, 12.5817f, 8.709f, 12.75f, 9f, 12.75f)
            horizontalLineTo(10.8987f)
            lineTo(9.42384f, 14.5199f)
            curveTo(9.1587f, 14.8381f, 9.2017f, 15.311f, 9.5199f, 15.5762f)
            curveTo(9.8381f, 15.8413f, 10.311f, 15.7983f, 10.5762f, 15.4801f)
            lineTo(13.0762f, 12.4801f)
            curveTo(13.2625f, 12.2566f, 13.3026f, 11.9454f, 13.1792f, 11.6819f)
            curveTo(13.0558f, 11.4183f, 12.791f, 11.25f, 12.5f, 11.25f)
            horizontalLineTo(10.6013f)
            lineTo(12.0762f, 9.48014f)
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
            moveTo(9.94358f, 3.25f)
            horizontalLineTo(11.5564f)
            curveTo(13.3942f, 3.25f, 14.8498f, 3.25f, 15.989f, 3.4031f)
            curveTo(17.1614f, 3.5608f, 18.1104f, 3.8929f, 18.8588f, 4.6412f)
            curveTo(19.6071f, 5.3896f, 19.9392f, 6.3386f, 20.0969f, 7.511f)
            curveTo(20.1659f, 8.0244f, 20.2038f, 8.6021f, 20.2246f, 9.2501f)
            curveTo(20.5874f, 9.2508f, 20.9197f, 9.256f, 21.1972f, 9.2933f)
            curveTo(21.5527f, 9.3411f, 21.9284f, 9.4535f, 22.2374f, 9.7626f)
            curveTo(22.5465f, 10.0716f, 22.6589f, 10.4473f, 22.7067f, 10.8028f)
            curveTo(22.7501f, 11.1256f, 22.7501f, 11.5224f, 22.75f, 11.9552f)
            verticalLineTo(12.0447f)
            curveTo(22.7501f, 12.4776f, 22.7501f, 12.8744f, 22.7067f, 13.1972f)
            curveTo(22.6589f, 13.5527f, 22.5465f, 13.9284f, 22.2374f, 14.2374f)
            curveTo(21.9284f, 14.5465f, 21.5527f, 14.6589f, 21.1972f, 14.7067f)
            curveTo(20.9197f, 14.744f, 20.5874f, 14.7492f, 20.2246f, 14.7499f)
            curveTo(20.2038f, 15.3979f, 20.1659f, 15.9756f, 20.0969f, 16.489f)
            curveTo(19.9392f, 17.6614f, 19.6071f, 18.6104f, 18.8588f, 19.3588f)
            curveTo(18.1104f, 20.1071f, 17.1614f, 20.4392f, 15.989f, 20.5969f)
            curveTo(14.8498f, 20.75f, 13.3942f, 20.75f, 11.5565f, 20.75f)
            horizontalLineTo(9.94359f)
            curveTo(8.1059f, 20.75f, 6.6502f, 20.75f, 5.511f, 20.5969f)
            curveTo(4.3386f, 20.4392f, 3.3896f, 20.1071f, 2.6412f, 19.3588f)
            curveTo(1.8929f, 18.6104f, 1.5608f, 17.6614f, 1.4031f, 16.489f)
            curveTo(1.25f, 15.3498f, 1.25f, 13.8942f, 1.25f, 12.0564f)
            verticalLineTo(11.9436f)
            curveTo(1.25f, 10.1058f, 1.25f, 8.6502f, 1.4031f, 7.511f)
            curveTo(1.5608f, 6.3386f, 1.8929f, 5.3896f, 2.6412f, 4.6412f)
            curveTo(3.3896f, 3.8929f, 4.3386f, 3.5608f, 5.511f, 3.4031f)
            curveTo(6.6502f, 3.25f, 8.1058f, 3.25f, 9.9436f, 3.25f)
            close()
            moveTo(5.71085f, 4.88976f)
            curveTo(4.7048f, 5.025f, 4.1251f, 5.2787f, 3.7019f, 5.7019f)
            curveTo(3.2787f, 6.1251f, 3.025f, 6.7048f, 2.8898f, 7.7108f)
            curveTo(2.7516f, 8.7385f, 2.75f, 10.0932f, 2.75f, 12f)
            curveTo(2.75f, 13.9068f, 2.7516f, 15.2615f, 2.8898f, 16.2892f)
            curveTo(3.025f, 17.2952f, 3.2787f, 17.8749f, 3.7019f, 18.2981f)
            curveTo(4.1251f, 18.7213f, 4.7048f, 18.975f, 5.7108f, 19.1102f)
            curveTo(6.7385f, 19.2484f, 8.0932f, 19.25f, 10f, 19.25f)
            horizontalLineTo(11.5f)
            curveTo(13.4068f, 19.25f, 14.7615f, 19.2484f, 15.7892f, 19.1102f)
            curveTo(16.7952f, 18.975f, 17.3749f, 18.7213f, 17.7981f, 18.2981f)
            curveTo(18.2213f, 17.8749f, 18.475f, 17.2952f, 18.6102f, 16.2892f)
            curveTo(18.7484f, 15.2615f, 18.75f, 13.9068f, 18.75f, 12f)
            curveTo(18.75f, 10.0932f, 18.7484f, 8.7385f, 18.6102f, 7.7108f)
            curveTo(18.475f, 6.7048f, 18.2213f, 6.1251f, 17.7981f, 5.7019f)
            curveTo(17.3749f, 5.2787f, 16.7952f, 5.025f, 15.7892f, 4.8898f)
            curveTo(14.7615f, 4.7516f, 13.4068f, 4.75f, 11.5f, 4.75f)
            horizontalLineTo(10f)
            curveTo(8.0932f, 4.75f, 6.7385f, 4.7516f, 5.7108f, 4.8898f)
            close()
            moveTo(20.75f, 10.7594f)
            verticalLineTo(13.2406f)
            curveTo(20.845f, 13.2362f, 20.9259f, 13.2297f, 20.9973f, 13.2201f)
            curveTo(21.0939f, 13.2071f, 21.1423f, 13.1918f, 21.164f, 13.1828f)
            curveTo(21.1691f, 13.1808f, 21.1724f, 13.1791f, 21.1743f, 13.1781f)
            lineTo(21.1768f, 13.1768f)
            lineTo(21.1781f, 13.1743f)
            curveTo(21.1791f, 13.1724f, 21.1808f, 13.1691f, 21.1828f, 13.164f)
            curveTo(21.1918f, 13.1423f, 21.2071f, 13.0939f, 21.2201f, 12.9973f)
            curveTo(21.2484f, 12.7866f, 21.25f, 12.4926f, 21.25f, 12f)
            curveTo(21.25f, 11.5074f, 21.2484f, 11.2134f, 21.2201f, 11.0027f)
            curveTo(21.2071f, 10.9061f, 21.1918f, 10.8577f, 21.1828f, 10.836f)
            curveTo(21.1808f, 10.8309f, 21.1791f, 10.8276f, 21.1781f, 10.8257f)
            lineTo(21.1768f, 10.8232f)
            lineTo(21.1743f, 10.8219f)
            curveTo(21.1724f, 10.8209f, 21.1691f, 10.8192f, 21.164f, 10.8172f)
            curveTo(21.1423f, 10.8082f, 21.0939f, 10.7929f, 20.9973f, 10.7799f)
            curveTo(20.9259f, 10.7703f, 20.845f, 10.7638f, 20.75f, 10.7594f)
            close()
        }
    }.build()
}
