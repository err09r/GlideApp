package com.apsl.glideapp.core.domain.transaction

sealed class TransactionException(message: String? = null) : Exception(message) {
    data object InvalidVoucherCodeException : TransactionException("Voucher code is invalid")
}
