package com.extension

import android.app.Dialog
import android.content.Context
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
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

fun AlertDialog.singleChoice(buttonText: String, f: () -> Unit) {
    setButton(AlertDialog.BUTTON_POSITIVE, buttonText) { dialog, which ->
        f()
        dialog.dismiss()
    }
    this.show()
}

fun AlertDialog.doubleChoice(positiveButtonText: String, negativeButtonText: String, f: (Int) -> Unit) {
    setButton(AlertDialog.BUTTON_POSITIVE, positiveButtonText) { dialog, _ ->
        f(AlertDialog.BUTTON_POSITIVE)
        dialog.dismiss()
    }
    setButton(AlertDialog.BUTTON_NEGATIVE, negativeButtonText) { dialog, _ ->
        f(AlertDialog.BUTTON_NEGATIVE)
        dialog.dismiss()
    }
    show()
}

fun View.snackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

fun View.snackBar(message: String, actionMessage: String, f: View.() -> Unit) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
            .setAction(actionMessage) {
                f()
            }.show()
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
