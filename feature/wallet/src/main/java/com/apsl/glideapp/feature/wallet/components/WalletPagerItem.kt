package com.apsl.glideapp.feature.wallet.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WalletPagerItem(
    text: String,
    image: ImageVector,
    onClick: () -> Unit,
    content: @Composable () -> Unit = {}
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            shape = CircleShape,
            color = Color.White,
            elevation = 16.dp,
            onClick = onClick
        ) {
            Icon(
                imageVector = image,
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .size(64.dp),
                tint = Color.DarkGray
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = text,
            modifier = Modifier.widthIn(max = 128.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
        content()
    }
}
