package com.apsl.glideapp.core.domain.auth

import javax.inject.Inject

class CheckIfUsernameValidUseCase @Inject constructor() {

    operator fun invoke(username: String): Boolean {
        return username.matches(Regex("^[a-z0-9_-]{4,20}\$"))
    }
}
