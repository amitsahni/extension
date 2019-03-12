package com.extension

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v7.widget.AppCompatImageView
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import java.io.File

/*------------------------------------ImageView-----------------------------------------------*/

var ImageView.foregroundTint: Int
    @ColorRes get() = foregroundTint
    @SuppressLint("RestrictedApi")
    set(value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.imageTintList = context.resColorStateList(value)
        } else {
            (this as AppCompatImageView).supportImageTintList = context.resColorStateList(value)
        }
    }

@SuppressLint("CheckResult")
fun ImageView.load(image: String, @DrawableRes placeHolder: Int = -1) {
    val requestOptions = RequestOptions()
    requestOptions.placeholder(placeHolder)
    requestOptions.error(placeHolder)
    Glide.with(context.applicationContext)
            .asBitmap()
            .load(image)
            .apply(requestOptions)
            .into(this)
}

@SuppressLint("CheckResult")
fun ImageView.loadSkipCache(image: String, @DrawableRes placeHolder: Int = -1) {
    val requestOptions = RequestOptions()
    requestOptions.placeholder(placeHolder)
    requestOptions.error(placeHolder)
    requestOptions.skipMemoryCache(true)
    requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE)
    Glide.with(context.applicationContext)
            .asBitmap()
            .load(image)
            .apply(requestOptions)
            .into(this)
}

@SuppressLint("CheckResult")
fun ImageView.load(image: String, @DrawableRes placeHolder: Int = -1, f: Bitmap?.() -> Unit) {
    val requestOptions = RequestOptions()
    requestOptions.placeholder(placeHolder)
    requestOptions.error(placeHolder)
    Glide.with(context.applicationContext)
            .asBitmap()
            .load(image)
            .apply(requestOptions)
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                    f(null)
                    return false
                }

                override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    f(resource)
                    return true
                }

            })
            .into(this)
}

@SuppressLint("CheckResult")
fun ImageView.load(image: String, @DrawableRes placeHolder: Int = -1, transformations: Transformation<Bitmap>?, f: Bitmap?.() -> Unit) {
    val requestOptions = RequestOptions()
    requestOptions.placeholder(placeHolder)
    requestOptions.error(placeHolder)
    transformations?.also {
        requestOptions.transform(it)
    }
    Glide.with(context.applicationContext)
            .asBitmap()
            .load(image)
            .apply(requestOptions)
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                    f(null)
                    return false
                }

                override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    f(resource)
                    return true
                }

            })
            .into(this)
}

@SuppressLint("CheckResult")
fun Context.downloadBitmap(image: String, resizeHeight: Int = 405, resizeWidth: Int = 720, f: Bitmap?.() -> Unit) {
    Glide.with(applicationContext)
            .asBitmap()
            .load(image)
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                    f(null)
                    return false
                }

                override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    f(resource)
                    return true
                }
            })
            .into(object : SimpleTarget<Bitmap>(resizeHeight, resizeWidth) {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                }
            })
}

@SuppressLint("CheckResult")
fun Context.downloadFile(image: String, resizeHeight: Int = 405, resizeWidth: Int = 720, f: File?.() -> Unit) {
    Glide.with(applicationContext)
            .downloadOnly()
            .load(image)
            .listener(object : RequestListener<File> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<File>?, isFirstResource: Boolean): Boolean {
                    f(null)
                    return false
                }

                override fun onResourceReady(resource: File?, model: Any?, target: Target<File>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    f(resource)
                    return true
                }
            })
            .into(object : SimpleTarget<File>(resizeHeight, resizeWidth) {
                override fun onResourceReady(resource: File, transition: Transition<in File>?) {
                }
            })
}

fun Context.clearImageCache() {
    val glide = Glide.get(this)
    glide.clearMemory()
    object : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg voids: Void): Void? {
            glide.clearDiskCache()
            return null
        }
    }.execute()
}