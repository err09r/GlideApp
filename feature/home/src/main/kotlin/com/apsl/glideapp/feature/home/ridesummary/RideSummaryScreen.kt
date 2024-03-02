package com.apsl.glideapp.feature.home.ridesummary

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.apsl.glideapp.core.ui.DismissibleScreen
import com.apsl.glideapp.core.ui.GlideImage
import com.apsl.glideapp.core.ui.annotatedStringResource
import com.apsl.glideapp.core.ui.icons.Comment
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.icons.Share
import com.apsl.glideapp.core.ui.none
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.util.android.DistanceFormatter
import com.apsl.glideapp.core.ui.R as CoreR

@Composable
fun RideSummaryScreen(distance: Float, averageSpeed: Float, onNavigateBack: () -> Unit) {
    DismissibleScreen(onDismissClick = onNavigateBack, contentWindowInsets = WindowInsets.none) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .navigationBarsPadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(CoreR.string.ride_summary_headline),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                Spacer(Modifier.height(40.dp))

                HighlightItem(
                    imageResId = CoreR.drawable.img_location,
                    title = stringResource(CoreR.string.ride_summary_highlight_title1),
                    text = annotatedStringResource(
                        id = CoreR.string.ride_summary_highlight_text1,
                        DistanceFormatter.format(averageSpeed),
                        DistanceFormatter.format(distance)
                    ),
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(Modifier.height(48.dp))

                HighlightItem(
                    imageResId = CoreR.drawable.img_battery,
                    title = stringResource(CoreR.string.ride_summary_highlight_title2),
                    text = annotatedStringResource(CoreR.string.ride_summary_highlight_text2),
                    modifier = Modifier.align(Alignment.End),
                    rightToLeft = true
                )

                Spacer(Modifier.height(48.dp))

                HighlightItem(
                    imageResId = CoreR.drawable.img_clock,
                    title = stringResource(CoreR.string.ride_summary_highlight_title3),
                    text = annotatedStringResource(CoreR.string.ride_summary_highlight_text3),
                    modifier = Modifier.align(Alignment.End)
                )

                Spacer(Modifier.height(32.dp))
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                var textFieldValue by rememberSaveable { mutableStateOf("") }
                TextField(
                    value = textFieldValue,
                    onValueChange = { textFieldValue = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = stringResource(CoreR.string.ride_summary_textfield_hint)) },
                    leadingIcon = {
                        Icon(imageVector = GlideIcons.Comment, contentDescription = null)
                    },
                    singleLine = true
                )

                Spacer(Modifier.height(32.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    FilledTonalIconButton(onClick = {}) {
                        Icon(imageVector = GlideIcons.Share, contentDescription = null)
                    }
                    Spacer(Modifier.width(16.dp))
                    Button(onClick = onNavigateBack, modifier = Modifier.weight(1f)) {
                        Text(text = stringResource(CoreR.string.ride_summary_button))
                    }
                }
            }
        }
    }
}

@Composable
private fun HighlightItem(
    @DrawableRes imageResId: Int,
    title: String,
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    rightToLeft: Boolean = false
) {
    Row(modifier = modifier) {
        if (rightToLeft) {
            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                Text(
                    text = title,
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = text,
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(Modifier.width(20.dp))

            Card(onClick = {}, shape = CircleShape) {
                GlideImage(
                    imageResId = imageResId,
                    size = DpSize(72.dp, 72.dp),
                    contentPadding = PaddingValues(12.dp)
                )
            }
        } else {
            Card(onClick = {}, shape = CircleShape) {
                GlideImage(
                    imageResId = imageResId,
                    size = DpSize(72.dp, 72.dp),
                    contentPadding = PaddingValues(12.dp)
                )
            }

            Spacer(Modifier.width(20.dp))

            Column {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(4.dp))
                Text(text = text, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Preview
@Composable
private fun RideSummaryScreenPreview() {
    GlideAppTheme {
        RideSummaryScreen(averageSpeed = 17.5f, distance = 4.3f, onNavigateBack = {})
    }
}
