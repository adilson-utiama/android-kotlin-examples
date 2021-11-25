package com.asuprojects.kotlincustomcomponents.fragments.pickers.datepicker

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.asuprojects.kotlincustomcomponents.R
import kotlinx.android.synthetic.main.activity_date_picker.*
import java.util.*

class DatePickerActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_date_picker)

        supportActionBar?.apply {
            this.title = "Date Picker"
            this.setDisplayHomeAsUpEnabled(true)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
                Toast.makeText(this@DatePickerActivity,
                    "Data Selecionada: $dayOfMonth/${monthOfYear + 1}/$year",
                    Toast.LENGTH_SHORT).show()
            }
        }else{
            val calendar = Calendar.getInstance()
            datePicker.init(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ) { view, year, monthOfYear, dayOfMonth ->
                Toast.makeText(this@DatePickerActivity,
                    "Data Selecionada: $dayOfMonth/$monthOfYear/$year",
                    Toast.LENGTH_SHORT).show()
            }
        }

        btnDatePicker.setOnClickListener {
            val datePickerFragment = DatePickerFragment()
            datePickerFragment.show(supportFragmentManager, "DatePickerDialog")
        }
    }
}
