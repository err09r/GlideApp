package com.apsl.glideapp.core.domain.user

import javax.inject.Inject

class SaveWalletVisitedUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke() = runCatching {
        userRepository.saveWalletVisited(value = true)
    }
}
