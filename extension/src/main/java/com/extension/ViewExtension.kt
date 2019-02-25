package com.extension

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.support.annotation.ColorRes
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView


/*------------------------------------View---------------------------------------------*/

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

/**
 * Toggle a view's visibility
 */
fun View.toggleVisibility() {
    visibility = if (visibility == View.VISIBLE) {
        View.INVISIBLE
    } else {
        View.VISIBLE
    }
}


/**
 * Extension method to provide simpler access to {@link View#getResources()#getString(int)}.
 */
fun View.resString(stringResId: Int): String = this.context?.resString(stringResId).orEmpty()

/**
 * Extension method to show a keyboard for View.
 */
fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}

/**
 * Try to hide the keyboard and returns whether it worked
 */
fun View.hideKeyboard(): Boolean {
    return try {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) {
        false
    }

}

/**
 * Extension method to remove the required boilerplate for running code after a view has been
 * inflated and measured.
 */
inline fun <T : View> T.afterMeasured(crossinline f: T.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                f()
            }
        }
    })
}

/**
 * Extension method to set View's start padding.
 */
fun View.setPaddingStart(value: Int) = setPaddingRelative(value, paddingTop, paddingEnd, paddingBottom)

/**
 * Extension method to set View's end padding.
 */
fun View.setPaddingEnd(value: Int) = setPaddingRelative(paddingStart, paddingTop, value, paddingBottom)

/**
 * Extension method to set View's top padding.
 */
fun View.setPaddingTop(value: Int) = setPaddingRelative(paddingStart, value, paddingEnd, paddingBottom)

/**
 * Extension method to set View's bottom padding.
 */
fun View.setPaddingBottom(value: Int) = setPaddingRelative(paddingStart, paddingTop, paddingEnd, value)

/**
 * Extension method to set View's horizontal padding.
 */
fun View.setPaddingHorizontal(value: Int) = setPaddingRelative(value, paddingTop, value, paddingBottom)

/**
 * Extension method to set View's vertical padding.
 */
fun View.setPaddingVertical(value: Int) = setPaddingRelative(paddingStart, value, paddingEnd, value)

/**
 * Extension method to set View's height.
 */
fun View.setHeight(value: Int) {
    val lp = layoutParams
    lp?.let {
        lp.height = value
        layoutParams = lp
    }
}

/**
 * Extension method to set View's width.
 */
fun View.setWidth(value: Int) {
    val lp = layoutParams
    lp?.let {
        lp.width = value
        layoutParams = lp
    }
}

/**
 * Extension method to resize View with height & width.
 */
fun View.resize(width: Int, height: Int) {
    val lp = layoutParams
    lp?.let {
        lp.width = width
        lp.height = height
        layoutParams = lp
    }
}

/**
 * Set an onclick listener
 */
@Suppress("UNCHECKED_CAST")
fun <T : View> T.click(block: (T) -> Unit) = setOnClickListener { block(it as T) }

/**
 * Extension method to set OnLongClickListener on a view.
 */
@Suppress("UNCHECKED_CAST")
fun <T : View> T.longClick(block: (T) -> Boolean) = setOnLongClickListener { block(it as T) }

fun View.disable() {
    isEnabled = false
    alpha = 0.3f
}

fun View.enable() {
    isEnabled = true
    alpha = 1.0f
}

fun View.backgroundTint(@ColorRes resId: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        if (background != null) {
            backgroundTintList = context.resColorStateList(resId)
        } else {
            setBackgroundColor(context.resColor(resId))
        }
    } else {
        if (background != null) {
            DrawableCompat.setTintList(background, context.resColorStateList(resId))
        } else {
            setBackgroundColor(context.resColor(resId))
        }

    }
}


/*------------------------------------ViewGroup-----------------------------------------------*/

inline fun ViewGroup.forEach(action: (View) -> Unit) {
    for (index in 0 until childCount) {
        action(getChildAt(index))
    }
}

/*------------------------------------TextView-----------------------------------------------*/

/**
 * Extension method to set a drawable to the start of a TextView.
 */
fun TextView.setDrawableStart(drawable: Int) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, 0, 0, 0)
}

/**
 * Extension method to set a drawable to the ens of a TextView.
 */
fun TextView.setDrawableEnd(drawable: Int) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, drawable, 0)
}


fun TextView.toUpperCase() {
    text = value.toUpperCase()
}

fun TextView.toLowerCase() {
    text = value.toLowerCase()
}

fun TextView.textSize(px: Float) {
    setTextSize(TypedValue.COMPLEX_UNIT_PX, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, px, resources.displayMetrics))
}

val TextView.value
    get() = text.toString()


@SuppressLint("RestrictedApi")
fun TextView.backgroundTint(@ColorRes resId: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        if (background != null) {
            backgroundTintList = context.resColorStateList(resId)
        } else {
            setBackgroundColor(context.resColor(resId))
        }
    } else {
        if (background != null) {
            (this as AppCompatTextView).supportBackgroundTintList = context.resColorStateList(resId)
        } else {
            setBackgroundColor(context.resColor(resId))
        }

    }
}

/*------------------------------------EditText-----------------------------------------------*/

inline fun EditText.afterTextChanged(crossinline afterTextChanged: (Editable?) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable)
        }
    })
}

inline fun EditText.onTextChanged(crossinline onTextChanged: (CharSequence?, Int, Int, Int) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged.invoke(p0, start, before, count)
        }

        override fun afterTextChanged(editable: Editable?) {
        }
    })
}

inline fun EditText.beforeTextChanged(crossinline beforeTextChanged: (CharSequence?, Int, Int, Int) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            beforeTextChanged.invoke(s, start, count, after)
        }


        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
        }
    })
}
/*------------------------------------ImageView-----------------------------------------------*/

@SuppressLint("RestrictedApi")
fun ImageView.foregroundTint(@ColorRes resId: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        this.imageTintList = context.resColorStateList(resId)
    } else {
        (this as AppCompatImageView).supportImageTintList = context.resColorStateList(resId)

    }
}

/*------------------------------------SwipeRefreshLayout-----------------------------------------------*/

fun SwipeRefreshLayout.backgroundColor(@ColorRes resId: Int) {
    setProgressBackgroundColorSchemeColor(this.context.resColor(resId))
}

