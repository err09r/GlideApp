package com.apsl.glideapp.feature.home.maps

import android.location.Location
import com.google.android.gms.maps.LocationSource
import timber.log.Timber

object GlideLocationSource : LocationSource {

    private var listener: LocationSource.OnLocationChangedListener? = null

    override fun activate(listener: LocationSource.OnLocationChangedListener) {
        GlideLocationSource.listener = listener
    }

    override fun deactivate() {
        listener = null
    }

    fun onLocationChanged(location: Location) {
        listener?.onLocationChanged(location)
    }
}
