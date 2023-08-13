package com.apsl.glideapp.feature.rides.viewmodels

import androidx.compose.runtime.Immutable
import java.net.SocketTimeoutException
import timber.log.Timber

@Immutable
class AllRidesUiError(exception: Exception) {

    //    @StringRes
//    val textResId: Int
    val text: String

    init {
        Timber.d(exception)
        
        text = when (exception) {
            is SocketTimeoutException -> "Server error"
            else -> "Unknown Error"
        }
    }

    constructor(throwable: Throwable) : this(Exception(throwable))

    constructor(message: String) : this(Exception(message))
}
