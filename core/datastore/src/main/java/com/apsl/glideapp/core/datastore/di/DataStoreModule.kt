package com.apsl.glideapp.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKey
import com.apsl.glideapp.core.datastore.AppDataStore
import com.apsl.glideapp.core.datastore.AppDataStoreImpl
import com.apsl.glideapp.core.datastore.AppPreferences
import com.apsl.glideapp.core.datastore.AppPreferencesSerializer
import com.apsl.glideapp.core.datastore.EncryptedAppPreferences
import com.apsl.glideapp.core.datastore.EncryptedAppPreferencesSerializer
import com.apsl.glideapp.core.datastore.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.osipxd.security.crypto.createEncrypted
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun provideAppDataStore(
        dataStore: DataStore<AppPreferences>,
        encryptedDataStore: DataStore<EncryptedAppPreferences>
    ): AppDataStore {
        return AppDataStoreImpl(dataStore, encryptedDataStore)
    }

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<AppPreferences> {
        return DataStoreFactory.create(
            serializer = AppPreferencesSerializer,
            produceFile = { context.dataStoreFile(context.getString(R.string.app_prefs)) }
        )
    }

    @Singleton
    @Provides
    fun provideEncryptedDataStore(@ApplicationContext context: Context): DataStore<EncryptedAppPreferences> {
        return DataStoreFactory.createEncrypted(
            serializer = EncryptedAppPreferencesSerializer,
            produceFile = {
                EncryptedFile(
                    context = context,
                    file = context.dataStoreFile(context.getString(R.string.encr_prefs)),
                    masterKey = MasterKey(context)
                )
            }
        )
    }
}
