@file:Suppress("UnusedReceiverParameter")

package com.apsl.glideapp.feature.home.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apsl.glideapp.core.ui.theme.GlideAppTheme

@Composable
fun RideInformationScreen(onNavigateBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        InfoSection(title = "First") {

        }
        InfoSection(title = "Second") {

        }
    }
}

@Composable
fun ColumnScope.InfoSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Text(text = title, style = MaterialTheme.typography.titleLarge)
    Spacer(Modifier.height(8.dp))
    content()
}

@Composable
fun ZoneInfoItem(modifier: Modifier = Modifier) {
    Row(modifier = Modifier.fillMaxWidth()) {
//        Image(painterResoure = painterResource(com.apsl.glideapp.core.ui.R.drawable.img_phone), contentDescription = )
    }
}

@Preview(showBackground = true)
@Composable
private fun RideInformationScreenPreview() {
    GlideAppTheme {
        RideInformationScreen(onNavigateBack = {})
    }
}
