package example.extension

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.activity.Builder
import com.extension.*
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.android.scope.ext.android.bindScope
import org.koin.android.scope.ext.android.getOrCreateScope
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class MainActivity : AppCompatActivity() {


    private val id1 by extra("Hello", "fg")
    val service: Builder by inject()
    val context: Context by inject()
    val httpModule: HttpModule by inject("http")
    val httpModule2: HttpModule by inject("factory")
    val viewModels by viewModel<AppViewModel>()
    val abcModule by inject<AbcModule>()
    val components: Components by lazy {
        Components()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindScope(getOrCreateScope("mainActivity"))

        setContentView(R.layout.activity_main)
        Log.i(localClassName, "ids = $id1")
        Log.i(localClassName, "context = ${context}")
        Log.i(localClassName, "builder = ${service}")
        Log.i(localClassName, "httpModule = ${httpModule}")
        Log.i(localClassName, "httpModule = ${httpModule}")
        Log.i(localClassName, "httpModule.Context = ${httpModule.init()}")
        Log.i(localClassName, "httpModule2 = ${httpModule2}")
        Log.i(localClassName, "httpModule2 = ${httpModule2}")
        Log.i(localClassName, "httpModule2.Context = ${httpModule2.init()}")

        Log.i(localClassName, "viewModels = $viewModels")
        Log.i(localClassName, "viewModels.init = ${viewModels.init()}")

        Log.i(localClassName, "components.context = ${components.context}")
        Log.i(localClassName, "components.service = ${components.service}")
        Log.i(localClassName, "AbcModule().components.context = ${abcModule.init()}")
        "Hello".printInfo()
        "Hello".printError()
//        resColor(R.color.notification_icon_bg_color)
//        val intent = intent<MainActivity>()
//        val int = intent<MainActivity> {
//            putExtra("v", "hello")
//        }
//        startActivity<MainActivity>(Bundle())
//
//        resInt(R.integer.abc_config_activityDefaultDur)
//        resBoolean(R.bool.abc_action_bar_embed_tabs)
//        resDrawable(R.drawable.abc_ab_share_pack_mtrl_alpha)
//        inflateLayout(R.layout.abc_action_menu_item_layout)
//        share("Text", "Hello")
//        email("a@a.com", "ad@aa.com", "subject", "text")
//        dial("123344")
//        rate()
//        resColor(R.color.notification_icon_bg_color)
//        val textView = TextView(this)
//        val string = textView.resString(R.string.abc_action_bar_home_description)
//        textView.visible()
//        textView.invisible()
//        textView.gone()
//        textView.showKeyboard()
//        textView.afterMeasured {
//
//        }
//        textView.click {
//
//        }
//        val editText = EditText(this)
//        editText.onTextChanged { _, _, _, _ ->
//
//        }
//        editText.disable()
//        val linearLayout = LinearLayout(this)
//        linearLayout.forEach {
//            it.context
//        }
        btn.backgroundTint(R.color.error_color_material_dark)
        btn.disable()
        val str = ""
        str.isEmptyOrNull {

        }
        image.foregroundTint(R.color.error_color_material_dark)

        btn1.backgroundTint(R.color.highlighted_text_material_light)
        btn1.click {
            btn.enable()
            btn.toggleVisibility()
            // btn.toUpperCase()
            btn.textSize(20f)
            createImageFile("abc.jpg")
            createImageFile("abc.text")
            val file = getImageFile("abc.jpg")
            val a = "10"
            println(file?.toString())
            println(now())
            println(currentUTC)
            println(currentUTC("dd-MM-yyyy", Locale.ENGLISH))
            println(now().toUTC)
            val milli = 1451005003353
            println(milli.toUTC())
            startActivity<SecondActivity>()
        }
        val a = "10"
        a.toJson()
        a.fromJson<String>()
        val array = emptyList<String>()
        array.isBlank()
        a.toInt().times {
            it.toString()
        }


    }
}
