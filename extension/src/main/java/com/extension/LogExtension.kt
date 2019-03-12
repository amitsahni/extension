package com.extension

import android.util.Log

fun String.printInfo() {
    if (BuildConfig.DEBUG) {
        Log.i(this.javaClass.simpleName, this)
    }
}

fun String.printError() {
    if (BuildConfig.DEBUG) {
        Log.e(this.javaClass.simpleName, this)
    }
}

fun String.printWarn() {
    if (BuildConfig.DEBUG) {
        Log.w(this.javaClass.simpleName, this)
    }
}

fun Any.printInfo() {
    if (BuildConfig.DEBUG) {
        Log.i(this.javaClass.simpleName, this.toString())
    }
}

fun Any.printError() {
    if (BuildConfig.DEBUG) {
        Log.e(this.javaClass.simpleName, this.toString())
    }
}

fun Any.printWarn() {
    if (BuildConfig.DEBUG) {
        Log.w(this.javaClass.simpleName, this.toString())
    }
}

