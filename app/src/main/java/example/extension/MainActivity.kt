package example.extension

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.extension.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {


    private val id1 by extra("Hello", "fg")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(localClassName, "ids = $id1")
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
