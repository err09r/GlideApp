package com.apsl.glideapp.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.apsl.glideapp.core.database.entities.ZoneCoordinatesEntity

@Dao
interface ZoneCoordinatesDao : BaseDao {

    @Upsert
    suspend fun upsertZoneCoordinates(coordinates: List<ZoneCoordinatesEntity>)

    @Query("DELETE FROM zone_coordinates")
    suspend fun deleteAllZoneCoordinates()
}
