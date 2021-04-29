package com.rivaldofez.cubihub

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rivaldofez.cubihub.database.SettingsPreference
import com.rivaldofez.cubihub.databinding.FragmentFollowBinding
import com.rivaldofez.cubihub.databinding.FragmentSettingsBinding
import java.util.*

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var settingsPreference: SettingsPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            binding.tvAlarmStatus.text = "Alarm Aktif"
        else
            binding.tvAlarmStatus.text = "Alarm Mati"


        binding.clChooseLanguage.setOnClickListener(View.OnClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
            binding.tvLanguageStatus.text = Locale.getDefault().displayLanguage
        })

        binding.swAlarm.setOnCheckedChangeListener({_, isChecked->
            val time = "09:00"
            if(isChecked){
                alarmReceiver.setRepeatingAlarm(requireContext(), time, "Waktu untuk kembali ke aplikasi")
                binding.tvAlarmStatus.text = "Alarm Aktif"
                settingsPreference.setSettings(true)
            }else{
                alarmReceiver.cancelAlarm(requireContext(), AlarmReceiver.TYPE_REPEATING)
                binding.tvAlarmStatus.text = "Alarm Mati"
                settingsPreference.setSettings(false)
            }
        })

        binding.btnBack.setOnClickListener{
            requireActivity().onBackPressed()
        }
    }

}