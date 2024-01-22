package com.apsl.glideapp.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.apsl.glideapp.core.database.entities.RideCoordinatesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RideCoordinatesDao : BaseDao {

    @Query("SELECT * FROM ride_coordinates")
    fun getAllRideCoordinates(): Flow<List<RideCoordinatesEntity>>

    @Query("SELECT * FROM ride_coordinates WHERE ride_id = :id")
    suspend fun getRideCoordinatesByRideId(id: String): List<RideCoordinatesEntity>

    @Upsert
    suspend fun upsertRideCoordinates(coordinates: List<RideCoordinatesEntity>)

    @Query("DELETE FROM ride_coordinates")
    suspend fun deleteAllRideCoordinates()
}
