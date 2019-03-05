package com.extension

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.annotation.*
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager

/*------------------------------------Context---------------------------------------------*/

fun Context.resColor(@ColorRes colorRes: Int) = ContextCompat.getColor(this, colorRes)

fun Context.resColorStateList(@ColorRes colorRes: Int) = ContextCompat.getColorStateList(this, colorRes)

fun Context.resString(@StringRes stringRes: Int) = getString(stringRes)

fun Context.resString(@StringRes stringRes: Int, vararg formatArgs: Any?) =
    getString(stringRes, formatArgs)

fun Context.resDrawable(@DrawableRes drawableRes: Int) = ContextCompat.getDrawable(this, drawableRes)

fun Context.resDimenPx(@DimenRes dimenRes: Int) = this.resources.getDimensionPixelSize(dimenRes)

fun Context.resInt(@IntegerRes intRes: Int) = this.resources.getInteger(intRes)

fun Context.resBoolean(@BoolRes boolRes: Int) = this.resources.getBoolean(boolRes)

fun Context.resIntArray(@ArrayRes intArrRes: Int) = this.resources.getIntArray(intArrRes)

fun Context.resStrArray(@ArrayRes strArrRes: Int) = this.resources.getStringArray(strArrRes)

fun Context.inflateLayout(@LayoutRes layoutId: Int, parent: ViewGroup? = null, attachToRoot: Boolean = false): View =
    LayoutInflater.from(this).inflate(layoutId, parent, attachToRoot)

inline fun <reified T : Activity> Context.intent() = Intent(this, T::class.java)

inline fun <reified T : Activity> Context.intent(body: Intent.() -> Unit): Intent {
    val intent = Intent(this, T::class.java)
    intent.body()
    return intent
}

inline fun <reified T : Activity> Context.startActivity(bundle: Bundle = Bundle(), flag: Int = Intent.FLAG_ACTIVITY_NEW_TASK) {
    val intent = Intent(this, T::class.java)
    intent.putExtras(bundle)
    intent.addFlags(flag)
    ContextCompat.startActivity(this, intent, Bundle())
}

inline fun <reified T : Activity> Context.startActivity(enterResId: Int = 0, exitResId: Int = 0,
                                                        bundle: Bundle = Bundle(), flag: Int = Intent.FLAG_ACTIVITY_NEW_TASK) {
    val intent = Intent(this, T::class.java)
    intent.putExtras(bundle)
    intent.addFlags(flag)
    val optionsCompat = ActivityOptionsCompat.makeCustomAnimation(this, enterResId, exitResId)
    ContextCompat.startActivity(this, intent, optionsCompat.toBundle())
}

fun Context.share(text: String, subject: String = ""): Boolean {
    val intent = Intent()
    intent.type = "text/plain"
    intent.putExtra(EXTRA_SUBJECT, subject)
    intent.putExtra(EXTRA_TEXT, text)
    return try {
        startActivity(createChooser(intent, null))
        true
    } catch (e: ActivityNotFoundException) {
        false
    }
}

fun Context.email(vararg email: String, subject: String = "", text: String = ""): Boolean {
    val intent = Intent(ACTION_SENDTO)
    intent.data = Uri.parse("mailto:")
    intent.putExtra(EXTRA_EMAIL, arrayOf(email))
    if (subject.isNotBlank()) intent.putExtra(EXTRA_SUBJECT, subject)
    if (text.isNotBlank()) intent.putExtra(EXTRA_TEXT, text)
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
        return true
    }
    return false
}

fun Context.browse(url: String, newTask: Boolean = false): Boolean {
    val intent = Intent(ACTION_VIEW)
    intent.data = Uri.parse(url)
    if (newTask) intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
    return try {
        startActivity(intent)
        true
    } catch (e: Exception) {
        false
    }
}

fun Context.dial(tel: String?) = startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$tel")))

fun Context.rate(): Boolean =
    browse("market://details?id=$packageName") or browse("http://play.google.com/store/apps/details?id=$packageName")


fun runOnUiThread(f: () -> Unit) {
    val handler = Handler(Looper.getMainLooper())
    handler.post {
        f()
    }
}

/**
 * Convert dp integer to pixel
 */
fun Context.dpToPx(dp: Int): Float {
    val displayMetrics = this.resources.displayMetrics
    return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).toFloat()
}

/*------------------------------------Fragment---------------------------------------------*/

fun Fragment.resColor(@ColorRes colorRes: Int) = this.context!!.resColor(colorRes)

fun Fragment.resString(@StringRes stringRes: Int) = this.context!!.resString(stringRes).orEmpty()

fun Fragment.resString(@StringRes stringRes: Int, vararg formatArgs: Any?) =
    this.context!!.resString(stringRes, formatArgs).orEmpty()

fun Fragment.resDrawable(@DrawableRes drawableRes: Int) = this.context!!.resDrawable(drawableRes)

fun Fragment.resDimenPx(@DimenRes dimenRes: Int) = this.context!!.resDimenPx(dimenRes).or(-1)

fun Fragment.resInt(@IntegerRes intRes: Int) = this.context!!.resInt(intRes).or(-1)

fun Fragment.resBoolean(@BoolRes boolRes: Int) = this.context!!.resBoolean(boolRes).or(false)

fun Fragment.resIntArray(@ArrayRes intArrRes: Int) = this.context!!.resIntArray(intArrRes)

fun Fragment.resStrArray(@ArrayRes strArrRes: Int) = this.context!!.resStrArray(strArrRes).orEmpty()

fun Fragment.hideSoftKeyboard() {
    activity?.hideSoftKeyboard()
}

inline fun <reified T : Any> Fragment.extra(key: String, default: T? = null) = lazy {
    val value = arguments?.get(key)
    if (value is T) value else default
}

inline fun <reified T : Activity> Fragment.startActivity(sharedElements: Pair<View, String>, bundle: Bundle = Bundle(),
                                                         flag: Int = Intent.FLAG_ACTIVITY_NEW_TASK) {
    this.activity?.also {
        val intent = Intent(this.context, T::class.java)
        intent.putExtras(bundle).addFlags(flag)
        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(it, sharedElements)
        ContextCompat.startActivity(this.context!!, intent, optionsCompat.toBundle())
    }
}

inline fun <reified T : Activity> Fragment.startActivityForResult(resultCode: Int, bundle: Bundle = Bundle(), flag: Int = Intent.FLAG_ACTIVITY_NEW_TASK) {
    this.context?.also {
        val intent = Intent(it, T::class.java)
        intent.putExtras(bundle).addFlags(flag)
        startActivityForResult(intent, resultCode)
    }
}


inline fun <reified T : Activity> Fragment.startActivityForResult(resultCode: Int, sharedElements: Pair<View, String>,
                                                                  bundle: Bundle = Bundle(), flag: Int = Intent.FLAG_ACTIVITY_NEW_TASK) {
    this.activity?.also {
        val intent = Intent(this.context, T::class.java)
        intent.putExtras(bundle).addFlags(flag)
        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(it, sharedElements)
        startActivityForResult(intent, resultCode, optionsCompat.toBundle())
    }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun Fragment.addFragment(fragment: Fragment, frameId: Int, addToBackStack: Boolean = true) {
    this.activity?.supportFragmentManager?.inTransaction {
        val ft = add(frameId, fragment)
        if (addToBackStack) ft.addToBackStack(fragment.tag)
        ft
    }
}

fun Fragment.replaceFragment(fragment: Fragment, frameId: Int, addToBackStack: Boolean = true) {
    this.activity?.supportFragmentManager?.inTransaction {
        val ft = replace(frameId, fragment)
        if (addToBackStack) ft.addToBackStack(fragment.tag)
        ft
    }
}

fun Fragment.replaceFragment(fragment: Fragment, frameId: Int, vararg sharedElements: View, addToBackStack: Boolean = true) {
    this.activity?.supportFragmentManager?.inTransaction {
        val ft = replace(frameId, fragment)
        if (addToBackStack) ft.addToBackStack(fragment.tag)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for (view in sharedElements) {
                ft.addSharedElement(view, view.transitionName)
            }
        }
        ft
    }
}
/*------------------------------------Activity---------------------------------------------*/


fun Activity.hideSoftKeyboard() {
    currentFocus?.let {
        val inputMethodManager = getSystemService(
            Context
                .INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }
}

inline fun <reified T : Any> Activity.extra(key: String, default: T? = null) = lazy {
    val value = intent?.extras?.get(key)
    if (value is T) value else default
}

inline fun <reified T : Activity> Activity.startActivity(bundle: Bundle = Bundle(), sharedElements: Pair<View, String>,
                                                         flag: Int = Intent.FLAG_ACTIVITY_NEW_TASK) {
    val intent = Intent(this, T::class.java)
    intent.putExtras(bundle).addFlags(flag)
    val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedElements)
    ContextCompat.startActivity(this, intent, optionsCompat.toBundle())
}

inline fun <reified T : Activity> Activity.startActivityForResult(resultCode: Int, bundle: Bundle = Bundle(), flag: Int = Intent.FLAG_ACTIVITY_NEW_TASK) {
    val intent = Intent(this, T::class.java)
    intent.putExtras(bundle).addFlags(flag)
    startActivityForResult(intent, resultCode)
}


inline fun <reified T : Activity> Activity.startActivityForResult(resultCode: Int, sharedElements: Pair<View, String>, bundle: Bundle = Bundle(),
                                                                  flag: Int = Intent.FLAG_ACTIVITY_NEW_TASK) {
    val intent = Intent(this, T::class.java)
    intent.putExtras(bundle).addFlags(flag)
    val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedElements)
    startActivityForResult(intent, resultCode, optionsCompat.toBundle())
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int, addToBackStack: Boolean = true) {
    supportFragmentManager.inTransaction {
        val ft = add(frameId, fragment)
        if (addToBackStack) ft.addToBackStack(fragment.tag)
        ft
    }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int, addToBackStack: Boolean = true) {
    supportFragmentManager.inTransaction {
        val ft = replace(frameId, fragment)
        if (addToBackStack) ft.addToBackStack(fragment.tag)
        ft
    }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int, vararg sharedElements: View, addToBackStack: Boolean = true) {
    supportFragmentManager.inTransaction {
        val ft = replace(frameId, fragment)
        if (addToBackStack) ft.addToBackStack(fragment.tag)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for (view in sharedElements) {
                ft.addSharedElement(view, view.transitionName)
            }
        }
        ft
    }
}

