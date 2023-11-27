package com.apsl.glideapp.core.domain.auth

import javax.inject.Inject

class CheckIfPasswordValidUseCase @Inject constructor() {

    operator fun invoke(password: String): Boolean {
        return password.matches(Regex("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}\$"))
    }
}
