package com.asuprojects.kotlincustomcomponents.fragments.spinners.exemplo3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.asuprojects.kotlincustomcomponents.R

class CustomSpinnerAdapter(
    val context: Context,
    var flags: List<Int>,
    var countryNames: List<String>
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_spinner_item, null)
        val icon = view.findViewById<AppCompatImageView>(R.id.custom_spinner_flag)
        val countryName = view.findViewById<AppCompatTextView>(R.id.custom_spinner_flag_name)
        icon.setImageResource(flags[position])
        countryName.text = countryNames[position]
        return view
    }

    override fun getItem(position: Int): Any {
        return Any()
    }

    override fun getItemId(position: Int): Long {
        return 0L
    }

    override fun getCount(): Int {
        return flags.size
    }

}