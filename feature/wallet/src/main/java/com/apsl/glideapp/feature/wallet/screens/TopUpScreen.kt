package com.apsl.glideapp.feature.wallet.screens

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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material.icons.rounded.Money
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apsl.glideapp.core.ui.FeatureScreen
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.feature.wallet.models.PaymentMethodUiModel
import com.apsl.glideapp.feature.wallet.viewmodels.PaymentAction
import com.apsl.glideapp.feature.wallet.viewmodels.TopUpUiState
import com.apsl.glideapp.feature.wallet.viewmodels.TopUpViewModel

@Composable
fun TopUpScreen(
    viewModel: TopUpViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToPayment: () -> Unit,
    onNavigateToTopUpSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.actions.collect { action ->
            when (action) {
                is PaymentAction.PaymentProcessingStarted -> onNavigateToPayment()
                is PaymentAction.PaymentProcessingCompleted -> onNavigateToTopUpSuccess()
            }
        }
    }

    TopUpScreenContent(
        uiState = uiState,
        onBackClick = onNavigateBack,
        onAmountValueChange = viewModel::setAmountTextFieldValue,
        onAmountFocusEvent = viewModel::handleFocusEvent,
        onPaymentMethodSelected = viewModel::setSelectedPaymentMethodIndex,
        onTopUpClick = viewModel::startPaymentProcessing
    )
}

@Composable
fun TopUpScreenContent(
    uiState: TopUpUiState,
    onBackClick: () -> Unit,
    onAmountValueChange: (String) -> Unit,
    onAmountFocusEvent: (Boolean) -> Unit,
    onPaymentMethodSelected: (Int) -> Unit,
    onTopUpClick: () -> Unit
) {
    FeatureScreen(
        topBarText = "Account Top Up",
        onBackClick = onBackClick
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color.White,
                elevation = 8.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Rounded.Money,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(Modifier.width(16.dp))
                        Text(
                            text = "Top up your account",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                    Text("Enter the amount you want to top up your account")
                    Spacer(Modifier.height(16.dp))
                    OutlinedTextField(
                        value = uiState.amountTextFieldValue ?: "0.0",
                        onValueChange = onAmountValueChange,
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusEvent { focusState -> onAmountFocusEvent(focusState.isFocused) },
                        label = {
                            Text(text = "Amount")
                        },
                        trailingIcon = {
                            Text(text = "PLN", modifier = Modifier.padding(horizontal = 16.dp))
                        },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp)
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color.White,
                elevation = 8.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Rounded.CreditCard,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(Modifier.width(16.dp))
                        Text(
                            text = "Select payment method",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Spacer(Modifier.height(24.dp))
                    RadioButtonSample(
                        items = uiState.paymentMethods,
                        selectedIndex = uiState.selectedPaymentMethodIndex,
                        onPaymentMethodSelected = onPaymentMethodSelected
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = onTopUpClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Top Up", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun RadioButtonSample(
    items: List<PaymentMethodUiModel>,
    selectedIndex: Int,
    onPaymentMethodSelected: (Int) -> Unit
) {
    Column {
        items.forEachIndexed { index, paymentMethod ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = index == selectedIndex,
                        onClick = { onPaymentMethodSelected(index) }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = index == selectedIndex,
                    onClick = { onPaymentMethodSelected(index) }
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    text = paymentMethod.name,
                    style = MaterialTheme.typography.body1.merge()
                )
                Spacer(Modifier.weight(1f))
                Image(
                    painter = painterResource(id = paymentMethod.iconResId),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopUpScreenPreview() {
    GlideAppTheme {
        TopUpScreenContent(
            uiState = TopUpUiState(),
            onBackClick = {},
            onAmountValueChange = {},
            onAmountFocusEvent = {},
            onPaymentMethodSelected = {},
            onTopUpClick = {}
        )
    }
}
