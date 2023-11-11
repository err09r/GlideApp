package com.apsl.glideapp.core.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
//    Surface(modifier = modifier.fillMaxWidth()) {
//        Box(
//            modifier = Modifier.padding(16.dp)
//        ) {
//            IconButton(onClick = onBackClick) {
//                Icon(
//                    imageVector = GlideIcons.ArrowBack,
//                    contentDescription = null,
//                    tint = MaterialTheme.colorScheme.primary
//                )
//            }
//            Spacer(Modifier.width(20.dp))
//            Text(text = text, modifier = Modifier.align(Alignment.Center), fontSize = 20.sp)
//        }
//    }
}

@Preview
@Composable
fun FeatureTopBarPreview() {
    GlideAppTheme {
        FeatureTopBar(text = "My Rides", onBackClick = {})
    }
}
