package com.rivaldofez.cubihub.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rivaldofez.cubihub.R
import com.rivaldofez.cubihub.database.SettingsPreference
import com.rivaldofez.cubihub.databinding.FragmentSettingsBinding
import com.rivaldofez.cubihub.service.AlarmReceiver
import java.util.*

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var settingsPreference: SettingsPreference
    private val time = "09:00"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alarmReceiver = AlarmReceiver()
        settingsPreference = SettingsPreference(requireContext())

        binding.tvLanguageStatus.text = Locale.getDefault().displayLanguage

        binding.swAlarm.isChecked = settingsPreference.getSettings()
        if(settingsPreference.getSettings())
            binding.tvAlarmStatus.text = getString(R.string.alarm_status_active)
        else
            binding.tvAlarmStatus.text = getString(R.string.alarm_status_inactive)


        binding.clChooseLanguage.setOnClickListener({
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
            binding.tvLanguageStatus.text = Locale.getDefault().displayLanguage
        })

        binding.swAlarm.setOnCheckedChangeListener({_, isChecked->
            if(isChecked){
                alarmReceiver.setRepeatingAlarm(requireContext(), time, getString(R.string.alarm_notification_message))
                binding.tvAlarmStatus.text = getString(R.string.alarm_status_active)
                settingsPreference.setSettings(true)
            }else{
                alarmReceiver.cancelAlarm(requireContext())
                binding.tvAlarmStatus.text = getString(R.string.alarm_status_inactive)
                settingsPreference.setSettings(false)
            }
        })

        binding.btnBack.setOnClickListener{
            requireActivity().onBackPressed()
        }
    }

}