package uz.gita.newmobilebankig.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.LinearLayout
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.newmobilebankig.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val host = supportFragmentManager.findFragmentById(R.id.fragmentController) as NavHostFragment
        val graph =host.navController.navInflater.inflate(R.navigation.navigation)
        host.navController.graph = graph

    }
}
/*
 findViewById<EditText>(R.id.userNameEdit).setOnFocusChangeListener { view, b ->
            findViewById<LinearLayout>(R.id.linear1).setBackgroundResource(
                if (b) R.drawable.bg_layout else R.drawable.bg_layout_un
            )
        }
* */