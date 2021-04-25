package com.rivaldofez.cubihub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import com.rivaldofez.cubihub.databinding.ActivitySettingsBinding
import com.rivaldofez.cubihub.databinding.ActivityUserDetailBinding
import com.rivaldofez.cubihub.databinding.FragmentUsersBinding
import java.text.SimpleDateFormat
import java.util.*

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var alarmReceiver: AlarmReceiver

    companion object {
        private const val DATE_PICKER_TAG = "DatePicker"
        private const val TIME_PICKER_ONCE_TAG = "TimePickerOnce"
        private const val TIME_PICKER_REPEAT_TAG = "TimePickerRepeat"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        alarmReceiver = AlarmReceiver()

        binding.tvLanguageStatus.text = Locale.getDefault().displayLanguage

        binding.clChooseLanguage.setOnClickListener(View.OnClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
            binding.tvLanguageStatus.text = Locale.getDefault().displayLanguage
        })

        binding.swAlarm.setOnCheckedChangeListener({_, isChecked->
            val date = "2021-04-25"
            val time = "18:45"

            if(isChecked){
                alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING,
                    time, "Testing")
            }else{
                alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_REPEATING)
            }
        })
    }
}