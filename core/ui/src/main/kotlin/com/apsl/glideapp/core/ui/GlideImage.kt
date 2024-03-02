package com.apsl.glideapp.core.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.apsl.glideapp.core.ui.theme.GlideAppTheme

@Composable
fun GlideImage(
    @DrawableRes imageResId: Int,
    modifier: Modifier = Modifier,
    size: DpSize = DpSize(120.dp, 120.dp),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    contentScale: ContentScale = ContentScale.Fit,
    contentDescription: String? = null
) {
    Box(modifier = modifier.padding(contentPadding)) {
        Image(
            painter = painterResource(imageResId),
            contentDescription = contentDescription,
            modifier = Modifier.size(size),
            contentScale = contentScale
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GlideImagePreview() {
    GlideAppTheme {
        GlideImage(R.drawable.img_bell)
    }
}
