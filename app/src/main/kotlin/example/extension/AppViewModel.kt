package example.extension

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.util.Log

// Injected by constructor
class AppViewModel(application: Application) : AndroidViewModel(application) {

    fun init() {

        Log.d(AppViewModel::class.java.simpleName, "Context = ${getApplication<Application>()}")
    }
}