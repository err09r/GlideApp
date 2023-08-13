package com.apsl.glideapp.core.domain.map

import com.apsl.glideapp.core.domain.home.Vehicle
import com.apsl.glideapp.core.domain.home.Zone

data class MapState(
    val availableVehicles: List<Vehicle>,
    val ridingZones: List<Zone>,
    val noParkingZones: List<Zone>
)
