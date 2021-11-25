package com.asuprojects.kotlincustomcomponents.fragments.bottomsheet.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asuprojects.kotlincustomcomponents.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.custom_bs_add_text.*


interface OnSaveText {
    fun onClickSaveTextButton(text: String)
}

class CustomBSDialolgAddText() : BottomSheetDialogFragment() {

    private var onSaveText: OnSaveText? = null

    fun setOnSaveText(listener: OnSaveText){
        this.onSaveText = listener
    }

//    override fun getTheme(): Int {
//        return R.style.Theme_MaterialComponents_Light_BottomSheetDialog
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Set Theme with Transparent Background
        setStyle(STYLE_NORMAL, R.style.MyBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.custom_bs_add_text, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_bs_adicionar_texto.setOnClickListener {
            if(bs_input_text.text.toString().isNotEmpty()){

                val value = bs_input_text.text.toString()

                onSaveText?.onClickSaveTextButton(value)

                dismiss()
            }
        }

        btn_bs_cancelar.setOnClickListener {
            dismiss()
        }

    }
}