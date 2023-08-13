package com.apsl.glideapp.core.ui

import androidx.lifecycle.ViewModel
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

    private val tag = this.javaClass.simpleName

    init {
        Timber.tag(tag).d("init")
    }

    override fun onCleared() {
        super.onCleared()
        Timber.tag(tag).d("onCleared")
    }
}
