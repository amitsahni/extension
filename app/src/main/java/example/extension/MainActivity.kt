package example.extension

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.extension.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
            btn.toUpperCase()
            btn.textSize(10f)
        }
    }
}
