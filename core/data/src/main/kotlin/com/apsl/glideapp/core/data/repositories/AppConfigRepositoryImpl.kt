package com.apsl.glideapp.core.data.repositories

import com.apsl.glideapp.core.datastore.AppDataStore
import com.apsl.glideapp.core.domain.config.AppConfigRepository
import com.apsl.glideapp.core.model.AppConfig
import com.apsl.glideapp.core.network.http.GlideApi
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull

class AppConfigRepositoryImpl @Inject constructor(
    private val api: GlideApi,
    private val appDataStore: AppDataStore
) : AppConfigRepository {

    override suspend fun getAppConfig(): AppConfig? {
        return appDataStore.unlockDistance.firstOrNull()?.let {
            AppConfig(unlockDistanceMeters = it)
        }
    }

    override suspend fun updateAppConfig() {
        val config = api.getAppConfig()
        appDataStore.saveUnlockDistance(config.unlockDistance)
    }
}
