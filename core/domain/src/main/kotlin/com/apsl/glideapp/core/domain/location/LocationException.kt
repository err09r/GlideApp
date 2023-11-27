package com.apsl.glideapp.core.domain.location

sealed class LocationException(message: String) : Exception(message)

class GpsDisabledException(
    message: String? = null
) : LocationException(message ?: "GPS is disabled")

class MissingLocationPermissionsException(
    message: String? = null
) : LocationException(message ?: "Missing location permissions")
