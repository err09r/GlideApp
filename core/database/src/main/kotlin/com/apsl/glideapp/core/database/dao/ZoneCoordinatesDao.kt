package com.apsl.glideapp.core.database.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.apsl.glideapp.core.database.entities.ZoneCoordinatesEntity

@Dao
interface ZoneCoordinatesDao : BaseDao {

    @Upsert
    suspend fun upsertZoneCoordinates(coordinates: List<ZoneCoordinatesEntity>)
}
