package com.apsl.glideapp.feature.wallet.transactions

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.apsl.glideapp.core.ui.R
import java.net.SocketTimeoutException

@Immutable
class AllTransactionsUiError(exception: Exception) {

    @StringRes
    val textResId: Int

    init {
        textResId = when (exception) {
            is SocketTimeoutException -> R.string.error_server
            else -> R.string.error_unknown
        }
    }

    constructor(throwable: Throwable) : this(Exception(throwable))
}
