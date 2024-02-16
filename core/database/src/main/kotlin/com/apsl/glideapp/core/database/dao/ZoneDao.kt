package com.apsl.glideapp.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.apsl.glideapp.core.database.entities.ZoneCoordinatesEntity
import com.apsl.glideapp.core.database.entities.ZoneEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ZoneDao : BaseDao {

    @Query("SELECT * FROM zones JOIN zone_coordinates ON zones.id = zone_coordinates.zone_id")
    fun getAllZones(): Flow<Map<ZoneEntity, List<ZoneCoordinatesEntity>>>

    @Upsert
    suspend fun upsertZones(zones: List<ZoneEntity>)

    @Query("DELETE FROM zones")
    suspend fun deleteAllZones()
}
