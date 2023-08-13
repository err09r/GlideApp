package com.apsl.glideapp.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.apsl.glideapp.core.database.entities.RideEntity

@Dao
interface RideDao {

    @Query("SELECT (SELECT COUNT(*) FROM rides) == 0")
    fun isTableEmpty(): Boolean

    @Query("SELECT * FROM rides")
    fun getRidePagingSource(): PagingSource<Int, RideEntity>

    @Upsert
    suspend fun upsertAllRides(transactions: List<RideEntity>)

    @Query("DELETE FROM rides")
    suspend fun deleteAllRides()
}
