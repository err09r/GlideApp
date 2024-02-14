package com.apsl.glideapp.feature.wallet.wallet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apsl.glideapp.core.ui.icons.CardMoney
import com.apsl.glideapp.core.ui.icons.File
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.icons.Question
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.ui.R as CoreR

@Composable
fun BalanceCard(
    value: String,
    modifier: Modifier = Modifier,
    onAddMoneyClick: () -> Unit
) {
    ElevatedCard(
        onClick = {},
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = stringResource(CoreR.string.balance_card_title),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = stringResource(CoreR.string.value_zloty, value),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Image(
                    painter = painterResource(CoreR.drawable.img_wallet_front),
                    contentDescription = null,
                    modifier = Modifier.size(56.dp)
                )
            }

            Spacer(Modifier.height(32.dp))
            Divider()
            Spacer(Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onAddMoneyClick) {
                    Icon(
                        imageVector = GlideIcons.CardMoney,
                        contentDescription = null
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(text = stringResource(CoreR.string.balance_card_button))
                }
                FilledTonalIconButton(
                    onClick = {},
                    colors = IconButtonDefaults.filledTonalIconButtonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Icon(
                        imageVector = GlideIcons.File,
                        contentDescription = null
                    )
                }
                FilledTonalIconButton(
                    onClick = {},
                    colors = IconButtonDefaults.filledTonalIconButtonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Icon(
                        imageVector = GlideIcons.Question,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun BalanceCardPreview() {
    GlideAppTheme {
        BalanceCard(value = "1234,56", onAddMoneyClick = {})
    }
}
