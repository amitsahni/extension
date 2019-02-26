package example.extension

import com.activity.ActivityManager
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

// just declare it
val myModule = module() {
    single { ActivityManager.with(get()) }
    factory("factory") { HttpModule(get()) }
    scope("mainActivity", "http") { HttpModule(get()) }

}

val viewModelModule = module {
    this.viewModel { AppViewModel(get()) }
}