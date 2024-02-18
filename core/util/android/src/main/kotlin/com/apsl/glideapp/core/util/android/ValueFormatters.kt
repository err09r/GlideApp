@file:Suppress("Unused")

package com.apsl.glideapp.core.util.android

import android.icu.text.NumberFormat
import android.icu.util.Currency
import timber.log.Timber

abstract class NumberFormatter {

    protected abstract val formatter: NumberFormat
    protected open val defaultValue = 0

    open val currency: String? get() = this.formatter.currency?.symbol

    open fun format(value: Number): String {
        return try {
            checkNotNull(formatter.format(value))
        } catch (e: Exception) {
            Timber.d(e.message)
            value.toString()
        }
    }

    open fun default(): String = format(defaultValue)

    companion object : NumberFormatter() {

        private const val FRACTIONAL_MIN_FRACTION_DIGITS = 2
        private const val FRACTIONAL_MAX_FRACTION_DIGITS = FRACTIONAL_MIN_FRACTION_DIGITS

        override val formatter: NumberFormat
            get() = NumberFormat.getInstance().apply {
                isGroupingUsed = false
            }

        override fun format(value: Number): String = formatter.format(value)

        override fun default(): String = defaultValue.toString()

        fun defaultFractional(): String = formatter
            .apply {
                minimumFractionDigits = FRACTIONAL_MIN_FRACTION_DIGITS
                maximumFractionDigits = FRACTIONAL_MAX_FRACTION_DIGITS
            }
            .format(defaultValue.toFloat())
    }
}

object CurrencyFormatter : NumberFormatter() {

    private const val PLN_ISO_CODE = "PLN"

    override val currency: String = super.currency ?: PLN_ISO_CODE

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
