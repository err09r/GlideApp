package com.apsl.glideapp.feature.rides.allrides

import androidx.compose.runtime.Immutable
import timber.log.Timber
import java.net.SocketTimeoutException

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
