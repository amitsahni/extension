@file:JvmName("DateUtils")
package com.extension

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object D {
    val FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
}

val now get() = Calendar.getInstance().time!!
val calendar get() = Calendar.getInstance()!!

val Long.toDate get() = Date(this)
val Long.toUTC get() = toDate.toUTC
val time = now.time

val currentSecond get() = TimeUnit.MILLISECONDS.toSeconds(time)
val currentMinute get() = TimeUnit.MILLISECONDS.toMinutes(time)
val currentHour get() = TimeUnit.MILLISECONDS.toHours(time)

fun Date.isMonday() = calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY
fun Date.isTuesday() = calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY
fun Date.isWednesday() = calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY
fun Date.isThursday() = calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY
fun Date.isFriday() = calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY
fun Date.isSaturday() = calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
fun Date.isSunday() = calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY

fun formatDate(
        format: String = D.FORMAT,
        locale: Locale = Locale.getDefault(),
        timeZone: TimeZone = TimeZone.getDefault()
): DateFormat {
    val sdf = SimpleDateFormat(format, locale)
    sdf.timeZone = timeZone
    return sdf
}

fun String?.parseDate(
    format: String = D.FORMAT,
    locale: Locale = Locale.getDefault(),
    timeZone: TimeZone = TimeZone.getDefault()
): Date? {
    return try {
        formatDate(format, locale, timeZone).parse(this)
    } catch (e: ParseException) {
        null
    }

}

//val currentUTC: Date
//    get() {
//        val calendar = calendar()
//        val ro = calendar.timeZone.rawOffset
//        val dst = calendar.timeZone.dstSavings
//        val isDayLight = TimeZone.getDefault().inDaylightTime(
//            calendar.time
//        )
//        var gmtMillis = calendar.timeInMillis - ro
//        if (isDayLight) {
//            gmtMillis = calendar.timeInMillis - ro.toLong() - dst.toLong()
//        }
//        return Date(gmtMillis)
//    }

val currentUTC: Date
    get() {
        return now.toUTC
    }

fun currentUTC(
    format: String = D.FORMAT,
    locale: Locale = Locale.getDefault()
): String {
    return formatDate(format, locale, TimeZone.getDefault()).format(now.toUTC)
}

val Date.toUTC: Date
    get() {
        val calendar = calendar
        val ro = calendar.timeZone.rawOffset
        val dst = calendar.timeZone.dstSavings
        val isDayLight = TimeZone.getDefault().inDaylightTime(
            this
        )
        var gmtMillis = getTime() - ro
        if (isDayLight) {
            gmtMillis = getTime() - ro.toLong() - dst.toLong()
        }
        return Date(gmtMillis)
    }

fun tomorrow(): Date {
    val cal = calendar
    cal.add(Calendar.DAY_OF_YEAR, 1)
    return cal.time
}

fun addDays(days : Int): Date {
    val cal = calendar
    cal.add(Calendar.DAY_OF_YEAR, days)
    return cal.time
}
