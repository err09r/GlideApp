package com.apsl.glideapp.core.domain.transaction

import javax.inject.Inject

class GetAllPaymentMethodsUseCase @Inject constructor() {

    operator fun invoke() = runCatching {
        PaymentMethod.entries
    }
}
