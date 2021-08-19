package com.submission.githubuserapi.utils

import android.content.Context
import android.view.View
import com.submission.githubuserapi.notification.ItemsReminder
import com.submission.githubuserapi.notification.Pref
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun View.toVisible() {
    visibility = View.VISIBLE
}

fun View.toGone() {
    visibility = View.GONE
}

fun String.isDateInvalid(date: String): Boolean {
    return try {
        val df = SimpleDateFormat(this, Locale.getDefault())
        df.isLenient = false
        df.parse(date)
        false
    } catch (e: ParseException) {
        true
    }
}

fun Boolean.saveReminder(context: Context) {
    val reminder = Pref(context)
    val itemsReminder = ItemsReminder()
    itemsReminder.reminder = this
    reminder.setReminder(itemsReminder)

}
