package com.asuprojects.kotlincustomcomponents.extensions

import android.graphics.Typeface
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

fun Toolbar.changeToolbarFont(){
    for (i in 0 until childCount) {
        val view = getChildAt(i)
        if (view is TextView && view.text == title) {
            view.typeface = Typeface.createFromAsset(view.context.assets, "fonts/customFont")
            break
        }
    }
}