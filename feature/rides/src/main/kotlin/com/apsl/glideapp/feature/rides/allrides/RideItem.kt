package com.apsl.glideapp.feature.rides.allrides

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apsl.glideapp.core.ui.GlideImage
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.icons.MapPoint
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.util.android.CurrencyFormatter
import com.apsl.glideapp.core.ui.R as CoreR

@Composable
fun RideItem(
    modifier: Modifier = Modifier,
    overlineText: String,
    headlineText: String,
    supportingText: String,
    trailingText: String,
    route: RideRoute,
    onClick: () -> Unit
) {
    val mapBackgroundDegree: Float = remember { arrayOf(0f, 90f, 180f, 270f).random() }
    val mapBackgroundScaleX: Float = remember { arrayOf(-1f, 1f).random() }
    Surface(
        shape = RectangleShape,
        modifier = modifier,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.clip(shape = CardDefaults.shape)) {
                GlideImage(
                    imageResId = CoreR.drawable.img_map_bg,
                    modifier = Modifier
                        .rotate(mapBackgroundDegree)
                        .scale(scaleX = mapBackgroundScaleX, scaleY = 1f),
                    size = DpSize(80.dp, 80.dp),
                    contentScale = ContentScale.Crop
                )
                RideGraph(
                    modifier = Modifier.size(80.dp),
                    contentPadding = PaddingValues(12.dp),
                    thickness = 2.dp,
                    rideRoute = route
                )
            }
            Spacer(Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = GlideIcons.MapPoint,
                        contentDescription = null,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = overlineText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    text = headlineText,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp
                    )
                )
                Spacer(Modifier.weight(1f))
                Text(text = supportingText, style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(Modifier.width(16.dp))
            Text(
                text = trailingText,
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
private fun RideItemPreview(@PreviewParameter(RideRoutePreviewParameterProvider::class) route: RideRoute) {
    GlideAppTheme {
        RideItem(
            modifier = Modifier.fillMaxWidth(),
            overlineText = "Spacerowa 1A, Słupsk",
            headlineText = "13:48 - 14:15",
            supportingText = "1415 meters",
            trailingText = CurrencyFormatter.format(13.50),
            route = route,
            onClick = {}
        )
    }
}
