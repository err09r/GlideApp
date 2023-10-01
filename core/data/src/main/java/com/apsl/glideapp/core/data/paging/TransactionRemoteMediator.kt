package com.apsl.glideapp.core.data.paging

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.apsl.glideapp.common.util.now
import com.apsl.glideapp.core.database.AppDatabase
import com.apsl.glideapp.core.database.entities.TransactionEntity
import com.apsl.glideapp.core.domain.connectivity.ConnectivityObserver
import com.apsl.glideapp.core.model.ConnectionState
import com.apsl.glideapp.core.network.http.GlideApi
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.datetime.LocalDateTime
import timber.log.Timber

@Singleton
class TransactionRemoteMediator @Inject constructor(
    private val api: GlideApi,
    private val appDatabase: AppDatabase,
    private val connectivityObserver: ConnectivityObserver
) : RemoteMediator<Int, TransactionEntity>() {

    private val transactionDao = appDatabase.transactionDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TransactionEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    ((lastItem.key ?: 1) / state.config.pageSize) + 1
                }
            }

            if (connectivityObserver.connectivityState.firstOrNull() == ConnectionState.Available) {
                return if (transactionDao.isTableEmpty()) {
                    MediatorResult.Error(Exception("Transaction table is empty"))
                } else {
                    MediatorResult.Success(true)
                }
            }

            Timber.d("Loadkey: $loadKey")

            val transactionDtos =
                api.getUserTransactions(page = loadKey, limit = state.config.pageSize)

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    transactionDao.deleteAllTransactions()
                }

                val transactionEntities = transactionDtos.map {
                    TransactionEntity(
                        id = it.id,
                        key = it.key,
                        amount = it.amount,
                        dateTime = it.dateTime,
                        type = it.type,
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now(),
                    )
                }
                transactionDao.upsertTransactions(transactionEntities)
            }
            MediatorResult.Success(endOfPaginationReached = transactionDtos.size < state.config.pageSize)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
