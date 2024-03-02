package com.apsl.glideapp.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.apsl.glideapp.core.ui.icons.ArrowBack
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.theme.GlideAppTheme

@Composable
fun FeatureScreen(
    topBarText: String,
    onBackClick: () -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    contentWindowInsets: WindowInsets = WindowInsets.navigationBars,
    content: @Composable BoxScope.() -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .onContentClickResettable(),
        topBar = {
            FeatureTopBar(text = topBarText, onBackClick = onBackClick)
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        contentWindowInsets = contentWindowInsets
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            content()
        }
    }
}

@Composable
fun FeatureTopBar(text: String, onBackClick: () -> Unit, modifier: Modifier = Modifier) {
    TopAppBar(
        title = { Text(text = text) },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = GlideIcons.ArrowBack, contentDescription = null)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun FeatureScreenPreview() {
    GlideAppTheme {
        FeatureScreen(topBarText = "My wallet", onBackClick = {}) {
        }
    }
}

@Preview
@Composable
private fun FeatureTopBarPreview() {
    GlideAppTheme {
        FeatureTopBar(text = "My rides", onBackClick = {})
    }
}
