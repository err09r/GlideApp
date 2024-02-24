package com.apsl.glideapp.feature.wallet.voucher

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apsl.glideapp.core.ui.FeatureScreen
import com.apsl.glideapp.core.ui.GlideImage
import com.apsl.glideapp.core.ui.ScreenActions
import com.apsl.glideapp.core.ui.imeCollapsible
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import kotlinx.coroutines.launch
import com.apsl.glideapp.core.ui.R as CoreR

@Composable
fun VoucherScreen(
    onNavigateToPayment: () -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToVoucherActivated: () -> Unit,
    viewModel: VoucherViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    ScreenActions(viewModel.actions) { action ->
        when (action) {
            is VoucherAction.VoucherProcessingStarted -> onNavigateToPayment()
            is VoucherAction.VoucherProcessingCompleted -> onNavigateToVoucherActivated()
            is VoucherAction.VoucherActivationError -> {
                onNavigateBack()
                scope.launch {
                    val error = context.getString(action.textResId)
                    snackbarHostState.showSnackbar(error)
                }
            }
        }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
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
        topBarText = stringResource(CoreR.string.voucher_screen_title),
        snackbarHostState = snackbarHostState,
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

                    Text(
                        text = stringResource(CoreR.string.voucher_screen_card_title),
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(Modifier.height(16.dp))

                    GlideImage(
                        imageResId = CoreR.drawable.img_gift,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(Modifier.height(16.dp))

                    Text(text = stringResource(CoreR.string.voucher_screen_card_text))

                    Spacer(Modifier.height(16.dp))

                    OutlinedTextField(
                        value = uiState.codeTextFieldValue ?: "",
                        onValueChange = onCodeValueChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = stringResource(CoreR.string.voucher_screen_textfield_hint)) },
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
                        Text(text = stringResource(CoreR.string.voucher_screen_button))
                    }

                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VoucherScreenPreview() {
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
