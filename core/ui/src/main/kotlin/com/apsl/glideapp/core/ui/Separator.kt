package com.apsl.glideapp.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.apsl.glideapp.core.ui.theme.GlideAppTheme

@Composable
fun Separator(
    text: String,
    modifier: Modifier = Modifier,
    showBackground: Boolean = true
) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .run {
                if (showBackground) {
                    this.background(MaterialTheme.colorScheme.background)
                } else {
                    this
                }
            }
            .then(modifier),
        style = MaterialTheme.typography.titleMedium,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}

@Preview
@Composable
private fun SeparatorPreview() {
    GlideAppTheme {
        Separator(text = "Monday, February 23")
    }
}

@Preview
@Composable
private fun SeparatorNoBackgroundPreview() {
    GlideAppTheme {
        Separator(text = "Monday, February 23", showBackground = false)
    }
}
