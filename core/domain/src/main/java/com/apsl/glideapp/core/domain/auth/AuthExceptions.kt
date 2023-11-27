package com.apsl.glideapp.core.domain.auth

class InvalidAuthTokenException(message: String = "Retrieved token is invalid") : Exception(message)