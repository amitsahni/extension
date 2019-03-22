package example.extension

import android.content.Context
import com.activity.Builder
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class Components : KoinComponent {
    val context: Context by inject()
    val service: Builder by inject()
//    val viewModels by viewModel<AppViewModel>(get())

}