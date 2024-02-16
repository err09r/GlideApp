package com.apsl.glideapp.feature.wallet.wallet

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.apsl.glideapp.core.ui.GlideImage
import com.apsl.glideapp.core.ui.icons.AltArrow
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import kotlin.math.abs
import com.apsl.glideapp.core.ui.R as CoreR

@Immutable
data class WalletPagerItem(
    val id: Int,
    @StringRes val titleResId: Int,
    @StringRes val textResId: Int,
    @DrawableRes val imageResId: Int,
    val onClick: () -> Unit
)

@Immutable
@JvmInline
value class WalletPagerItems(val value: List<WalletPagerItem>)

fun List<WalletPagerItem>.toWalletPagerItems(): WalletPagerItems {
    return WalletPagerItems(value = this)
}

@Composable
fun WalletPager(
    items: WalletPagerItems,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val startIndex = Int.MAX_VALUE / 2
    val pagerState = rememberPagerState(
        initialPage = startIndex,
        pageCount = { Int.MAX_VALUE }
    )

    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        contentPadding = contentPadding,
        verticalAlignment = Alignment.Top,
        beyondBoundsPageCount = 3,
        pageSpacing = 8.dp
    ) { index ->
        val page = (index - startIndex).floorMod(items.value.size)
        val item = items.value[page]
        Column {
            ElevatedCard(
                onClick = item.onClick,
                modifier = Modifier.graphicsLayer {
                    /*
                     Deprecated way ('calculateCurrentOffsetForPage' in accompanist):
                     (currentPage - page) + currentPageOffset
                     */
                    val pageOffset =
                        abs((pagerState.currentPage - index) + pagerState.currentPageOffsetFraction)

                    lerp(
                        start = 0.9f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
//                        scaleX = scale
                        scaleY = scale
                    }

                    alpha = lerp(
                        start = 0.9f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GlideImage(imageResId = item.imageResId, size = DpSize(64.dp, 64.dp))
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(text = stringResource(item.titleResId))
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = stringResource(item.textResId),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    IconButton(onClick = item.onClick) {
                        Icon(
                            imageVector = GlideIcons.AltArrow,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
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
private fun WalletPagerPreview() {
    GlideAppTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            WalletPager(
                items = listOf(
                    WalletPagerItem(
                        id = 1,
                        titleResId = CoreR.string.wallet_pager_item_title1,
                        textResId = CoreR.string.wallet_pager_item_text1,
                        imageResId = CoreR.drawable.img_gift_front,
                        onClick = {}
                    ),
                    WalletPagerItem(
                        id = 2,
                        titleResId = CoreR.string.wallet_pager_item_title2,
                        textResId = CoreR.string.wallet_pager_item_text2,
                        imageResId = CoreR.drawable.img_card_front,
                        onClick = {}
                    )
                ).toWalletPagerItems(),
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 32.dp)
            )
        }
    }
}
