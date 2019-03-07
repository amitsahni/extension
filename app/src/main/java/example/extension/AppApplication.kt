package example.extension

import android.app.Application
import org.koin.android.ext.android.startKoin

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(myModule, viewModelModule, scope))
    }
}