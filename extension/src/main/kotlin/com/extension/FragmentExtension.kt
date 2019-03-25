@file:JvmName("FragmentUtils")
import android.app.Activity
import android.content.Intent
import androidx.annotation.*
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import android.view.View
import com.extension.*

/*------------------------------------Fragment---------------------------------------------*/

fun Fragment.resColor(@ColorRes colorRes: Int) = this.context?.resColor(colorRes)

fun Fragment.resString(@StringRes stringRes: Int) = this.context?.resString(stringRes).orEmpty()

fun Fragment.resString(@StringRes stringRes: Int, vararg formatArgs: Any?) =
        this.context?.resString(stringRes, formatArgs)

fun Fragment.resDrawable(@DrawableRes drawableRes: Int) = this.context?.resDrawable(drawableRes)

fun Fragment.resDimenPx(@DimenRes dimenRes: Int) = this.context?.resDimenPx(dimenRes)

fun Fragment.resInt(@IntegerRes intRes: Int) = this.context?.resInt(intRes)

fun Fragment.resBoolean(@BoolRes boolRes: Int) = this.context?.resBoolean(boolRes)

fun Fragment.resIntArray(@ArrayRes intArrRes: Int) = this.context?.resIntArray(intArrRes)

fun Fragment.resStrArray(@ArrayRes strArrRes: Int) = this.context?.resStrArray(strArrRes)

fun Fragment.hideSoftKeyboard() {
    activity?.hideSoftKeyboard()
}

inline fun <reified T : Any> Fragment.extra(key: String, default: T? = null) = lazy {
    val value = arguments?.get(key)
    if (value is T) value else default
}

inline fun <reified T : Activity> Fragment.startActivity(sharedElements: Pair<View, String>) {
    this.activity?.also {
        val intent = Intent(it, T::class.java)
        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(it, sharedElements)
        ContextCompat.startActivity(it, intent, optionsCompat.toBundle())
    }
}

inline fun <reified T : Activity> Fragment.startActivity(body: Intent.() -> Unit, sharedElements: Pair<View, String>) {
    this.activity?.also {
        val intent = Intent(it, T::class.java)
        intent.body()
        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(it, sharedElements)
        ContextCompat.startActivity(it, intent, optionsCompat.toBundle())
    }
}

inline fun <reified T : Activity> Fragment.startActivityForResult(resultCode: Int) {
    this.activity?.also {
        val intent = Intent(it, T::class.java)
        startActivityForResult(intent, resultCode)
    }
}

inline fun <reified T : Activity> Fragment.startActivityForResult(resultCode: Int, body: Intent.() -> Unit) {
    this.activity?.also {
        val intent = Intent(it, T::class.java)
        intent.body()
        startActivityForResult(intent, resultCode)
    }
}

inline fun <reified T : Activity> Fragment.startActivityForResult(resultCode: Int, sharedElements: Pair<View, String>) {
    this.activity?.also {
        val intent = Intent(it, T::class.java)
        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(it, sharedElements)
        startActivityForResult(intent, resultCode, optionsCompat.toBundle())
    }
}

inline fun <reified T : Activity> Fragment.startActivityForResult(resultCode: Int, sharedElements: Pair<View, String>, body: Intent.() -> Unit) {
    this.activity?.also {
        val intent = Intent(it, T::class.java)
        intent.body()
        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(it, sharedElements)
        startActivityForResult(intent, resultCode, optionsCompat.toBundle())
    }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun Fragment.getFragment(frameId: Int = -1): Fragment? {
    var containerId = frameId
    if (containerId != -1) {
        containerId = id
    }
    return this.activity?.supportFragmentManager?.findFragmentById(containerId)
}

fun Fragment.getFragment(tag: String? = ""): Fragment? {
    var value = this.tag
    tag.isEmptyOrNull {
        value = this
    }
    return this.activity?.supportFragmentManager?.findFragmentByTag(value)
}