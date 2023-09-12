package com.apsl.glideapp.core.datastore

import androidx.datastore.core.Serializer
import java.io.InputStream
import java.io.OutputStream
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import timber.log.Timber

@Serializable
data class EncryptedAppPreferences(
    val authToken: String? = null
)

object EncryptedAppPreferencesSerializer : Serializer<EncryptedAppPreferences> {

    override val defaultValue: EncryptedAppPreferences = EncryptedAppPreferences()

    override suspend fun readFrom(input: InputStream): EncryptedAppPreferences {
        return try {
            ProtoBuf.decodeFromByteArray<EncryptedAppPreferences>(input.readBytes())
        } catch (e: Exception) {
            Timber.e(e.message)
            defaultValue
        }
    }

    override suspend fun writeTo(t: EncryptedAppPreferences, output: OutputStream) {
        output.write(ProtoBuf.encodeToByteArray(t))
    }
}
