package com.submission.githubuserapi.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.submission.githubuserapi.R
import com.submission.githubuserapi.databinding.ActivitySettingsBinding
import com.submission.githubuserapi.notification.AlarmReceiver
import com.submission.githubuserapi.notification.Pref
import com.submission.githubuserapi.utils.saveReminder

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val alarmReceiver = AlarmReceiver()
        val reminderPref = Pref(this)
        binding.swReminder.isChecked = reminderPref.getReminder().reminder
        binding.swReminder.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                true.saveReminder(this)
                val time = getString(R.string.time)
                val message = getString(R.string.hello)
                alarmReceiver.setRepeatingAlarm(
                    this, AlarmReceiver.TYPE_REPEATING,
                    time, message
                )
            } else {
                false.saveReminder(this)
                alarmReceiver.cancelAlarm(
                    this,
                    AlarmReceiver.TYPE_REPEATING
                )
            }
        }

    }

}