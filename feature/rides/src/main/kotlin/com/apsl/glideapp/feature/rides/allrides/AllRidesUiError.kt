package com.apsl.glideapp.feature.rides.allrides

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import java.net.SocketTimeoutException
import com.apsl.glideapp.core.ui.R as CoreR

@Immutable
class AllRidesUiError(exception: Exception) {

    @StringRes
    val textResId: Int

    init {
        textResId = when (exception) {
            is SocketTimeoutException -> CoreR.string.error_server
            else -> CoreR.string.error_unknown
        }
    }

    constructor(throwable: Throwable) : this(Exception(throwable))
}
