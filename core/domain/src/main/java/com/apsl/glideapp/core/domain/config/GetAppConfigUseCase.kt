package com.apsl.glideapp.core.domain.config

import javax.inject.Inject

class GetAppConfigUseCase @Inject constructor(
    private val appConfigRepository: AppConfigRepository
) {

    suspend operator fun invoke() = runCatching {
        appConfigRepository.getAppConfig()
    }
}
