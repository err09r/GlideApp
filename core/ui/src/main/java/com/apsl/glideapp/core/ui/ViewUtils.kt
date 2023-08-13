package com.apsl.glideapp.core.ui

import android.content.res.Resources
import android.util.TypedValue

val Number.asDp
    get() =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()
