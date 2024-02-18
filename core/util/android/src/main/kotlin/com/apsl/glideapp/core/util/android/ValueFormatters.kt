@file:Suppress("Unused")

package com.apsl.glideapp.core.util.android

import android.icu.text.NumberFormat
import android.icu.util.Currency
import timber.log.Timber

abstract class NumberFormatter {

    protected abstract val formatter: NumberFormat
    protected open val defaultValue = 0

    open fun format(value: Number): String {
        return try {
            checkNotNull(formatter.format(value))
        } catch (e: Exception) {
            Timber.d(e.message)
            value.toString()
        }
    }

    fun default(): String = format(defaultValue)

    companion object : NumberFormatter() {
        override val formatter: NumberFormat get() = NumberFormat.getInstance()
        override fun format(value: Number) = defaultValue.toString()
    }
}

object CurrencyFormatter : NumberFormatter() {

    private const val PLN_ISO_CODE = "PLN"

    override val formatter: NumberFormat
        get() = NumberFormat.getCurrencyInstance().apply {
            currency = Currency.getInstance(PLN_ISO_CODE)
            isGroupingUsed = false
        }
}

object DistanceFormatter : NumberFormatter() {

    private const val MIN_FRACTION_DIGITS = 1
    private const val MAX_FRACTION_DIGITS = MIN_FRACTION_DIGITS

    override val formatter: NumberFormat
        get() = NumberFormat.getInstance().apply {
            isGroupingUsed = false
            minimumFractionDigits = MIN_FRACTION_DIGITS
            maximumFractionDigits = MAX_FRACTION_DIGITS
        }
}
