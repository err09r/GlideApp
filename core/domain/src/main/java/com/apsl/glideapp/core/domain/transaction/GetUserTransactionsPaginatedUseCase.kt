package com.apsl.glideapp.core.domain.transaction

import javax.inject.Inject

class GetUserTransactionsPaginatedUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {

    operator fun invoke() = transactionRepository.getUserTransactionsPaginated()
}
