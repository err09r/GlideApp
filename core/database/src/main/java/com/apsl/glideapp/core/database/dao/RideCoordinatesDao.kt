package com.apsl.glideapp.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.apsl.glideapp.core.database.entities.RideCoordinatesEntity

@Dao
interface RideCoordinatesDao : BaseDao {

    @Upsert
    suspend fun upsertRideCoordinates(coordinates: List<RideCoordinatesEntity>)

    @Query("DELETE FROM ride_coordinates")
    suspend fun deleteAllRideCoordinates()
}
