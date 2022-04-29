package com.lollipop.rdcmdt.helper

import java.text.NumberFormat

object CurrencyConverter {
    fun currencyConverter(value: Float): String {
        val numberFormat = NumberFormat.getNumberInstance()
        numberFormat.maximumFractionDigits = 0;
        return numberFormat.format(value)
    }
}