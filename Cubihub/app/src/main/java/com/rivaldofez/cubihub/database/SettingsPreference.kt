package com.rivaldofez.cubihub.database

import android.content.Context

class SettingsPreference(context: Context) {

    companion object {
        private const val PREFERENCE_NAME = "user_settings"
        private const val ALARM_STATE = "alarm_state"
    }

    private val preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun setSettings(value: Boolean){
        val editor = preferences.edit()
        editor.putBoolean(ALARM_STATE,value)
        editor.apply()
    }

    fun getSettings(): Boolean = preferences.getBoolean(ALARM_STATE, false)
}