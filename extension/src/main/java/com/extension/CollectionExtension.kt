package com.extension

fun <T> Array<T>?.isBlank(): Boolean = this == null || isEmpty()

fun <T> Collection<T>?.isBlank(): Boolean = this == null || isEmpty()