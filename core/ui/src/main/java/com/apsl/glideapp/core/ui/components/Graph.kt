@file:Suppress("UnnecessaryVariable", "LocalVariableName")

package com.apsl.glideapp.core.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.apsl.glideapp.common.util.compress
import com.apsl.glideapp.common.util.maxOfOrDefault
import com.apsl.glideapp.common.util.minOfOrDefault
import com.apsl.glideapp.core.ui.theme.GlideAppTheme

@Immutable
@JvmInline
value class RideRoute(val value: List<Pair<Float, Float>>)

@Composable
fun Graph(
    rideRoute: RideRoute,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    color: Color = MaterialTheme.colorScheme.primary,
    thickness: Dp = 1.dp
) {
    val points = rideRoute.value
    val (minX, maxX) = remember(points) {
        points.minOfOrDefault(0f) { it.first } to points.maxOfOrDefault(0f) { it.first }
    }
    val (minY, maxY) = remember(points) {
        points.minOfOrDefault(0f) { it.second } to points.maxOfOrDefault(0f) { it.second }
    }
    val xDiff = maxX - minX
    val yDiff = maxY - minY

    val path = remember { Path() }
    Canvas(modifier.padding(contentPadding)) {
        path.reset()

        for (i in 0..<points.lastIndex) {
            val (_x, _y) = points[i]
            val (_nextX, _nextY) = points[i + 1]

            val x = size.width / xDiff * (_x - minX)
            val y = size.height / yDiff * (_y - minY)
            val nextX = size.width / xDiff * (_nextX - minX)
            val nextY = size.height / yDiff * (_nextY - minY)

            if (i == 0) {
                path.moveTo(x = x, y = y)
            }

            val firstControlX = (x + nextX) / 2
            val firstControlY = y

            val secondControlX = firstControlX
            val secondControlY = nextY

            path.cubicTo(
                x1 = firstControlX, y1 = firstControlY,
                x2 = secondControlX, y2 = secondControlY,
                x3 = nextX, y3 = nextY
            )

            if (i == 0) {
                drawCircle(
                    color = color,
                    radius = (thickness * 0.8f).toPx(),
                    center = Offset(x = x, y = y)
                )
            }
            if (i == points.lastIndex - 1) {
                drawCircle(
                    color = color,
                    radius = (thickness * 0.8f).toPx(),
                    center = Offset(x = nextX, y = nextY)
                )
            }
        }

        drawPath(
            path = path,
            color = Color.White,
            style = Stroke(width = (thickness + 1.5.dp).toPx(), cap = StrokeCap.Round)
        )
        drawPath(
            path = path,
            color = color,
            style = Stroke(width = thickness.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Preview
@Composable
fun GraphPreview() {
    GlideAppTheme {
        Graph(
            modifier = Modifier.size(120.dp),
            contentPadding = PaddingValues(4.dp),
            thickness = 2.dp,
            rideRoute = RideRoute(
                listOf(
                    1f to 150f,
                    2f to 100f,
                    3f to 150f,
                    4f to 25f,
                    5f to 325f,
                    6f to 275f
                )
            )
        )
    }
}

@Preview
@Composable
fun GraphEmptyPreview() {
    GlideAppTheme {
        Graph(
            modifier = Modifier.size(120.dp),
            contentPadding = PaddingValues(4.dp),
            thickness = 2.dp,
            rideRoute = RideRoute(listOf())
        )
    }
}

@Preview
@Composable
fun GraphLatLngPreview(@PreviewParameter(GraphRoutePreviewParameterProvider::class) route: RideRoute) {
    GlideAppTheme {
        Graph(
            modifier = Modifier.size(120.dp),
            contentPadding = PaddingValues(4.dp),
            thickness = 2.dp,
            rideRoute = route
        )
    }
}

class GraphRoutePreviewParameterProvider : PreviewParameterProvider<RideRoute> {

    override val values: Sequence<RideRoute>
        get() = sequenceOf(
            RideRoute(
                listOf(
                    54.469453333333334f to 17.05219333333333f,
                    54.47316000000001f to 17.004160000000002f,
                    54.47338f to 17.00429f,
                    54.473600000000005f to 17.00447f,
                    54.473789999999994f to 17.004579999999997f,
                    54.47393f to 17.00462f,
                    54.47393999999999f to 17.00491f,
                    54.47390833333334f to 17.00532666666667f,
                    54.473879999999994f to 17.00571f,
                    54.473769999999995f to 17.006098333333334f,
                    54.473769999999995f to 17.0063f,
                    54.473819999999996f to 17.0064f,
                    54.473859999999995f to 17.006970000000003f,
                    54.473821666666666f to 17.007386666666665f,
                    54.473785f to 17.007803333333335f,
                    54.47375f to 17.008210000000002f,
                    54.47371f to 17.00853f,
                    54.473690000000005f to 17.008918333333334f,
                    54.47366f to 17.009220000000003f,
                    54.473600000000005f to 17.00978f,
                    54.47356f to 17.01001f,
                    54.473510000000005f to 17.010460000000002f,
                    54.47348f to 17.010730000000002f,
                    54.47343000000001f to 17.011039999999998f,
                    54.47336f to 17.01153f,
                    54.473310000000005f to 17.01191833333333f,
                    54.473240000000004f to 17.01253f,
                    54.473079999999996f to 17.01285f,
                    54.47264f to 17.01294f,
                    54.47234f to 17.012929999999997f,
                    54.47192f to 17.01283f,
                    54.47145833333334f to 17.012735000000003f,
                    54.470996666666665f to 17.012641666666664f,
                    54.47043999999999f to 17.01253f,
                    54.46998f to 17.012405f,
                    54.46963000000001f to 17.01231f,
                    54.46916666666666f to 17.012251666666668f,
                    54.46878f to 17.01203f,
                    54.46858f to 17.012040000000002f,
                    54.46839f to 17.01205f,
                    54.46803166666667f to 17.011976666666666f,
                    54.46767333333334f to 17.011905000000002f,
                    54.467299999999994f to 17.011828333333334f,
                    54.46696f to 17.011738333333334f,
                    54.46678f to 17.01173f,
                    54.46625000000001f to 17.011599999999998f,
                    54.46585f to 17.01143f,
                    54.46556f to 17.01124f,
                    54.46537f to 17.011210000000002f,
                    54.4648f to 17.01116f,
                    54.464639999999996f to 17.01119f,
                    54.46415999999999f to 17.011300000000002f,
                    54.46384f to 17.01119f,
                    54.463710000000006f to 17.011210000000002f,
                    54.463629999999995f to 17.01141f,
                    54.46349f to 17.0115f,
                    54.4633f to 17.01149f,
                    54.46307f to 17.011509999999998f,
                    54.462909999999994f to 17.01143f,
                    54.462851666666666f to 17.011131666666664f,
                    54.46279500000001f to 17.010833333333334f,
                    54.46275000000001f to 17.010608333333334f,
                    54.46269166666666f to 17.010311666666666f,
                    54.462489999999995f to 17.010119999999997f,
                    54.462399999999995f to 17.010080000000002f,
                    54.46208f to 17.00993f,
                    54.46168666666667f to 17.009741666666667f,
                    54.460989999999995f to 17.00941f,
                    54.460595f to 17.00924f,
                    54.46029f to 17.009108333333334f,
                    54.45989666666666f to 17.008926666666664f,
                    54.45950333333334f to 17.008744999999998f,
                    54.45904f to 17.00853f,
                    54.458645f to 17.008354999999998f,
                    54.45844999999999f to 17.00827f,
                    54.45784999999999f to 17.008010000000002f,
                    54.45745166666667f to 17.007855f,
                    54.457055000000004f to 17.0077f,
                    54.45703f to 17.00769f,
                    54.456269999999996f to 17.00734f,
                    54.45595f to 17.00716f,
                    54.455709999999996f to 17.006858333333334f,
                    54.45547f to 17.00678f,
                    54.455450000000006f to 17.00726f,
                    54.45541f to 17.007888333333334f,
                    54.45530999999999f to 17.00865f,
                    54.45521f to 17.00936f,
                    54.455058333333334f to 17.010481666666667f,
                    54.45496f to 17.01116f,
                    54.45485f to 17.012f,
                    54.45476999999999f to 17.012600000000003f,
                    54.454530000000005f to 17.01326f,
                    54.4546f to 17.01351833333333f,
                    54.454170000000005f to 17.01422f,
                    54.45385666666666f to 17.014813333333333f,
                    54.45365f to 17.01520833333333f,
                    54.45333333333333f to 17.015798333333333f,
                    54.45301833333333f to 17.016388333333335f,
                    54.45273999999999f to 17.016830000000002f,
                    54.45236f to 17.01729f,
                    54.45248f to 17.01759f,
                    54.45287f to 17.01804f,
                    54.453120000000006f to 17.018269999999998f,
                    54.45345999999999f to 17.01882f,
                    54.45379f to 17.01935f,
                    54.45448f to 17.02054f,
                    54.45487f to 17.02118f,
                    54.45512f to 17.021588333333334f,
                    54.45547f to 17.02216f,
                    54.45592f to 17.022859999999998f,
                    54.4564f to 17.02366f,
                    54.4567f to 17.024150000000002f,
                    54.45709f to 17.02481f,
                    54.45727f to 17.025109999999998f,
                    54.45756333333334f to 17.025564999999997f,
                    54.45784999999999f to 17.02601f,
                    54.45814166666667f to 17.026466666666668f,
                    54.45843333333334f to 17.026925000000002f,
                    54.45861f to 17.0272f,
                    54.458895f to 17.027668333333335f,
                    54.459160000000004f to 17.0281f,
                    54.459610000000005f to 17.028830000000003f,
                    54.45996f to 17.0294f,
                    54.460245f to 17.029870000000003f,
                    54.460300000000004f to 17.02995f,
                    54.460586666666664f to 17.030416666666667f,
                    54.46101f to 17.03105f,
                    54.46134f to 17.03132f,
                    54.46181f to 17.03174f,
                    54.461940000000006f to 17.03199f,
                    54.46214f to 17.03233f,
                    54.46245999999999f to 17.03291f,
                    54.46259f to 17.03312833333333f,
                    54.46279f to 17.03347f,
                    54.46316f to 17.03411f,
                    54.46325f to 17.034229999999997f,
                    54.463525f to 17.034553333333335f,
                    54.46387f to 17.03496f,
                    54.464189999999995f to 17.03536f,
                    54.4643f to 17.03553f,
                    54.464600000000004f to 17.036189999999998f,
                    54.4648f to 17.03676f,
                    54.464879999999994f to 17.03706833333333f,
                    54.46499000000001f to 17.03776f,
                    54.46504f to 17.038310000000003f,
                    54.46504f to 17.03883f,
                    54.46504f to 17.039138333333334f,
                    54.46502f to 17.04001f,
                    54.46498f to 17.04046f,
                    54.4648f to 17.04093f,
                    54.464549999999996f to 17.04123f,
                    54.464226666666676f to 17.041361666666667f,
                    54.463903333333334f to 17.041493333333335f,
                    54.46358166666667f to 17.041625f,
                    54.4635f to 17.04166f,
                    54.46304f to 17.04185f,
                    54.46261f to 17.04203f,
                    54.46232f to 17.042199999999998f,
                    54.46211f to 17.0424f,
                    54.46191f to 17.042759999999998f,
                    54.46173999999999f to 17.04334f,
                    54.46165166666666f to 17.043889999999998f,
                    54.46156333333333f to 17.04444f,
                    54.46152f to 17.044719999999998f,
                    54.461439999999996f to 17.045279999999998f,
                    54.461343333333325f to 17.045824999999997f,
                    54.461259999999996f to 17.046310000000002f,
                    54.46116f to 17.046853333333335f,
                    54.461059999999996f to 17.047396666666668f,
                    54.460879999999996f to 17.04838f,
                    54.460699999999996f to 17.04918f,
                    54.46067f to 17.049500000000002f,
                    54.46064f to 17.05014f,
                    54.46053f to 17.05078f,
                    54.4604f to 17.051808333333334f,
                    54.46037f to 17.052368333333334f,
                    54.460339999999995f to 17.053440000000002f,
                    54.460330000000006f to 17.05413f,
                    54.460330000000006f to 17.05483f,
                    54.46032f to 17.05592f,
                    54.46031f to 17.05654f,
                    54.460300000000004f to 17.0577f,
                    54.460249999999995f to 17.05835f,
                    54.46018000000001f to 17.05906f,
                    54.46004f to 17.05987f,
                    54.45982000000001f to 17.06014f,
                    54.45948f to 17.06011f,
                    54.45895f to 17.06009f,
                    54.45862999999999f to 17.060119999999998f,
                    54.458200000000005f to 17.060068333333334f,
                    54.45807f to 17.06013f,
                    54.45767333333333f to 17.060123333333333f,
                    54.45709f to 17.060119999999998f
                )
                    .compress(25)
                    .map { it.second to if (it.first >= 0) 90f - it.first else it.first }
            )
        )
}
