package example.extension

import androidx.appcompat.app.AlertDialog
import com.activity.ActivityManager
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

// just declare it
val myModule = module {
    single { ActivityManager.with(get()) }
    single { AlertDialog.Builder(get()) }
    factory(named<MainActivity>()) { HttpModule(get()) }
}

val viewModelModule = module {
    viewModel { AppViewModel(get()) }
    single { AbcModule() }
}

val scope = module {
    scope(named<MainActivity>()) {
        scoped { HttpModule(get()) }
    }
}

