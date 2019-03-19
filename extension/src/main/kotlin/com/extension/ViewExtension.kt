@file:JvmName("ViewUtils")
package com.extension

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.support.annotation.*
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.FloatingActionButton
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.*
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
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
var View.resString: Int
    @StringRes get() = resString
    set(value) {
        this.context?.resString(value).orEmpty()
    }


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
var View.setPaddingStart: Int
    @DimenRes get() = setPaddingStart
    set(value) {
        setPaddingRelative(value, paddingTop, paddingEnd, paddingBottom)
    }

/**
 * Extension method to set View's end padding.
 */
var View.setPaddingEnd: Int
    @DimenRes get() = setPaddingEnd
    set(value) {
        setPaddingRelative(paddingStart, paddingTop, value, paddingBottom)
    }
/**
 * Extension method to set View's top padding.
 */
var View.setPaddingTop: Int
    @DimenRes get() = setPaddingTop
    set(value) {
        setPaddingRelative(paddingStart, value, paddingEnd, paddingBottom)
    }
/**
 * Extension method to set View's bottom padding.
 */
var View.setPaddingBottom: Int
    @DimenRes get() = setPaddingBottom
    set(value) {
        setPaddingRelative(paddingStart, paddingTop, paddingEnd, value)
    }
/**
 * Extension method to set View's horizontal padding.
 */
var View.setPaddingHorizontal: Int
    @DimenRes get() = setPaddingHorizontal
    set(value) {
        setPaddingRelative(value, paddingTop, value, paddingBottom)
    }
/**
 * Extension method to set View's vertical padding.
 */
var View.setPaddingVertical: Int
    @DimenRes get() = setPaddingVertical
    set(value) {
        setPaddingRelative(paddingStart, value, paddingEnd, value)
    }
/**
 * Extension method to set View's height.
 */
var View.setHeight: Int
    @DimenRes get() = setHeight
    set(value) {
        val lp = layoutParams
        lp?.let {
            lp.height = value
            layoutParams = lp
        }
    }
/**
 * Extension method to set View's width.
 */
var View.setWidth: Int
    @DimenRes get() = setWidth
    set(value) {
        val lp = layoutParams
        lp?.let {
            lp.height = value
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
fun <T : View> T.click(block: T.() -> Unit) = setOnClickListener { block(it as T) }

/**
 * Extension method to set OnLongClickListener on a view.
 */
@Suppress("UNCHECKED_CAST")
fun <T : View> T.longClick(block: T.() -> Unit) = setOnLongClickListener {
    block(it as T)
    true
}

fun View.disable() {
    isEnabled = false
    alpha = 0.3f
}

fun View.enable() {
    isEnabled = true
    alpha = 1.0f
}

var View.backgroundTint: Int
    @ColorInt get() = backgroundTint
    @SuppressLint("RestrictedApi")
    set(value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (background != null) {
                backgroundTintList = context.resColorStateList(value)
            } else {
                setBackgroundColor(context.resColor(value))
            }
        } else {
            if (background != null) {
                when {
                    this is AppCompatAutoCompleteTextView -> supportBackgroundTintList = context.resColorStateList(value)
                    this is AppCompatTextView -> supportBackgroundTintList = context.resColorStateList(value)
                    this is AppCompatButton -> supportBackgroundTintList = context.resColorStateList(value)
                    this is AppCompatImageView -> supportBackgroundTintList = context.resColorStateList(value)
                    this is AppCompatMultiAutoCompleteTextView -> supportBackgroundTintList = context.resColorStateList(value)
                    this is AppCompatEditText -> supportBackgroundTintList = context.resColorStateList(value)
                    this is AppCompatImageButton -> supportBackgroundTintList = context.resColorStateList(value)
                    this is AppCompatSpinner -> supportBackgroundTintList = context.resColorStateList(value)
                    else -> DrawableCompat.setTintList(background, context.resColorStateList(value))
                }
            } else {
                setBackgroundColor(context.resColor(value))
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
var TextView.drawableStart: Int
    @DrawableRes get() = drawableStart
    set(value) {
        setCompoundDrawablesRelativeWithIntrinsicBounds(value, 0, 0, 0)
    }


/**
 * Extension method to set a drawable to the ens of a TextView.
 */
var TextView.drawableEnd: Int
    @DrawableRes get() = drawableEnd
    set(value) {
        setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, value, 0)
    }


fun TextView.toUpperCase() {
    text = value.toUpperCase()
}

fun TextView.toLowerCase() {
    text = value.toLowerCase()
}

var TextView.setTextSize: Float
    get() = setTextSize
    set(value) {
        setTextSize(TypedValue.COMPLEX_UNIT_PX, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, resources.displayMetrics))
    }

val TextView.value
    get() = text.toString()


var TextView.textColor: Int
    @ColorRes get() = textColor
    set(value) {
        this.setTextColor(this.context.resColor(value))
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

/*------------------------------------SwipeRefreshLayout-----------------------------------------------*/

var SwipeRefreshLayout.backgroundColor: Int
    @ColorRes get() = backgroundColor
    set(value) {
        setProgressBackgroundColorSchemeColor(this.context.resColor(value))
    }

/*------------------------------------BottomNavigationView-----------------------------------------------*/

val BottomNavigationView.selectedItem: Int
    get() {
    for (i in 0 until menu.size()) {
        if (menu.getItem(i).isChecked) {
            return i
        }
    }
    return 0
}

fun BottomNavigationView.itemSelect(f: MenuItem.() -> Boolean) {
    setOnNavigationItemSelectedListener {
        f(it)
    }
}

/**
 * Adds an [RecyclerView.OnScrollListener] to show or hide the FloatingActionButton when the RecyclerView scrolls up
 * or down respectively
 */
fun RecyclerView.bindFloatingActionButton(fab: FloatingActionButton) = this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy > 0 && fab.isShown) {
            fab.hide()
        } else if (dy < 0 && !fab.isShown) {
            fab.show()
        }
    }
})

fun <T : RecyclerView.ViewHolder> T.onClick(event: (position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event(adapterPosition, itemViewType)
    }
    return this
}

