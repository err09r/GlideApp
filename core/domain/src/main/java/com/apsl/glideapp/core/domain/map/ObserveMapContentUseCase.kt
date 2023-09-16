package com.apsl.glideapp.core.domain.map

import com.apsl.glideapp.common.models.ZoneType
import com.apsl.glideapp.common.util.asResult
import com.apsl.glideapp.core.domain.ride.RideRepository
import com.apsl.glideapp.core.domain.zone.ZoneRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onStart

class ObserveMapContentUseCase @Inject constructor(
    private val mapRepository: MapRepository,
    private val zoneRepository: ZoneRepository,
    private val rideRepository: RideRepository
) {

    operator fun invoke(): Flow<Result<MapContent>> = combine(
        zoneRepository.getAllZones().distinctUntilChanged(),
        rideRepository.isRideModeActive.distinctUntilChanged()
    ) { zones, isRideModeActive ->
        println("isRideModeActive: $isRideModeActive, zones: ${zones.size}")
        if (isRideModeActive) {
            flowOf(
                MapContent(
                    availableVehicles = emptyList(),
                    ridingZones = zones.filter { it.type == ZoneType.Riding },
                    noParkingZones = zones.filter { it.type == ZoneType.NoParking }
                )
            )
        } else {
            mapRepository.remoteMapContent
                .asResult()
                .mapNotNull { it.getOrNull() }
                .map { remoteMapContent ->
                    MapContent(
                        availableVehicles = remoteMapContent.availableVehicles,
                        ridingZones = zones.filter { it.type == ZoneType.Riding },
                        noParkingZones = zones.filter { it.type == ZoneType.NoParking }
                    )
                }
                .onStart {
                    emit(
                        MapContent(
                            availableVehicles = emptyList(),
                            ridingZones = zones.filter { it.type == ZoneType.Riding },
                            noParkingZones = zones.filter { it.type == ZoneType.NoParking }
                        )
                    )
                }
        }
    }
        .flatMapLatest { it }
        .asResult()
}
