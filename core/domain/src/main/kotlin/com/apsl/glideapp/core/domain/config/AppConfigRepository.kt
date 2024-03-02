package com.apsl.glideapp.core.domain.config

import com.apsl.glideapp.core.model.AppConfig

interface AppConfigRepository {
    suspend fun getAppConfig(): AppConfig?
    suspend fun updateAppConfig(): Boolean
}
