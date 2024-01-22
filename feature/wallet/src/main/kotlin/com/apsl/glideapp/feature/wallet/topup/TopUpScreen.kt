package com.apsl.glideapp.feature.wallet.topup

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apsl.glideapp.core.model.PaymentMethod
import com.apsl.glideapp.core.ui.FeatureScreen
import com.apsl.glideapp.core.ui.GlideImage
import com.apsl.glideapp.core.ui.ScreenActions
import com.apsl.glideapp.core.ui.clickable
import com.apsl.glideapp.core.ui.icons.CardMoney
import com.apsl.glideapp.core.ui.icons.GlideIcons
import com.apsl.glideapp.core.ui.icons.Wallet
import com.apsl.glideapp.core.ui.imeCollapsible
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.ui.R as CoreR

@Composable
fun TopUpScreen(
    viewModel: TopUpViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToPayment: () -> Unit,
    onNavigateToTopUpSuccess: () -> Unit
) {
    ScreenActions(viewModel.actions) { action ->
        when (action) {
            is TopUpAction.PaymentProcessingStarted -> onNavigateToPayment()
            is TopUpAction.PaymentProcessingCompleted -> onNavigateToTopUpSuccess()
            is TopUpAction.PaymentProcessingFailed -> onNavigateBack()
        }
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TopUpScreenContent(
        uiState = uiState,
        onBackClick = onNavigateBack,
        onAmountValueChange = viewModel::updateAmountTextFieldValue,
        onPaymentMethodSelected = viewModel::setSelectedPaymentMethodIndex,
        onTopUpClick = viewModel::startPaymentProcessing
    )
}

@Composable
fun TopUpScreenContent(
    uiState: TopUpUiState,
    onBackClick: () -> Unit,
    onAmountValueChange: (String?) -> Unit,
    onPaymentMethodSelected: (Int) -> Unit,
    onTopUpClick: () -> Unit
) {
    FeatureScreen(
        topBarText = "Account top up",
        onBackClick = onBackClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(WindowInsets.systemBars)
                .padding(16.dp)
                .imePadding()
        ) {
            Spacer(Modifier.imeCollapsible(initialHeight = 16.dp))
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(Modifier.height(16.dp))

                    GlideImage(
                        imageResId = CoreR.drawable.img_card,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(Modifier.height(16.dp))

                    TitleWithIcon(text = "Top up your account", image = GlideIcons.CardMoney)

                    Spacer(Modifier.height(16.dp))

                    Text(text = "Enter the amount you want to top up your account")

                    Spacer(Modifier.height(16.dp))

                    OutlinedTextField(
                        value = uiState.amountTextFieldValue ?: "0,0",
                        onValueChange = onAmountValueChange,
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { focusState ->
                                when {
                                    focusState.isFocused && (uiState.amountTextFieldValue == "0,0" || uiState.amountTextFieldValue == null) -> {
                                        onAmountValueChange("")
                                    }

                                    !focusState.isFocused && uiState.amountTextFieldValue.isNullOrBlank() -> {
                                        onAmountValueChange(null)
                                    }
                                }
                            },
                        label = { Text(text = "Amount") },
                        trailingIcon = {
                            Text(text = "zł", modifier = Modifier.padding(horizontal = 16.dp))
                        },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true
                    )

                    Spacer(Modifier.height(32.dp))

                    TitleWithIcon(text = "Select payment method", image = GlideIcons.Wallet)

                    Spacer(Modifier.height(16.dp))

                    PaymentMethodList(
                        items = uiState.paymentMethods,
                        selectedIndex = uiState.selectedPaymentMethodIndex,
                        onPaymentMethodSelected = onPaymentMethodSelected
                    )

                    Spacer(Modifier.height(32.dp))

                    Button(
                        onClick = onTopUpClick,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Top up")
                    }

                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun TitleWithIcon(text: String, image: ImageVector, modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = image,
            contentDescription = null
        )
        Spacer(Modifier.width(16.dp))
        Text(text = text, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun PaymentMethodList(
    items: PaymentMethods,
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
    onPaymentMethodSelected: (Int) -> Unit
) {
    val hapticFeedback = LocalHapticFeedback.current
    Column(modifier = modifier) {
        items.value.forEachIndexed { index, paymentMethod ->
            PaymentMethodItem(
                title = paymentMethod.title,
                iconResId = paymentMethod.iconResId,
                selected = index == selectedIndex,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                    onPaymentMethodSelected(index)
                }
            )
        }
    }
}

@Composable
fun PaymentMethodItem(
    title: String,
    @DrawableRes iconResId: Int,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.clickable(onClick = onClick, indication = null),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge
        )
        Spacer(Modifier.weight(1f))
        Box(modifier = Modifier.padding(4.dp)) {
            Image(
                painter = painterResource(iconResId),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TopUpScreenPreview() {
    GlideAppTheme {
        TopUpScreenContent(
            uiState = TopUpUiState(paymentMethods = PaymentMethod.entries.toPaymentMethods()),
            onBackClick = {},
            onAmountValueChange = {},
            onPaymentMethodSelected = {},
            onTopUpClick = {}
        )
    }
}
