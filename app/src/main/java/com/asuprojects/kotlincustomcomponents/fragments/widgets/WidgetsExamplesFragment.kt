package com.asuprojects.kotlincustomcomponents.fragments.widgets


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.asuprojects.kotlincustomcomponents.R
import kotlinx.android.synthetic.main.fragment_widgets_examples.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WidgetsExamplesFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_widgets_examples, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(sb: SeekBar?) {
                sb?.apply {
                    seekBar.progress = this.progress
                    seekBar_value.text = this.progress.toString()
                }
            }

        })

        btn_start_progress.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                for(i in 1..5){
                    progressBar2.progress += 20
                    delay(1000)
                }
            }
        }

        btn_reset_progress.setOnClickListener {
            if(progressBar2.progress == 100){
                progressBar2.progress = 0
            }
        }

    }

}
