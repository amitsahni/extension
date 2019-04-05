@file:JvmName("LogUtils")
package com.extension

import android.util.Log
import com.extension.Log.IS_DEBUG
import com.extension.Log.TAG

object Log {
    val TAG = "Logger"
    var IS_DEBUG = true
}
fun String.printInfo() {
    if (IS_DEBUG) {
        Log.i(TAG, this)
    }
}

fun String.printError() {
    if (IS_DEBUG) {
        Log.e(TAG, this)
    }
}

fun String.printWarn() {
    if (IS_DEBUG) {
        Log.w(TAG, this)
    }
}

fun Any.printInfo() {
    if (IS_DEBUG) {
        Log.i(TAG, this.toString())
    }
}

fun Any.printError() {
    if (IS_DEBUG) {
        Log.e(TAG, this.toString())
    }
}

fun Any.printWarn() {
    if (IS_DEBUG) {
        Log.w(TAG, this.toString())
    }
}

