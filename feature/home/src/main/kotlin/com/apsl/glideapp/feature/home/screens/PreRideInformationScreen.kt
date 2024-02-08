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
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.apsl.glideapp.core.ui.icons.ArrowBack
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.feature.home.map.NoParkingMarker
import com.apsl.glideapp.core.ui.R as CoreR

@Composable
fun PreRideInformationScreen(onNavigateBack: () -> Unit, text: String) {
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

        Text(text)
        Text("2. Lorem ipsum")
        Text("3. Lorem ipsum")

        Spacer(Modifier.height(16.dp))
        Divider()
        Spacer(Modifier.height(16.dp))

        Spacer(Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            ZoneThumbnail(modifier = Modifier.weight(0.2f))
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(0.8f)) {
                Text(text = "Mandatory parking spot", style = MaterialTheme.typography.titleLarge)
                Text(text = "You must end your ride at one of these \"P\" spots within this zone.")
            }
        }
        Spacer(Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            ZoneThumbnail(modifier = Modifier.weight(0.2f))
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(0.8f)) {
                Text(text = "Mandatory parking spot", style = MaterialTheme.typography.titleLarge)
                Text(text = "You must end your ride at one of these \"P\" spots within this zone.")
            }
        }
        Spacer(Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            ZoneThumbnail(modifier = Modifier.weight(0.2f))
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(0.8f)) {
                Text(text = "Mandatory parking spot", style = MaterialTheme.typography.titleLarge)
                Text(text = "You must end your ride at one of these \"P\" spots within this zone.")
            }
        }
        Spacer(Modifier.weight(1f))
        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text(text = "I agree")
        }
        Spacer(Modifier.height(32.dp))
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
        NoParkingMarker(modifier = Modifier.align(Alignment.Center))
    }
}

@Preview(showBackground = true)
@Composable
private fun PreRideInformationScreenPreview(@PreviewParameter(LoremIpsum::class) text: String) {
    GlideAppTheme {
        PreRideInformationScreen(onNavigateBack = {}, text = text)
    }
}
