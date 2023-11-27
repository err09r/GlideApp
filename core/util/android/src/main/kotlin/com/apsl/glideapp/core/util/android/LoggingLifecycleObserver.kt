package com.apsl.glideapp.core.util.android

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import timber.log.Timber

object LoggingLifecycleObserver : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        log(owner, "onCreate")
    }

    override fun onStart(owner: LifecycleOwner) {
        log(owner, "onStart")
    }

    override fun onResume(owner: LifecycleOwner) {
        log(owner, "onResume")
    }

    override fun onPause(owner: LifecycleOwner) {
        log(owner, "onPause")
    }

    override fun onStop(owner: LifecycleOwner) {
        log(owner, "onStop")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        log(owner, "onDestroy")
    }

    private fun log(owner: LifecycleOwner, message: String) {
        Timber.tag(owner::class.java.simpleName).d(message)
    }
}
