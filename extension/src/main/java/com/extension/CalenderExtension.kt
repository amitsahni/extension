package com.extension

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.util.*
import java.util.concurrent.TimeUnit

object D {
    val FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
}

fun now() = Calendar.getInstance().time!!
fun calendar() = Calendar.getInstance()
fun Long.toDate(): Date = Date(this)
fun Long.toUTC(): Date = Date(this).toUTC
fun time() = now().time

fun currentSecond() = TimeUnit.MILLISECONDS.toSeconds(time())
fun currentMinute() = TimeUnit.MILLISECONDS.toMinutes(time())
fun currentHour() = TimeUnit.MILLISECONDS.toHours(time())

fun Date.isMonday() = calendar().get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY
fun Date.isTuesday() = calendar().get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY
fun Date.isWednesday() = calendar().get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY
fun Date.isThursday() = calendar().get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY
fun Date.isFriday() = calendar().get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY
fun Date.isSaturday() = calendar().get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
fun Date.isSunday() = calendar().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY

fun formatDate(
    format: String = D.FORMAT,
    timeZone: TimeZone = TimeZone.getDefault(),
    locale: Locale = Locale.getDefault()
): DateFormat {
    val sdf = SimpleDateFormat(format, locale)
    sdf.timeZone = timeZone
    return sdf
}

fun String.parseDate(
    format: String = D.FORMAT,
    locale: Locale = Locale.getDefault(),
    timeZone: TimeZone = TimeZone.getDefault()
): Date? {
    return try {
        formatDate(format, timeZone, locale).parse(this)
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
        return now().toUTC
    }

fun currentUTC(
    format: String = D.FORMAT,
    locale: Locale = Locale.getDefault()
): String {
    return formatDate(format, TimeZone.getDefault(), locale).format(now().toUTC)
}

val Date.toUTC: Date
    get() {
        val calendar = calendar()
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


