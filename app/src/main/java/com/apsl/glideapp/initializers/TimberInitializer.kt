package com.apsl.glideapp.initializers

import android.content.Context
import androidx.startup.Initializer
import com.apsl.glideapp.BuildConfig
import com.apsl.glideapp.core.util.CrashReportingTree
import timber.log.Timber

@Suppress("Unused")
class TimberInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(if (BuildConfig.DEBUG) Timber.DebugTree() else CrashReportingTree)
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
