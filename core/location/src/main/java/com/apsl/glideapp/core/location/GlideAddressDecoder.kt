package com.apsl.glideapp.core.location

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import com.apsl.glideapp.common.models.Coordinates
import com.apsl.glideapp.core.domain.location.AddressDecoder
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import timber.log.Timber

class GlideAddressDecoder @Inject constructor(
    @ApplicationContext context: Context
) : AddressDecoder {

    private val geocoder = Geocoder(context)

    override suspend fun decodeFromCoordinates(coordinates: Coordinates): String? =
        suspendCoroutine { continuation ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

                val callback = object : Geocoder.GeocodeListener {

                    override fun onGeocode(results: MutableList<Address>) {
                        Timber.d(results.toString())
                        val address = results.firstOrNull()
                        continuation.resume(address?.toAddressString())
                    }

                    override fun onError(errorMessage: String?) {
                        super.onError(errorMessage)
                        Timber.d("Geocoding failed: $errorMessage")
                        continuation.resume(null)
                    }
                }

                geocoder.getFromLocation(
                    coordinates.latitude,
                    coordinates.longitude,
                    GEOCODER_MAX_RESULTS,
                    callback
                )
            } else {
                val results = geocoder.getFromLocation(
                    coordinates.latitude,
                    coordinates.longitude,
                    GEOCODER_MAX_RESULTS
                )
                val address = results?.firstOrNull()
                continuation.resume(address?.toAddressString())
            }
        }

    private fun Address.toAddressString(): String {
        val street = this.thoroughfare ?: ""
        val number = this.subThoroughfare ?: ""
        val city = this.locality ?: ""
        return "$street $number, $city"
    }

    private companion object {
        private const val GEOCODER_MAX_RESULTS = 1
    }
}
