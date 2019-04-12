@file:JvmName("FragmentUtils")
import android.content.Intent
import android.support.annotation.*
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
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
    (activity as AppCompatActivity).hideSoftKeyboard()
}

val Fragment.activityCompat: AppCompatActivity
    get() {
        return this.activity as AppCompatActivity
    }

inline fun <reified T : Any> Fragment.extra(key: String, default: T? = null) = lazy {
    val value = arguments?.get(key)
    if (value is T) value else default
}

inline fun <reified T : AppCompatActivity> Fragment.startActivity() {
    this.activity?.also {
        val intent = Intent(it, T::class.java)
        ContextCompat.startActivity(it, intent, null)
    }
}

inline fun <reified T : AppCompatActivity> Fragment.startActivity(body: Intent.() -> Unit) {
    this.activity?.also {
        val intent = Intent(it, T::class.java)
        intent.body()
        ContextCompat.startActivity(it, intent, null)
    }
}

inline fun <reified T : AppCompatActivity> Fragment.startActivity(@AnimRes enterResId: Int = 0, @AnimRes exitResId: Int = 0) {
    this.activity?.also {
        val intent = Intent(it, T::class.java)
        val optionsCompat = ActivityOptionsCompat.makeCustomAnimation(it, enterResId, exitResId)
        ContextCompat.startActivity(it, intent, optionsCompat.toBundle())
    }
}

inline fun <reified T : AppCompatActivity> Fragment.startActivity(@AnimRes enterResId: Int = 0, @AnimRes exitResId: Int = 0,
                                                                  body: Intent.() -> Unit) {
    this.activity?.also {
        val intent = Intent(it, T::class.java)
        intent.body()
        val optionsCompat = ActivityOptionsCompat.makeCustomAnimation(it, enterResId, exitResId)
        ContextCompat.startActivity(it, intent, optionsCompat.toBundle())
    }
}

inline fun <reified T : AppCompatActivity> Fragment.startActivity(sharedElements: Pair<View, String>) {
    this.activity?.also {
        val intent = Intent(it, T::class.java)
        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(it, sharedElements)
        ContextCompat.startActivity(it, intent, optionsCompat.toBundle())
    }
}

inline fun <reified T : AppCompatActivity> Fragment.startActivity(body: Intent.() -> Unit, sharedElements: Pair<View, String>) {
    this.activity?.also {
        val intent = Intent(it, T::class.java)
        intent.body()
        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(it, sharedElements)
        ContextCompat.startActivity(it, intent, optionsCompat.toBundle())
    }
}

inline fun <reified T : AppCompatActivity> Fragment.startActivityForResult(resultCode: Int) {
    this.activity?.also {
        val intent = Intent(it, T::class.java)
        startActivityForResult(intent, resultCode)
    }
}

inline fun <reified T : AppCompatActivity> Fragment.startActivityForResult(resultCode: Int, body: Intent.() -> Unit) {
    this.activity?.also {
        val intent = Intent(it, T::class.java)
        intent.body()
        startActivityForResult(intent, resultCode)
    }
}

inline fun <reified T : AppCompatActivity> Fragment.startActivityForResult(resultCode: Int, sharedElements: Pair<View, String>) {
    this.activity?.also {
        val intent = Intent(it, T::class.java)
        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(it, sharedElements)
        startActivityForResult(intent, resultCode, optionsCompat.toBundle())
    }
}

inline fun <reified T : AppCompatActivity> Fragment.startActivityForResult(resultCode: Int, sharedElements: Pair<View, String>, body: Intent.() -> Unit) {
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