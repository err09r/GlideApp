package com.apsl.glideapp.core.di

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.apsl.glideapp.core.data.paging.RideRemoteMediator
import com.apsl.glideapp.core.data.paging.TransactionRemoteMediator
import com.apsl.glideapp.core.database.dao.RideDao
import com.apsl.glideapp.core.database.dao.TransactionDao
import com.apsl.glideapp.core.database.entities.RideEntity
import com.apsl.glideapp.core.database.entities.TransactionEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PagingModule {

    @Singleton
    @Provides
    fun provideTransactionPager(
        transactionRemoteMediator: TransactionRemoteMediator,
        transactionDao: TransactionDao
    ): Pager<Int, TransactionEntity> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = transactionRemoteMediator,
            pagingSourceFactory = { transactionDao.getTransactionPagingSource() }
        )
    }

    @Singleton
    @Provides
    fun provideRidePager(
        rideRemoteMediator: RideRemoteMediator,
        rideDao: RideDao
    ): Pager<Int, RideEntity> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = rideRemoteMediator,
            pagingSourceFactory = { rideDao.getRidePagingSource() }
        )
    }
}
