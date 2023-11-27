@file:Suppress("Unused")

package com.apsl.glideapp.feature.home.map

import android.location.Location
import androidx.compose.runtime.Immutable
import com.apsl.glideapp.core.model.UserLocation
import com.apsl.glideapp.core.util.maps.toLocation
import com.google.android.gms.maps.LocationSource

@Immutable
object HomeLocationSource : LocationSource {

    private var listener: LocationSource.OnLocationChangedListener? = null

    override fun activate(listener: LocationSource.OnLocationChangedListener) {
        this.listener = listener
    }

    override fun deactivate() {
        listener = null
    }

    fun onLocationChanged(location: Location) {
        listener?.onLocationChanged(location)
    }

    fun onLocationChanged(userLocation: UserLocation) {
        listener?.onLocationChanged(userLocation.toLocation())
    }
}
