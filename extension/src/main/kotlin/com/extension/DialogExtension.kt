@file:JvmName("DialogUtils")
package com.extension

import android.app.Dialog
import android.content.Context
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AlertDialog
import android.view.View
import android.view.Window
import android.widget.Toast

fun Dialog.customView(@LayoutRes layoutId: Int, isCancelable: Boolean = true) {
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    setContentView(layoutId)
    setCancelable(isCancelable)
}

fun Dialog.customView(view: View, isCancelable: Boolean = true) {
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    setContentView(view)
    setCancelable(isCancelable)
}

fun Context.alert(message: String = "", f: AlertDialog.Builder.() -> Unit) {
    val dialog = AlertDialog.Builder(this)
    dialog.setMessage(message)
    f(dialog)
    dialog.show()
}

fun Context.alert(message: String = "", @StyleRes themeResId: Int, f: AlertDialog.Builder.() -> Unit) {
    val dialog = AlertDialog.Builder(this, themeResId)
    dialog.setMessage(message)
    f(dialog)
    dialog.show()
}

fun View.snackBar(message: String) = Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()

fun View.snackBar(message: String, actionMessage: String, f: View.() -> Unit) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
            .setAction(actionMessage) {
                f()
            }.show()
}

fun View.longSnackBar(message: String) = Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()

fun View.longSnackBar(message: String, actionMessage: String, f: View.() -> Unit) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG)
            .setAction(actionMessage) {
                f()
            }.show()
}

fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.toast(@StringRes resId: Int) = Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()

fun Context.longToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun Context.longToast(@StringRes resId: Int) = Toast.makeText(this, resId, Toast.LENGTH_LONG).show()



