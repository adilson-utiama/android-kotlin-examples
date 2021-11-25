package com.asuprojects.kotlincustomcomponents.fragments.spinners


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter

import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.spinners.exemplo2.CountryAdapter
import com.asuprojects.kotlincustomcomponents.fragments.spinners.exemplo3.CustomSpinnerAdapter
import com.asuprojects.kotlincustomcomponents.helpers.ToastUtil
import kotlinx.android.synthetic.main.fragment_spinners.*

class SpinnersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spinners, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Custom Spinner Exemplo 2
        val mesAdapter = ArrayAdapter.createFromResource(requireActivity(), R.array.meses, android.R.layout.simple_spinner_item)
        mesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinners_example_1.adapter = mesAdapter

        val countryAdapter =
            CountryAdapter(
                requireActivity()
            )
        spinners_example_2.adapter = countryAdapter


        // Custom Spinner Exemplo 2
        val flags = listOf(
            R.drawable.brasil, R.drawable.japao, R.drawable.alemanha, R.drawable.eua, R.drawable.italia
        )
        val countryNames = listOf(
            "Brasil", "Japão", "Alemanha", "EUA", "Italia"
        )
        val customSpinnerAdapter = CustomSpinnerAdapter(requireActivity(), flags, countryNames)
        spinners_example_3.adapter = customSpinnerAdapter
        spinners_example_3.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                ToastUtil.msg(requireActivity(), "Pais Selecionado: ${countryNames[position]}")
            }

        }
    }
}
