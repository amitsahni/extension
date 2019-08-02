@file:JvmName("FragmentUtils")
import android.content.Intent
import androidx.annotation.*
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.extension.*

/*------------------------------------Fragment---------------------------------------------*/

fun androidx.fragment.app.Fragment.resColor(@ColorRes colorRes: Int) = this.context?.resColor(colorRes)

fun androidx.fragment.app.Fragment.resString(@StringRes stringRes: Int) = this.context?.resString(stringRes).orEmpty()

fun androidx.fragment.app.Fragment.resString(@StringRes stringRes: Int, vararg formatArgs: Any) =
        this.context?.resString(stringRes, formatArgs)

fun androidx.fragment.app.Fragment.resDrawable(@DrawableRes drawableRes: Int) = this.context?.resDrawable(drawableRes)

fun androidx.fragment.app.Fragment.resDimenPx(@DimenRes dimenRes: Int) = this.context?.resDimenPx(dimenRes)

fun androidx.fragment.app.Fragment.resInt(@IntegerRes intRes: Int) = this.context?.resInt(intRes)

fun androidx.fragment.app.Fragment.resBoolean(@BoolRes boolRes: Int) = this.context?.resBoolean(boolRes)

fun androidx.fragment.app.Fragment.resIntArray(@ArrayRes intArrRes: Int) = this.context?.resIntArray(intArrRes)

fun androidx.fragment.app.Fragment.resStrArray(@ArrayRes strArrRes: Int) = this.context?.resStrArray(strArrRes)

fun androidx.fragment.app.Fragment.hideSoftKeyboard() {
    (activity as AppCompatActivity).hideSoftKeyboard()
}

val androidx.fragment.app.Fragment.activityCompat: AppCompatActivity
    get() {
        return this.activity as AppCompatActivity
    }

inline fun <reified T : Any> androidx.fragment.app.Fragment.extra(key: String, default: T? = null) = lazy {
    val value = arguments?.get(key)
    if (value is T) value else default
}

inline fun <reified T : AppCompatActivity> androidx.fragment.app.Fragment.startActivity() {
    this.activity?.also {
        val intent = Intent(it, T::class.java)
        ContextCompat.startActivity(it, intent, null)
    }
}

inline fun <reified T : AppCompatActivity> androidx.fragment.app.Fragment.startActivity(body: Intent.() -> Unit) {
    this.activity?.also {
        val intent = Intent(it, T::class.java)
        intent.body()
        ContextCompat.startActivity(it, intent, null)
    }
}

inline fun <reified T : AppCompatActivity> androidx.fragment.app.Fragment.startActivity(@AnimRes enterResId: Int = 0, @AnimRes exitResId: Int = 0) {
    this.activity?.also {
        val intent = Intent(it, T::class.java)
        val optionsCompat = ActivityOptionsCompat.makeCustomAnimation(it, enterResId, exitResId)
        ContextCompat.startActivity(it, intent, optionsCompat.toBundle())
    }
}

inline fun <reified T : AppCompatActivity> androidx.fragment.app.Fragment.startActivity(@AnimRes enterResId: Int = 0, @AnimRes exitResId: Int = 0,
                                                                                        body: Intent.() -> Unit) {
    this.activity?.also {
        val intent = Intent(it, T::class.java)
        intent.body()
        val optionsCompat = ActivityOptionsCompat.makeCustomAnimation(it, enterResId, exitResId)
        ContextCompat.startActivity(it, intent, optionsCompat.toBundle())
    }
}

inline fun <reified T : AppCompatActivity> androidx.fragment.app.Fragment.startActivity(sharedElements: Pair<View, String>) {
    this.activity?.also {
        val intent = Intent(it, T::class.java)
        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(it, sharedElements)
        ContextCompat.startActivity(it, intent, optionsCompat.toBundle())
    }
}

inline fun <reified T : AppCompatActivity> androidx.fragment.app.Fragment.startActivity(body: Intent.() -> Unit, sharedElements: Pair<View, String>) {
    this.activity?.also {
        val intent = Intent(it, T::class.java)
        intent.body()
        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(it, sharedElements)
        ContextCompat.startActivity(it, intent, optionsCompat.toBundle())
    }
}

inline fun <reified T : AppCompatActivity> androidx.fragment.app.Fragment.startActivityForResult(resultCode: Int) {
    this.activity?.also {
        val intent = Intent(it, T::class.java)
        startActivityForResult(intent, resultCode)
    }
}

inline fun <reified T : AppCompatActivity> androidx.fragment.app.Fragment.startActivityForResult(resultCode: Int, body: Intent.() -> Unit) {
    this.activity?.also {
        val intent = Intent(it, T::class.java)
        intent.body()
        startActivityForResult(intent, resultCode)
    }
}

inline fun <reified T : AppCompatActivity> androidx.fragment.app.Fragment.startActivityForResult(resultCode: Int, sharedElements: Pair<View, String>) {
    this.activity?.also {
        val intent = Intent(it, T::class.java)
        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(it, sharedElements)
        startActivityForResult(intent, resultCode, optionsCompat.toBundle())
    }
}

inline fun <reified T : AppCompatActivity> androidx.fragment.app.Fragment.startActivityForResult(resultCode: Int, sharedElements: Pair<View, String>, body: Intent.() -> Unit) {
    this.activity?.also {
        val intent = Intent(it, T::class.java)
        intent.body()
        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(it, sharedElements)
        startActivityForResult(intent, resultCode, optionsCompat.toBundle())
    }
}

inline fun androidx.fragment.app.FragmentManager.inTransaction(func: androidx.fragment.app.FragmentTransaction.() -> androidx.fragment.app.FragmentTransaction) {
    beginTransaction().func().commit()
}

fun androidx.fragment.app.Fragment.getFragment(frameId: Int = -1): androidx.fragment.app.Fragment? {
    var containerId = frameId
    if (containerId != -1) {
        containerId = id
    }
    return this.activity?.supportFragmentManager?.findFragmentById(containerId)
}

fun androidx.fragment.app.Fragment.getFragment(tag: String? = ""): androidx.fragment.app.Fragment? {
    var value = this.tag
    tag.isEmptyOrNull {
        value = this
    }
    return this.activity?.supportFragmentManager?.findFragmentByTag(value)
}