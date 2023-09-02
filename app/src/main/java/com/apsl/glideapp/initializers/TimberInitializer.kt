package com.apsl.glideapp.initializers

import android.content.Context
import androidx.startup.Initializer
import com.apsl.glideapp.BuildConfig
import com.apsl.glideapp.core.util.CrashReportingTree
import timber.log.Timber

@Suppress("Unused")
class TimberInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        val tree = if (BuildConfig.DEBUG) Timber.DebugTree() else CrashReportingTree
        Timber.plant(tree)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
