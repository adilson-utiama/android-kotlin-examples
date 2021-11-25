package com.asuprojects.kotlincustomcomponents.fragments.spinners.exemplo2

import android.content.Context
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.asuprojects.kotlincustomcomponents.R
import java.util.*

class CountryAdapter(context: Context) :
    ArrayAdapter<OperatedCountry>(context, 0, OperatedCountry.values()) {

    val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.spinner_country_item, parent, false)
        } else {
            view = convertView
        }
        getItem(position)?.let { country ->
            setItemForCountry(view, country)
        }
        return view

    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        if (position == 0) {
            view = layoutInflater.inflate(R.layout.spinner_header_country, parent, false)
            view.setOnClickListener {
                val root = parent.rootView
                root.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK))
                root.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK))
            }
            view.findViewById<AppCompatImageView>(R.id.ivArrow)
                .animate()
                .rotation(180f)
                .setDuration(200)
                .start()
        } else {
            view = layoutInflater.inflate(R.layout.spinner_country_item, parent, false)
            getItem(position)?.let { country ->
                setItemForCountry(view, country)
            }
        }
        return view
    }

    override fun getItem(position: Int): OperatedCountry? {
        if (position == 0) {
            return null
        }
        return super.getItem(position - 1)
    }

    override fun getCount() = super.getCount() + 1

    override fun isEnabled(position: Int) = position != 0

    private fun setItemForCountry(view: View, country: OperatedCountry) {
        val tvCountry = view.findViewById<AppCompatTextView>(R.id.tvCountry)
        val ivCountry = view.findViewById<AppCompatImageView>(R.id.ivCountry)
        val countryName = Locale("", country.countryCode).displayCountry
        tvCountry.text = countryName
        ivCountry.setBackgroundResource(country.icon)
    }
}