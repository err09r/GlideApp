package com.apsl.glideapp.core.domain.transaction

import javax.inject.Inject

class CreateVoucherTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {

    suspend operator fun invoke(voucherCode: String) = runCatching {
        transactionRepository.createVoucherTransaction(voucherCode = voucherCode.trim())
    }
}
