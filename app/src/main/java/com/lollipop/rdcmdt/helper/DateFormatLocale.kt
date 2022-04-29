package com.lollipop.rdcmdt.helper

import java.text.SimpleDateFormat
import java.util.*

object DateFormatLocale {
    val locale = Locale("en", "EN")
    val dateOnly = SimpleDateFormat("yyyy-MM-dd", locale)
    val dateWithMonth = SimpleDateFormat("dd MMMM yyyy", locale)
    val dateDayWithHalfMonthSecond = SimpleDateFormat("dd MMM yyyy", locale)

    /**
     *  Create locale ID datetime format with half month and day
     *
     *  example input : 2017-11-29
     *
     *  example output : 29 Nov 2017
     *
     *  @param time as Timestamp
     */

    fun localeDateDayParseHalfMonthSecond(time: String): String {
        return try {
            val date = dateOnly.parse(time)
            dateDayWithHalfMonthSecond.format(date!!)
        } catch (ex: Exception) {
            ""
        }
    }

    /**
     *  Create locale ID datetime format with full month with day name
     *
     *  example input : 2017-11-29
     *
     *  example output : 29 November 2017
     *
     *  @param time as Timestamp
     */

    fun localeDateParseFullMonthWithoutDay(time: String): String {
        return try {
            val date = dateOnly.parse(time)
            dateWithMonth.format(date!!)
        } catch (ex: Exception) {
            ""
        }
    }
}