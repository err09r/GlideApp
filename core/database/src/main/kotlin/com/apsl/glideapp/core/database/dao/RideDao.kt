package com.apsl.glideapp.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.apsl.glideapp.core.database.entities.RideEntity

@Dao
interface RideDao : BaseDao {

    @Query("SELECT * FROM rides")
    fun getRidePagingSource(): PagingSource<Int, RideEntity>

    @Upsert
    suspend fun upsertRides(rides: List<RideEntity>)

    @Query("DELETE FROM rides")
    suspend fun deleteAllRides()
}
