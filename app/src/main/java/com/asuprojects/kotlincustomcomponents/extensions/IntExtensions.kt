package com.asuprojects.kotlincustomcomponents.extensions

import android.content.res.Resources

fun Int.toPx() = (this * Resources.getSystem().displayMetrics.density).toInt()