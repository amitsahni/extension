package example.extension

import android.util.Log

class AbcModule() {
//    val components: Components by lazy {
//        Components()
//    }

    fun init() {
        Log.d(AbcModule::class.java.simpleName, "----------------------------------")
        //Log.d(AbcModule::class.java.simpleName, "Context = ${components.context}")
    }
}