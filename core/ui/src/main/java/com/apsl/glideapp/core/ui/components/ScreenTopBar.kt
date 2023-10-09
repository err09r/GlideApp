package com.apsl.glideapp.core.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apsl.glideapp.core.ui.theme.GlideAppTheme

@Composable
fun ScreenTopBar(text: String, modifier: Modifier = Modifier, onBackClick: () -> Unit) {
    Surface(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .statusBarsPadding(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
            }
            Spacer(Modifier.width(20.dp))
            Text(text = text, fontSize = 20.sp)
        }
    }
}

@Preview
@Composable
fun ScreenTopBarPreview() {
    GlideAppTheme {
        ScreenTopBar(text = "My Rides", onBackClick = {})
    }
}
