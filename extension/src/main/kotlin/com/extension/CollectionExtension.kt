@file:JvmName("CollectionUtils")
package com.extension

fun <T> Array<T>?.isBlank(): Boolean = this == null || isEmpty()

fun <T> Collection<T>?.isBlank(): Boolean = this == null || isEmpty()

fun <T> List<T>.midElement(): T {
    if (isEmpty())
        throw NoSuchElementException("List is empty.")
    return this[size / 2]
}

val <T> Collection<T>.midIndex: Int
    get() = if (size == 0) 0 else size / 2


fun <T> List<T>?.toMutable(): MutableList<T> {
    return (this ?: emptyList<T>()).toMutableList()
}