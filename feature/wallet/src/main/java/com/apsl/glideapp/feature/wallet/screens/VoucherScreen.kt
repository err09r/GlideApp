package com.apsl.glideapp.feature.wallet.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalContext
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
import com.apsl.glideapp.feature.wallet.viewmodels.VoucherAction
import com.apsl.glideapp.feature.wallet.viewmodels.VoucherUiState
import com.apsl.glideapp.feature.wallet.viewmodels.VoucherViewModel

@Composable
fun VoucherScreen(
    viewModel: VoucherViewModel = hiltViewModel(),
    onNavigateToPayment: () -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToTopUpSuccess: () -> Unit
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.actions.collect { action ->
            when (action) {
                is VoucherAction.VoucherProcessingStarted -> onNavigateToPayment()
                is VoucherAction.VoucherProcessingCompleted -> onNavigateToTopUpSuccess()
                is VoucherAction.VoucherProcessingError -> {
                    onNavigateBack()
                    Toast.makeText(context, action.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    VoucherScreenContent(
        uiState = uiState,
        onBackClick = onNavigateBack,
        onCodeValueChange = viewModel::setCodeTextFieldValue,
        onActivateClick = viewModel::activateCode
    )
}

@Composable
fun VoucherScreenContent(
    uiState: VoucherUiState,
    onBackClick: () -> Unit,
    onCodeValueChange: (String?) -> Unit,
    onActivateClick: () -> Unit
) {
    FeatureScreen(topBarText = "Redeem Voucher", onBackClick = onBackClick) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Enter code", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)

                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = uiState.codeTextFieldValue ?: "",
                    onValueChange = onCodeValueChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusEvent {},
                    label = {
                        Text(text = "e.g. ABC123X")
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Characters,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp)
                )

                Spacer(Modifier.height(48.dp))

                Button(
                    onClick = onActivateClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Activate", fontSize = 16.sp)
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
            onBackClick = {},
            onCodeValueChange = {},
            onActivateClick = {}
        )
    }
}
