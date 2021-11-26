package com.asuprojects.kotlincustomcomponents.fragments.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.databinding.DialogFullScreenBinding

class DialogFullScreen : DialogFragment() {

    private var _bind: DialogFullScreenBinding? = null
    private val bind get() = _bind!!

    override fun onDestroy() {
        _bind = null
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_Dialog_FullScreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = DialogFullScreenBinding.inflate(inflater, container, false)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        //dialog?.window?.setWindowAnimations(R.style.DialogAnimation)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bind.btnChangeText.setOnClickListener {
            val text = bind.editTextContent.text.toString().trim()
            if(text.isNotEmpty()){
                bind.exampleText.text = text
                bind.editTextContent.setText("")
            }
        }

        bind.btnClose.setOnClickListener {
            dismiss()
        }

    }

}