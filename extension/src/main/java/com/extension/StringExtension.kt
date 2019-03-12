package com.extension

import android.text.Editable
import android.util.Patterns
import com.google.gson.GsonBuilder
import org.json.JSONArray
import org.json.JSONObject


object G {
    val gson = GsonBuilder()
        .disableHtmlEscaping()
        .setPrettyPrinting()
        .create()
}

/**
 * Converts string to integer safely otherwise returns zero
 */
fun String.asInt(): Int = toInt().or(-1)

fun String.asBoolean(): Boolean = toBoolean().or(false)

fun String.toCamelCase(): String {
    var titleText = ""
    if (!this.isEmpty()) {
        val words = this.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        words.filterNot { it.isEmpty() }
            .map { it.substring(0, 1).toUpperCase() + it.substring(1).toLowerCase() }
            .forEach { titleText += "$it " }
    }
    return titleText.trim { it <= ' ' }
}

fun String?.isEmptyOrNull(string: String.() -> Unit) {
    this?.let {
        if (!it.isEmpty()) {
            string()
        }
    }
}

/**
 * Extension method to check if String is Number.
 */
fun String.isNumeric(): Boolean = matches("^[0-9]+$".toRegex())

/**
 * Extension method to check if String is Email.
 */
fun String.isEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()

/**
 * Extension method to check if String is Email.
 */
fun String.isUrl(): Boolean = Patterns.WEB_URL.matcher(this).matches()

fun join(vararg params: Any?) = params.joinToString()

fun Any.toJson(): String = try {
    G.gson.toJson(this)
} catch (e: Exception) {
    ""
}

inline fun <reified T : Any> String?.fromJson() = G.gson.fromJson<T>(this, T::class.java)

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

fun Int.times(predicate: (Int) -> Unit) = repeat(this, predicate)

val String.isJson: Boolean
    get() {
        this.trim { it <= ' ' }
        if (this.startsWith("{")) {
            val jsonObject = JSONObject(this)
            val message = jsonObject.toString(2)
            return true
        }
        if (this.startsWith("[")) {
            val jsonArray = JSONArray(this)
            val message = jsonArray.toString(2)
            return true
        }
        return false
    }





