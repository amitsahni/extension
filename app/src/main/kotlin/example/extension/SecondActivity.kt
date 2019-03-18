package example.extension

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class SecondActivity : AppCompatActivity() {


    val components: Components by lazy {
        Components()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        Log.i(localClassName, "components.context = ${components.context}")
        Log.i(localClassName, "components.service = ${components.service}")
        //Log.i(localClassName, "components.viewModels = ${components.viewModels}")


    }
}
