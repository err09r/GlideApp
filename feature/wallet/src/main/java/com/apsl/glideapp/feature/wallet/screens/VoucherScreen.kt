package com.apsl.glideapp.feature.wallet.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apsl.glideapp.core.ui.FeatureScreen
import com.apsl.glideapp.core.ui.components.GlideImage
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.feature.wallet.viewmodels.VoucherAction
import com.apsl.glideapp.feature.wallet.viewmodels.VoucherUiState
import com.apsl.glideapp.feature.wallet.viewmodels.VoucherViewModel
import com.apsl.glideapp.core.ui.R as CoreR

@Composable
fun VoucherScreen(
    viewModel: VoucherViewModel = hiltViewModel(),
    onNavigateToPayment: () -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToVoucherActivated: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.actions.collect { action ->
            when (action) {
                is VoucherAction.VoucherProcessingStarted -> onNavigateToPayment()
                is VoucherAction.VoucherProcessingCompleted -> onNavigateToVoucherActivated()
                is VoucherAction.VoucherActivationError -> {
                    onNavigateBack()
                    snackbarHostState.showSnackbar(action.message)
                }
            }
        }
    }

    VoucherScreenContent(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onBackClick = onNavigateBack,
        onCodeValueChange = remember { { viewModel.updateCodeTextFieldValue(it) } },
        onActivateClick = remember { { viewModel.activateCode() } }
    )
}

@Composable
fun VoucherScreenContent(
    uiState: VoucherUiState,
    snackbarHostState: SnackbarHostState,
    onBackClick: () -> Unit,
    onCodeValueChange: (String?) -> Unit,
    onActivateClick: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    FeatureScreen(
        topBarText = "Voucher",
        snackbarHostState = snackbarHostState,
        onBackClick = onBackClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(WindowInsets.systemBars)
                .padding(top = 32.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
                .imePadding()
        ) {
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(text = "Enter your code", style = MaterialTheme.typography.headlineSmall)

                    Spacer(Modifier.height(16.dp))

                    GlideImage(
                        imageResId = CoreR.drawable.img_gift,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(Modifier.height(16.dp))

                    Text(text = "To activate the voucher and receive the bonus, enter your code below")

                    Spacer(Modifier.height(16.dp))

                    OutlinedTextField(
                        value = uiState.codeTextFieldValue ?: "",
                        onValueChange = onCodeValueChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = "e.g. GIFTCODE") },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Characters,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Go
                        ),
                        keyboardActions = KeyboardActions(
                            onGo = {
                                keyboardController?.hide()
                                onActivateClick()
                            }
                        ),
                        singleLine = true
                    )

                    Spacer(Modifier.height(48.dp))

                    Button(
                        onClick = onActivateClick,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = uiState.isActionButtonActive
                    ) {
                        Text(text = "Activate")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VoucherScreenPreview() {
    GlideAppTheme {
        VoucherScreenContent(
            uiState = VoucherUiState(),
            snackbarHostState = SnackbarHostState(),
            onBackClick = {},
            onCodeValueChange = {},
            onActivateClick = {}
        )
    }
}
