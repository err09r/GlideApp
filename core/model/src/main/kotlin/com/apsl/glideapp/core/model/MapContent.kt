package com.apsl.glideapp.core.model

data class MapContent(
    val availableVehicles: List<Vehicle>,
    val ridingZones: List<Zone>,
    val noParkingZones: List<Zone>
)
