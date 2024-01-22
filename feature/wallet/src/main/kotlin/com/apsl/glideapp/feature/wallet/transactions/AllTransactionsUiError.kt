package com.apsl.glideapp.feature.wallet.transactions

import androidx.compose.runtime.Immutable
import java.net.SocketTimeoutException

@Immutable
class AllTransactionsUiError(exception: Exception) {

    //    @StringRes
//    val textResId: Int
    val text: String

    init {
        text = when (exception) {
            is SocketTimeoutException -> "Server error"
            else -> "Unknown Error"
        }
    }

    constructor(throwable: Throwable) : this(Exception(throwable))

    constructor(message: String) : this(Exception(message))
}
