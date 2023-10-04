package com.apsl.glideapp.core.domain.zone

import com.apsl.glideapp.core.model.Zone
import kotlinx.coroutines.flow.Flow

interface ZoneRepository {
    fun getAllZones(): Flow<List<Zone>>
    suspend fun updateAllZones()
}
