package com.asuprojects.kotlincustomcomponents.fragments.pickers.timepicker

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TimePicker
import android.widget.Toast
import com.asuprojects.kotlincustomcomponents.R
import kotlinx.android.synthetic.main.activity_time_picker.*
import java.util.*

class TimePickerActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_time_picker)

        supportActionBar?.apply {
            this.title = "Time Picker"
            this.setDisplayHomeAsUpEnabled(true)
        }

        timePicker.setIs24HourView(true)

        timePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            val time = "Horario Selecionado: $hourOfDay:$minute"
            Toast.makeText(this@TimePickerActivity, time, Toast.LENGTH_SHORT).show()
        }

        showTimePicker.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hora = calendar.get(Calendar.HOUR_OF_DAY)
            val minuto = calendar.get(Calendar.MINUTE)
            val timePicker = TimePickerDialog(
                this@TimePickerActivity,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    val time = "Horario Selecionado: $hourOfDay:$minute"
                    Toast.makeText(this@TimePickerActivity, time, Toast.LENGTH_SHORT).show()
                }, hora, minuto, true
            )
            timePicker.setTitle("Selecione o Horário")
            timePicker.show()
        }
    }
}
