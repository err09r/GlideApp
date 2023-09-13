package com.apsl.glideapp.core.data.repositories

import com.apsl.glideapp.core.datastore.AppDataStore
import com.apsl.glideapp.core.domain.config.AppConfigRepository
import com.apsl.glideapp.core.network.http.GlideApi
import javax.inject.Inject

class AppConfigRepositoryImpl @Inject constructor(
    private val api: GlideApi,
    private val appDataStore: AppDataStore
) : AppConfigRepository {

    override suspend fun updateAppConfig() {
        val config = api.getAppConfig()
        appDataStore.saveUnlockDistance(config.unlockDistance)
    }
}
