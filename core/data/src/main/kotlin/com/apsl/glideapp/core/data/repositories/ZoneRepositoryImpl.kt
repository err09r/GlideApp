package com.apsl.glideapp.core.data.repositories

import androidx.room.withTransaction
import com.apsl.glideapp.common.dto.ZoneDto
import com.apsl.glideapp.common.models.Coordinates
import com.apsl.glideapp.common.util.now
import com.apsl.glideapp.core.database.AppDatabase
import com.apsl.glideapp.core.database.entities.ZoneCoordinatesEntity
import com.apsl.glideapp.core.database.entities.ZoneEntity
import com.apsl.glideapp.core.domain.zone.ZoneRepository
import com.apsl.glideapp.core.model.Zone
import com.apsl.glideapp.core.network.http.GlideApi
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime

class ZoneRepositoryImpl @Inject constructor(
    private val api: GlideApi,
    private val database: AppDatabase
) : ZoneRepository {

    private val zoneDao = database.zoneDao()
    private val zoneCoordinatesDao = database.zoneCoordinatesDao()

    override suspend fun updateAllZones(): Boolean {
        val zones = api.getAllZones()
        val result = refreshZones(zones)
        return result
    }

    private suspend fun refreshZones(zones: List<ZoneDto>): Boolean {
        val zoneEntities = zones.map {
            ZoneEntity(
                id = it.id,
                code = it.code,
                title = it.title,
                type = it.type,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        }

        val zoneCoordinatesEntities = zones.flatMap { zone ->
            zone.border.points.map { coordinates ->
                ZoneCoordinatesEntity(
                    zoneId = zone.id,
                    latitude = coordinates.latitude,
                    longitude = coordinates.longitude,
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now()
                )
            }
        }

        database.withTransaction {
            zoneDao.deleteAllZones()
            zoneCoordinatesDao.deleteAllZoneCoordinates()
            zoneDao.upsertZones(zoneEntities)
            zoneCoordinatesDao.upsertZoneCoordinates(zoneCoordinatesEntities)
        }

        return (!zoneDao.isTableEmpty() && !zoneCoordinatesDao.isTableEmpty())
    }

    override fun getAllZones(): Flow<List<Zone>> = zoneDao.getAllZones()
        .map { zoneEntitiesMap ->
            zoneEntitiesMap.map { (zoneEntity, zoneCoordinatesEntities) ->
                Zone(
                    id = zoneEntity.id,
                    code = zoneEntity.code,
                    title = zoneEntity.title,
                    type = zoneEntity.type,
                    coordinates = zoneCoordinatesEntities.map {
                        Coordinates(it.latitude, it.longitude)
                    }
                )
            }
        }
}
