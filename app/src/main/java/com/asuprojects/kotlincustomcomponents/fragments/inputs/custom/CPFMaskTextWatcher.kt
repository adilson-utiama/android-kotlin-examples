package com.asuprojects.kotlincustomcomponents.fragments.inputs.custom

import android.text.Editable
import android.text.TextWatcher
import android.util.Log

class CPFMaskTextWatcher(val mask: String): TextWatcher {

    private var isRunning = false
    private var isDeleting = false

    companion object {
        fun buildCpf(): CPFMaskTextWatcher {
            return CPFMaskTextWatcher("###.###.###-##")
        }
    }

    override fun afterTextChanged(editable: Editable?) {

        editable?.apply {
            if (isRunning || isDeleting) {
                return
            }
            isRunning = true



            val editableLength = editable.length
            if(editableLength < mask.length){
                if (mask[editableLength] != '#') {
                    editable.append(mask[editableLength])
                    Log.i("TESTE", "Editable: $editable")
                } else if (mask[editableLength - 1] != '#') {
                    editable.insert(editableLength - 1, mask, editableLength - 1, editableLength)
                    Log.i("TESTE", "Editable: $editable")
                }
            }
            Log.i("TESTE", "Editable: $editable")

            isRunning = false
        }

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        isDeleting = count > after
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}