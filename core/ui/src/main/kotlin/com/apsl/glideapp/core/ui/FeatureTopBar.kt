package com.apsl.glideapp.core.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.apsl.glideapp.core.ui.icons.ArrowBack
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.theme.GlideAppTheme

@Composable
fun FeatureTopBar(text: String, modifier: Modifier = Modifier, onBackClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = text) },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = GlideIcons.ArrowBack,
                    contentDescription = null
                )
            }
        }
    )
}

@Preview
@Composable
private fun FeatureTopBarPreview() {
    GlideAppTheme {
        FeatureTopBar(text = "My rides", onBackClick = {})
    }
}
