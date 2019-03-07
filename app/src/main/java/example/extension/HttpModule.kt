package example.extension

import android.content.Context
import android.util.Log

class HttpModule(val context: Context) {
    init {
        Log.d(HttpModule::class.java.simpleName, "Context = $context")
    }

    fun init() {
        Log.d(HttpModule::class.java.simpleName, "----------------------------------")
        Log.d(HttpModule::class.java.simpleName, "Context = ${context.toString()}")
    }
}