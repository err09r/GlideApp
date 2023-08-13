package com.apsl.glideapp.core.domain.transaction

import androidx.paging.PagingData
import com.apsl.glideapp.common.models.TransactionType
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getUserTransactionsPaginated(): Flow<PagingData<Transaction>>
    suspend fun getUserTransactions(limit: Int): List<Transaction>
    suspend fun createTransaction(type: TransactionType, amount: Double)
    suspend fun createVoucherTransaction(voucherCode: String)
}
