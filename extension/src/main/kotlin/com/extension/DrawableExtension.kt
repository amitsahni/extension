@file:JvmName("DrawableUtils")
package com.extension

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.ColorInt
import android.support.v4.graphics.drawable.DrawableCompat

var Drawable.tint: Int
    @ColorInt get() = tint
    set(value) {
        DrawableCompat.setTint(DrawableCompat.wrap(this).mutate(), value)
    }

var Drawable.tintList: ColorStateList
    get() = tintList
    set(value) {
        DrawableCompat.setTintList(DrawableCompat.wrap(this).mutate(), value)
    }

fun Drawable.tint(@ColorInt color: Int) {
    val drawable = DrawableCompat.wrap(this).mutate()
    DrawableCompat.setTint(drawable, color)
    drawable.invalidateSelf()
}