package com.apsl.glideapp.core.domain.transaction

import javax.inject.Inject

class ParseUserTransactionAmountUseCase @Inject constructor() {

    operator fun invoke(value: String?): Result<Double> = runCatching {
        requireNotNull(value)
        value
            .replace(regex = Regex("[^0-9.,]"), replacement = "")
            .toDouble()
    }
}
