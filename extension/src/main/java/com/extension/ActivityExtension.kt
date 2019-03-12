package com.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.MediaStore
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import inTransaction

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

inline fun <reified T : Activity> Activity.startActivity(sharedElements: Pair<View, String>) {
    val intent = Intent(this, T::class.java)
    val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedElements)
    ContextCompat.startActivity(this, intent, optionsCompat.toBundle())
}

inline fun <reified T : Activity> Activity.startActivity(body: Intent.() -> Unit, sharedElements: Pair<View, String>) {
    val intent = Intent(this, T::class.java)
    intent.body()
    val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedElements)
    ContextCompat.startActivity(this, intent, optionsCompat.toBundle())
}

inline fun <reified T : Activity> Activity.startActivityForResult(resultCode: Int) {
    val intent = Intent(this, T::class.java)
    startActivityForResult(intent, resultCode)
}

inline fun <reified T : Activity> Activity.startActivityForResult(resultCode: Int, body: Intent.() -> Unit) {
    val intent = Intent(this, T::class.java)
    intent.body()
    startActivityForResult(intent, resultCode)
}

inline fun <reified T : Activity> Activity.startActivityForResult(resultCode: Int, sharedElements: Pair<View, String>) {
    val intent = Intent(this, T::class.java)
    val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedElements)
    startActivityForResult(intent, resultCode, optionsCompat.toBundle())
}

inline fun <reified T : Activity> Activity.startActivityForResult(resultCode: Int, sharedElements: Pair<View, String>, body: Intent.() -> Unit) {
    val intent = Intent(this, T::class.java)
    intent.body()
    val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedElements)
    startActivityForResult(intent, resultCode, optionsCompat.toBundle())
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int, addToBackStack: Boolean = true) {
    supportFragmentManager?.inTransaction {
        val ft = add(frameId, fragment)
        if (addToBackStack) ft.addToBackStack(fragment.tag)
        ft
    }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int, addToBackStack: Boolean = true) {
    supportFragmentManager?.inTransaction {
        val ft = replace(frameId, fragment)
        if (addToBackStack) ft.addToBackStack(fragment.tag)
        ft
    }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int, vararg sharedElements: View, addToBackStack: Boolean = true) {
    supportFragmentManager?.inTransaction {
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
    supportFragmentManager?.inTransaction {
        val fragment = supportFragmentManager.findFragmentById(frameId)
        fragment?.also {
            remove(it)
        }
        this
    }
}

fun Activity.gallery(requestCode: Int) {
    val intent = Intent()
    intent.type = "image/*"
    intent.action = Intent.ACTION_GET_CONTENT
    startActivityForResult(intent, requestCode)
}

fun Activity.camera(requestCode: Int) {
    val intent = Intent(
            android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
    val imagePath = createImageFile("camera.png")
    val mUri = FileProvider.getUriForFile(this, packageName, imagePath)
    intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri)
    intent.putExtra("return-data", true)
    startActivityForResult(intent, requestCode)
}
