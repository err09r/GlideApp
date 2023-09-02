package com.apsl.glideapp.core.util

import android.graphics.Bitmap
import android.view.View
import androidx.core.view.drawToBitmap

fun View.toBitmap(width: Int, height: Int): Bitmap {
    measure(
        View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
        View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
    )
    layout(0, 0, width, height)
    return drawToBitmap()
}
