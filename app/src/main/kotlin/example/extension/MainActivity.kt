package example.extension

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.activity.Builder
import com.extension.*
import com.extension.P.defaultPrefs
import jp.wasabeef.glide.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MainActivity : AppCompatActivity() {


    private val id1 by extra("Hello", "fg")
    val service: Builder by inject()
    val context: Context by inject()
    val httpModule: HttpModule by currentScope.inject()
    val httpModule2: HttpModule by currentScope.inject()
    val viewModels by viewModel<AppViewModel>()
    val abcModule by inject<AbcModule>()
    val components: Components by lazy {
        Components()
    }
    val builder: AlertDialog.Builder by inject()
    private val url = "https://res.cloudinary.com/clickapp/image/upload/v1547194652/Test/1.png"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        text.drawableStart = android.R.drawable.ic_delete
//        context.downloadFile(url) {
//            this?.path?.printInfo()
//            image.setImageURI(Uri.fromFile(this))
//        }
        /*context.downloadBitmap(url) {
            this?.let {
                image.setImageBitmap(this)
            }
        }*/

        image.load<Drawable>(url, -1, CropCircleTransformation()) {
            image?.setImageDrawable(this)
        }
        alert {
            setCancelable(true)
            setPositiveButton("df") { _, _ ->

            }
            setNegativeButton("D") { _, _ ->

            }.show()
        }
//        builder.singleChoice("Hello") {
//
//        }
//        builder.doubleChoice("ok", "Cancel") {
//            if (it == AlertDialog.BUTTON_POSITIVE) {
//                "Ok".printInfo()
//            } else {
//                "Cancel".printError()
//            }
//        }
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
        val prefs = defaultPrefs(context)
        val prefs1 = defaultPrefs(context)
        val prefs2 = defaultPrefs(context)
        prefs["asdf"] = "Hello"
        val d = prefs["asdf", "jhkj"]
        btn.backgroundTint = R.color.error_color_material_dark
        btn.disable()
        btn.resString = R.string.abc_action_bar_home_description
        btn.setTextSize = 10f
        btn.textColor = R.color.notification_icon_bg_color
        val str = null
        str.isEmptyOrNull {

        }
        text.backgroundTint = R.color.notification_icon_bg_color
        val name = str ?: "dfasdf"
        //image.foregroundTint(R.color.error_color_material_dark)
//        image.foregroundTint = R.color.error_color_material_dark
//        image.drawable.tint = resColor(R.color.error_color_material_dark)
        // btn1.backgroundTint(R.color.highlighted_text_material_light)
        btn.backgroundTint = R.color.highlighted_text_material_light
        btn1.click {
            toast("ok")
            btn.snackBar("Hello", "Ok") {
                snackBar("Ok")
            }
            btn.enable()
            btn.toggleVisibility()
            // btn.toUpperCase()
            btn.setTextSize = 20f
            createImageFile("abc.jpg")
            createImageFile("abc.text")
            val file = getImageFile("abc.jpg")
            println(file?.toString())
            println(now)
            println(currentUTC)
            println(currentUTC("dd-MM-yyyy", Locale.ENGLISH))
            println(now.toUTC)
            val milli = 1451005003353
            println(milli.toUTC)
            //startActivity<SecondActivity>()
        }
        btn1.longClick {

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



