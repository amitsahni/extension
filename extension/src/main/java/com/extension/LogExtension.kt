package com.extension

import android.util.Log
import com.extension.Log.TAG
import java.util.logging.Logger

object Log {
    val TAG = Logger.getGlobal().javaClass.name
}
fun String.printInfo() {
    if (BuildConfig.DEBUG) {
        Log.i(TAG, this)
    }
}

fun String.printError() {
    if (BuildConfig.DEBUG) {
        Log.e(TAG, this)
    }
}

fun String.printWarn() {
    if (BuildConfig.DEBUG) {
        Log.w(TAG, this)
    }
}

fun Any.printInfo() {
    if (BuildConfig.DEBUG) {
        Log.i(TAG, this.toString())
    }
}

fun Any.printError() {
    if (BuildConfig.DEBUG) {
        Log.e(TAG, this.toString())
    }
}

fun Any.printWarn() {
    if (BuildConfig.DEBUG) {
        Log.w(TAG, this.toString())
    }
}

