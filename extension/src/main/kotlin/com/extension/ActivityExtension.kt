@file:JvmName("ActivityUtils")
package com.extension

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Parcelable
import android.provider.MediaStore
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.util.Pair
import inTransaction


/*------------------------------------Activity---------------------------------------------*/


fun AppCompatActivity.hideSoftKeyboard() {
    currentFocus?.let {
        val inputMethodManager = getSystemService(
                Context
                        .INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }
}

fun AppCompatActivity.showSoftKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

inline fun <reified T : Any> AppCompatActivity.extra(key: String, default: T) = lazy {
    val value = intent?.extras?.get(key)
    if (value is T) value else default
}

inline fun <reified T : Any> AppCompatActivity.extraSerializable(key: String, default: T) = lazy {
    val value = intent?.extras?.getSerializable(key)
    if (value is T) value else default
}

inline fun <reified T : Parcelable> AppCompatActivity.extraParcelable(key: String, default: T) = lazy {
    val value = intent?.extras?.getParcelable(key) ?: default
    value
}

inline fun <reified T : AppCompatActivity> AppCompatActivity.startActivity(sharedElements: Pair<View, String>) {
    val intent = Intent(this, T::class.java)
    val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedElements)
    ContextCompat.startActivity(this, intent, optionsCompat.toBundle())
}

inline fun <reified T : AppCompatActivity> AppCompatActivity.startActivity(body: Intent.() -> Unit, sharedElements: Pair<View, String>) {
    val intent = Intent(this, T::class.java)
    intent.body()
    val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedElements)
    ContextCompat.startActivity(this, intent, optionsCompat.toBundle())
}

inline fun <reified T : AppCompatActivity> AppCompatActivity.startActivityForResult(resultCode: Int) {
    val intent = Intent(this, T::class.java)
    startActivityForResult(intent, resultCode)
}

inline fun <reified T : AppCompatActivity> AppCompatActivity.startActivityForResult(resultCode: Int, body: Intent.() -> Unit) {
    val intent = Intent(this, T::class.java)
    intent.body()
    startActivityForResult(intent, resultCode)
}

inline fun <reified T : AppCompatActivity> AppCompatActivity.startActivityForResult(resultCode: Int, sharedElements: Pair<View, String>) {
    val intent = Intent(this, T::class.java)
    val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedElements)
    startActivityForResult(intent, resultCode, optionsCompat.toBundle())
}

inline fun <reified T : AppCompatActivity> AppCompatActivity.startActivityForResult(resultCode: Int, sharedElements: Pair<View, String>, body: Intent.() -> Unit) {
    val intent = Intent(this, T::class.java)
    intent.body()
    val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedElements)
    startActivityForResult(intent, resultCode, optionsCompat.toBundle())
}

fun AppCompatActivity.addFragment(fragment: androidx.fragment.app.Fragment, frameId: Int, addToBackStack: Boolean = true) {
    supportFragmentManager.inTransaction {
        val ft = add(frameId, fragment)
        if (addToBackStack) ft.addToBackStack(fragment.tag)
        ft
    }
}

fun AppCompatActivity.replaceFragment(fragment: androidx.fragment.app.Fragment, frameId: Int, addToBackStack: Boolean = true) {
    supportFragmentManager.inTransaction {
        val ft = replace(frameId, fragment)
        if (addToBackStack) ft.addToBackStack(fragment.tag)
        ft
    }
}

fun AppCompatActivity.replaceFragment(fragment: androidx.fragment.app.Fragment, frameId: Int, vararg sharedElements: View, addToBackStack: Boolean = true) {
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

fun AppCompatActivity.popFragment(frameId: Int) {
    supportFragmentManager.inTransaction {
        val fragment = supportFragmentManager.findFragmentById(frameId)
        fragment?.also {
            remove(it)
        }
        this
    }
}

fun AppCompatActivity.gallery(requestCode: Int) {
    val intent = Intent()
    intent.type = "image/*"
    intent.action = Intent.ACTION_GET_CONTENT
    startActivityForResult(intent, requestCode)
}

fun AppCompatActivity.camera(requestCode: Int) {
    val intent = Intent(
            android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
    val imagePath = createImageFile("camera.png")
    val mUri = FileProvider.getUriForFile(this, packageName, imagePath)
    intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri)
    intent.putExtra("return-data", true)
    startActivityForResult(intent, requestCode)
}
