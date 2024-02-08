@file:Suppress("UnusedReceiverParameter")

package com.apsl.glideapp.feature.home.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apsl.glideapp.core.ui.icons.ArrowBack
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.ui.R as CoreR

@Composable
fun RideInformationScreen(onNavigateBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(16.dp))
        IconButton(onClick = onNavigateBack, modifier = Modifier.align(Alignment.End)) {
            // TODO: Replace with 'X' cross icon
            Icon(imageVector = GlideIcons.ArrowBack, contentDescription = null)
        }
        Spacer(Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            ZoneThumbnail()
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Abcabcababbcabacabacasavasfasfasfasfasfc")
                Text(text = "Abcabcababbcabacabacasavasfasfasfasfasfc")
                Text(text = "Abcabcababbcabacabacasavasfasfasfasfasfc")
            }
        }
    }
}

@Composable
private fun ZoneThumbnail(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .aspectRatio(0.8f)
            .border(width = 2.dp, color = Color.Blue, shape = MaterialTheme.shapes.medium)
            .clip(MaterialTheme.shapes.medium)
    ) {
        Image(
            painter = painterResource(CoreR.drawable.img_map_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Spacer(
            Modifier
                .background(Color.Blue.copy(alpha = 0.2f))
                .fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RideInformationScreenPreview() {
    GlideAppTheme {
        RideInformationScreen(onNavigateBack = {})
    }
}
