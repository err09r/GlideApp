package com.apsl.glideapp.feature.wallet.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlin.math.absoluteValue

@Composable
fun WalletPager(vararg pageContent: @Composable () -> Unit) {
    val startIndex = Int.MAX_VALUE / 2
    val pagerState = rememberPagerState(initialPage = startIndex)

    HorizontalPager(
        count = Int.MAX_VALUE,
        modifier = Modifier.fillMaxSize(),
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 96.dp),
        verticalAlignment = Alignment.Top
    ) { index ->
        val page = (index - startIndex).floorMod(pageContent.size)
        Box(
            modifier = Modifier
                .graphicsLayer {
                    // Calculate the absolute offset for the current page from the
                    // scroll position. We use the absolute value which allows us to mirror
                    // any effects for both directions
                    val pageOffset = calculateCurrentOffsetForPage(index).absoluteValue

                    // We animate the scaleX + scaleY, between 85% and 100%
                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }

                    // We animate the alpha, between 50% and 100%
                    alpha = lerp(
                        start = 0.6f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
        ) {
            pageContent[page]()
        }
    }
}

private fun Int.floorMod(other: Int): Int {
    return when (other) {
        0 -> this
        else -> this - floorDiv(other) * other
    }
}

@Preview(showBackground = true)
@Composable
fun WalletPagerPreview() {
    GlideAppTheme {
        WalletPager(
            {
                BalanceItem(
                    balance = "0.00 PLN",
                    isRentalAvailable = false,
                    onClick = {}
                )
            },
            { TopUpItem(onClick = {}) },
            { VoucherItem(onClick = {}) }
        )
    }
}
