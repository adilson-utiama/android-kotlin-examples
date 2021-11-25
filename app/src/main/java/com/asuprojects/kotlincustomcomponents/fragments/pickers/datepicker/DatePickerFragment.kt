package com.asuprojects.kotlincustomcomponents.fragments.pickers.datepicker


import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment

import com.asuprojects.kotlincustomcomponents.R
import java.util.*

class DatePickerFragment : DialogFragment() {

    private val dateSetListener =
        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val date = "Data Selecionada: $dayOfMonth/$month/$year"
            Toast.makeText(requireActivity(), date, Toast.LENGTH_SHORT).show()
        }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return  DatePickerDialog(requireActivity(), dateSetListener, year, month, day)
    }


}
