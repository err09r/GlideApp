package com.apsl.glideapp.feature.home.screens

import androidx.compose.runtime.Immutable

@Immutable
data class UserInfo(
    val username: String? = null,
    val totalDistance: Int = 0,
    val totalRides: Int = 0
)
