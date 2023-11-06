package com.apsl.glideapp.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apsl.glideapp.core.ui.icons.ArrowBack
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.theme.GlideAppTheme

@Composable
fun FeatureTopBar(text: String, modifier: Modifier = Modifier, onBackClick: () -> Unit) {
    Surface(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = GlideIcons.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(Modifier.width(20.dp))
            Text(text = text, modifier = Modifier.align(Alignment.Center), fontSize = 20.sp)
        }
    }
}

@Preview
@Composable
fun FeatureTopBarPreview() {
    GlideAppTheme {
        FeatureTopBar(text = "My Rides", onBackClick = {})
    }
}
