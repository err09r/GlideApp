package com.apsl.glideapp.core.datastore

import androidx.datastore.core.Serializer
import com.apsl.glideapp.common.models.Coordinates
import java.io.InputStream
import java.io.OutputStream
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import timber.log.Timber

@Serializable
data class AppPreferences(
    val lastUserLocation: Coordinates? = null,
    val isRideModeActive: Boolean? = null,
    val unlockDistance: Double? = null
)

object AppPreferencesSerializer : Serializer<AppPreferences> {

    override val defaultValue: AppPreferences = AppPreferences()

    override suspend fun readFrom(input: InputStream): AppPreferences {
        return try {
            ProtoBuf.decodeFromByteArray<AppPreferences>(input.readBytes())
        } catch (e: Exception) {
            Timber.e(e.message)
            defaultValue
        }
    }

    override suspend fun writeTo(t: AppPreferences, output: OutputStream) {
        output.write(ProtoBuf.encodeToByteArray(t))
    }
}
