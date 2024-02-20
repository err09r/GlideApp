@file:Suppress("UnusedReceiverParameter")

package com.apsl.glideapp.feature.home.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.apsl.glideapp.core.ui.DismissibleScreen
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.icons.Warning
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.ui.theme.LocalExtendedColorScheme
import com.apsl.glideapp.feature.home.map.NoParkingMarker
import kotlinx.coroutines.launch
import com.apsl.glideapp.core.ui.R as CoreR

@Composable
fun PreRideInfoScreen(
    onNavigateBack: () -> Unit,
    viewModel: PreRideInfoViewModel = hiltViewModel()
) {
    PreRideInfoScreenContent(
        onAccept = {
            viewModel.accept()
            onNavigateBack()
        },
        onReject = {
            viewModel.reject()
            onNavigateBack()
        }
    )
}

@Composable
fun PreRideInfoScreenContent(onAccept: () -> Unit, onReject: () -> Unit) {
    DismissibleScreen(onDismissClick = onReject) {
        val scrollState = rememberScrollState()
        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState)
        ) {
            Spacer(Modifier.height(16.dp))
            Text(
                text = stringResource(CoreR.string.preride_info_title1),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(16.dp))
            RuleList()
            Spacer(Modifier.height(32.dp))
            Text(
                text = stringResource(CoreR.string.preride_info_title2),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(16.dp))
            ZoneList()
            Spacer(Modifier.height(88.dp))
        }

        Button(
            onClick = {
                if (!scrollState.canScrollForward) {
                    onAccept()
                } else {
                    scope.launch {
                        scrollState.animateScrollTo(scrollState.maxValue)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter)
        ) {
            Text(text = stringResource(CoreR.string.preride_info_button))
        }
    }
}

@Composable
fun ColumnScope.RuleList() {
    RuleItem(text = stringResource(CoreR.string.preride_info_rule1))
    Spacer(Modifier.height(16.dp))
    RuleItem(text = stringResource(CoreR.string.preride_info_rule2))
    Spacer(Modifier.height(16.dp))
    RuleItem(text = stringResource(CoreR.string.preride_info_rule3))
}

@Composable
private fun RuleItem(text: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Icon(
            imageVector = GlideIcons.Warning,
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .offset(y = 2.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.width(16.dp))
        Text(text = text)
    }
}

@Composable
private fun ColumnScope.ZoneList() {
    val extendedColorScheme = LocalExtendedColorScheme.current
    ZoneItem(
        title = stringResource(CoreR.string.preride_info_zone_title1),
        text = stringResource(CoreR.string.preride_info_zone_text1),
        color = extendedColorScheme.noParkingZone
    ) {
        NoParkingMarker()
    }

    Spacer(Modifier.height(24.dp))

    ZoneItem(
        title = stringResource(CoreR.string.preride_info_zone_title2),
        text = stringResource(CoreR.string.preride_info_zone_text2),
        color = extendedColorScheme.noRidingZone
    )

    Spacer(Modifier.height(24.dp))

    ZoneItem(
        title = stringResource(CoreR.string.preride_info_zone_title3),
        text = stringResource(CoreR.string.preride_info_zone_text3),
        color = extendedColorScheme.lowSpeedZone
    )
}

@Composable
private fun ZoneItem(
    title: String,
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    content: (@Composable () -> Unit)? = null
) {
    Row(modifier = modifier) {
        Card(border = BorderStroke(width = 2.dp, color = color)) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(CoreR.drawable.img_map_bg),
                    contentDescription = null,
                    modifier = Modifier.size(72.dp),
                    contentScale = ContentScale.FillWidth,
                    colorFilter = ColorFilter.tint(
                        color = color.copy(alpha = 0.2f),
                        blendMode = BlendMode.Multiply
                    )
                )
                if (content != null) {
                    content()
                }
            }
        }
        Spacer(Modifier.width(16.dp))
        Column {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(text = text, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreRideInfoScreenPreview() {
    GlideAppTheme {
        PreRideInfoScreenContent(onAccept = {}, onReject = {})
    }
}
