package example.extension

import android.app.Application
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.android.startKoin

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(myModule, viewModelModule, scope))
        Fabric.with(this, Crashlytics())
    }
}