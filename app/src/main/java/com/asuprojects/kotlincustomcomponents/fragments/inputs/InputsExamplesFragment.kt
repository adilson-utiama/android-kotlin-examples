package com.asuprojects.kotlincustomcomponents.fragments.inputs


import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.telephony.PhoneNumberUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.inputs.custom.*
import kotlinx.android.synthetic.main.fragment_inputs_examples.*

class InputsExamplesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inputs_examples, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        custom_input_monetary_value.setOnChangeValueListener(object: OnChangeValueListener{
            override fun onChangeValue(value: Double) {
                Log.i("TESTE", "Value: $value")
            }
        })

        custom_input_phone_mask.addTextChangedListener(object: PhoneNumberFormattingTextWatcher(){

        })
        PhoneNumberUtils.formatNumber(custom_input_phone_mask.text.toString())

        custom_input_cpf_mask.addTextChangedListener(CPFMaskTextWatcher("###.###.###-##"))

        custom_input_date_mask.addTextChangedListener(DateMaskTextWatcher(custom_input_date_mask, "##/##/####"))

        custom_input_date_mask_validation.addTextChangedListener(DateMaskValidationTextWatcher(custom_input_date_mask_validation))


    }

}
