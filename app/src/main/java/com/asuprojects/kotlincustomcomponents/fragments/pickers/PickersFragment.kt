package com.asuprojects.kotlincustomcomponents.fragments.pickers


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.pickers.datepicker.DatePickerActivity
import com.asuprojects.kotlincustomcomponents.fragments.pickers.filepicker.FilePickerActivity
import com.asuprojects.kotlincustomcomponents.fragments.pickers.timepicker.TimePickerActivity
import kotlinx.android.synthetic.main.fragment_pickers.*

class PickersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pickers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_date_picker.setOnClickListener {
            startActivity(Intent(requireActivity(), DatePickerActivity::class.java))
        }

        btn_time_picker.setOnClickListener {
            startActivity(Intent(requireActivity(), TimePickerActivity::class.java))
        }

        btn_file_picker.setOnClickListener {
            startActivity(Intent(requireActivity(), FilePickerActivity::class.java))
        }


    }


}
