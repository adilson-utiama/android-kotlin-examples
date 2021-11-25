package com.asuprojects.kotlincustomcomponents.fragments.bottomsheet.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asuprojects.kotlincustomcomponents.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.custom_bs_info.*

class CustomBSDialogInfo : BottomSheetDialogFragment() {

    private var msg = "Texto Padrão da Mensagem do BottomSheetDialogFragment!"

    fun setMsg(newText: String){
        this.msg = newText
    }

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
        return inflater.inflate(R.layout.custom_bs_info, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isCancelable = false
        dialog?.window?.attributes?.windowAnimations = R.style.BSDialogAnimation

        text_bs_info_message.text = msg

        btn_bs_info_ok.setOnClickListener {
            dismiss()
        }

    }
}