package com.apsl.glideapp.core.domain.transaction

import com.apsl.glideapp.common.models.TransactionType
import javax.inject.Inject

class CreateTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {

    suspend operator fun invoke(type: TransactionType, amount: Double) = runCatching {
        transactionRepository.createTransaction(type = type, amount = amount)
    }
}
