package com.apsl.glideapp.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.apsl.glideapp.core.ui.components.FeatureTopBar
import com.apsl.glideapp.core.ui.theme.GlideAppTheme

@Composable
fun FeatureScreen(
    topBarText: String,
    onBackClick: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            FeatureTopBar(
                modifier = Modifier.statusBarsPadding(),
                text = topBarText,
                onBackClick = onBackClick
            )
        },
        contentWindowInsets = WindowInsets.navigationBars
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

@Preview(showBackground = true)
@Composable
fun FeatureScreenPreview() {
    GlideAppTheme {
        FeatureScreen(topBarText = "My Wallet", onBackClick = {}) {
        }
    }
}
