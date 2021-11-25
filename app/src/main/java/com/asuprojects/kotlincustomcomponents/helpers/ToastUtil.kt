package com.asuprojects.kotlincustomcomponents.helpers

import android.content.Context
import android.widget.Toast

class ToastUtil {

    companion object {

        fun msg(context: Context, message: String){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

    }
}