package com.apsl.glideapp.core.domain.transaction

import javax.inject.Inject

class GetUserTransactionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {

    suspend operator fun invoke(limit: Int = 0) = runCatching {
        transactionRepository.getUserTransactions(limit = limit)
    }
}
