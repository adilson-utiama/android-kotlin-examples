package com.asuprojects.kotlincustomcomponents.helpers

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

//implementation "androidx.preference:preference:1.1.1"


class SharedPrefs(
    val context: Context
) {
    private var prefs: SharedPreferences = PreferenceManager
        .getDefaultSharedPreferences(context)

    fun storeInt(key: String, value: Int){
        val editor = prefs.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun restoreInt(key: String, defaultValue: Int = 0): Int {
        return prefs.getInt(key, defaultValue)
    }

    fun storeString(key: String, value: String) {
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun restoreString(key: String, defaultValue: String = ""): String? {
        return prefs.getString(key, defaultValue)
    }

    fun storeBoolean(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    fun restoreBoolean(key: String, defaultValue: Boolean): Boolean {
        return prefs.getBoolean(key, defaultValue)
    }

    fun storeLong(key: String, value: Long){
        prefs.edit().putLong(key, value).apply()
    }

    fun restoreLong(key: String, defaultValue: Long): Long {
        return prefs.getLong(key, defaultValue)
    }
}