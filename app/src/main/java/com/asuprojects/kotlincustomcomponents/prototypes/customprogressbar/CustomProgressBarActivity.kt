package com.asuprojects.kotlincustomcomponents.prototypes.customprogressbar

import android.graphics.Typeface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.AppCompatTextView
import com.asuprojects.kotlincustomcomponents.R
import kotlinx.android.synthetic.main.activity_custom_progress_bar.*

class CustomProgressBarActivity : AppCompatActivity() {

    private val creditosTotal = 100
    private var creditosUsados = 25

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_progress_bar)

        setSupportActionBar(custom_progress_toolbar)
//        val title =
//            custom_progress_toolbar.findViewById<AppCompatTextView>(R.id.toolbar_title)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            title.typeface = resources.getFont(R.font.roboto_condensed_regular)
//        }
        supportActionBar?.apply {
            this.title = "ProgressBars"
            this.setDisplayHomeAsUpEnabled(true)
        }

        custom_progress_value.text = creditosUsados.toString()

        custom_progress_creditos_text_limite.text = creditosTotal.toString()
        custom_progress_creditos_text_value.text = creditosUsados.toString()

        custom_barra_creditos.progress = creditosUsados
        custom_barra_creditos.max = creditosTotal

        custom_progress_remove.setOnClickListener {
            if(creditosUsados > 0){
                decreaseValue()
            }
        }
        custom_progress_add.setOnClickListener {
            if(creditosUsados < creditosTotal){
                increaseValue()
            }
        }
    }

    private fun increaseValue() {
        creditosUsados += 25
        custom_progress_creditos_text_value.text = creditosUsados.toString()

        custom_progress_value.text = creditosUsados.toString()
        custom_barra_creditos.progress = creditosUsados

        default_progressbar.progress = creditosUsados
        custom_simple_progress.progress = creditosUsados
        custom_simple_progress_color_line.progress = creditosUsados
        custom_simple_progress_gradient_color_line.progress = creditosUsados
    }

    private fun decreaseValue() {
        creditosUsados -= 25
        custom_progress_creditos_text_value.text = creditosUsados.toString()

        custom_progress_value.text = creditosUsados.toString()
        custom_barra_creditos.progress = creditosUsados

        default_progressbar.progress = creditosUsados
        custom_simple_progress.progress = creditosUsados
        custom_simple_progress_color_line.progress = creditosUsados
        custom_simple_progress_gradient_color_line.progress = creditosUsados
    }
}
