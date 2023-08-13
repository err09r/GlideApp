package com.apsl.glideapp.core.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.apsl.glideapp.common.dto.TransactionRequest
import com.apsl.glideapp.common.models.TransactionType
import com.apsl.glideapp.core.database.entities.TransactionEntity
import com.apsl.glideapp.core.domain.transaction.Transaction
import com.apsl.glideapp.core.domain.transaction.TransactionRepository
import com.apsl.glideapp.core.network.GlideApi
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepositoryImpl @Inject constructor(
    private val api: GlideApi,
    private val transactionPager: Pager<Int, TransactionEntity>
) : TransactionRepository {

    override fun getUserTransactionsPaginated(): Flow<PagingData<Transaction>> {
        return transactionPager.flow.map { pagingData ->
            pagingData.map { entity ->
                Transaction(
                    id = entity.id,
                    amount = entity.amount,
                    type = entity.type,
                    dateTime = entity.dateTime
                )
            }
        }
    }

    override suspend fun getUserTransactions(limit: Int): List<Transaction> {
        return api.getUserTransactions(page = 1, limit = limit).map { dto ->
            Transaction(
                id = dto.id,
                amount = dto.amount,
                type = dto.type,
                dateTime = dto.dateTime
            )
        }
    }

    override suspend fun createTransaction(type: TransactionType, amount: Double) {
        api.createTransaction(body = TransactionRequest(type = type, amount = amount))
    }

    override suspend fun createVoucherTransaction(voucherCode: String) {
        api.createTransaction(
            body = TransactionRequest(
                type = TransactionType.Voucher,
                amount = null,
                voucherCode = voucherCode
            )
        )
    }
}
