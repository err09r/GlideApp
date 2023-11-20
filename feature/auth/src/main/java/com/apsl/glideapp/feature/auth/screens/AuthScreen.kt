package com.apsl.glideapp.feature.auth.screens

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.apsl.glideapp.core.ui.imeCollapsible
import com.apsl.glideapp.core.ui.onContentClickResettable

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    scrollState: ScrollState = rememberScrollState(),
    content: @Composable ColumnScope.() -> Unit
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .onContentClickResettable(),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.navigationBarsPadding()
            )
        },
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentWindowInsets = WindowInsets.statusBars
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .onContentClickResettable()
                .padding(padding)
        ) {
            Spacer(Modifier.imeCollapsible(initialHeight = 72.dp))
            Surface(
                shape = BottomSheetDefaults.ExpandedShape,
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding()
                        .verticalScroll(scrollState)
                        .imePadding()
                        .padding(16.dp),
                    content = content
                )
            }
        }
    }
}
