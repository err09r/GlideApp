package com.apsl.glideapp.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.apsl.glideapp.core.database.entities.TransactionEntity

@Dao
interface TransactionDao : BaseDao {

    @Query("SELECT * FROM transactions")
    fun getTransactionPagingSource(): PagingSource<Int, TransactionEntity>

    @Upsert
    suspend fun upsertTransactions(transactions: List<TransactionEntity>)

    @Query("DELETE FROM transactions")
    suspend fun deleteAllTransactions()
}
