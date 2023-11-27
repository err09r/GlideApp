package com.apsl.glideapp.core.util.android

import android.util.Log
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import timber.log.Timber

object CrashReportingTree : Timber.Tree() {

    private val firebaseCrashlytics = Firebase.crashlytics

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority != Log.ERROR) {
            return
        }

        firebaseCrashlytics.log("$priority | $tag | $message")

        if (t != null) {
            firebaseCrashlytics.recordException(t)
        }
    }
}
