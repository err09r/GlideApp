package com.apsl.glideapp.core.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.apsl.glideapp.core.ui.R
import com.apsl.glideapp.core.ui.theme.GlideAppTheme

@Composable
fun GlideImage(
    @DrawableRes imageResId: Int,
    modifier: Modifier = Modifier,
    size: DpSize = DpSize(120.dp, 120.dp)
) {
    Image(
        painter = painterResource(imageResId),
        contentDescription = null,
        modifier = modifier.size(size)
    )
}

@Preview(showBackground = true)
@Composable
fun GlideImagePreview() {
    GlideAppTheme {
        GlideImage(R.drawable.img_bell)
    }
}
