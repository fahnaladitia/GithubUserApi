package com.submission.githubuserapi.notification

import android.content.Context

class Pref(context: Context) {

    private val preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setReminder(value: ItemsReminder) {
        val editor = preference.edit()
        editor.putBoolean(REMINDER, value.reminder)
        editor.apply()
    }

    fun getReminder(): ItemsReminder {
        val model = ItemsReminder()
        model.reminder = preference.getBoolean(REMINDER, false)
        return model
    }


    companion object {
        const val PREFS_NAME = "reminder_pref"
        private const val REMINDER = "isRemind"
    }
}