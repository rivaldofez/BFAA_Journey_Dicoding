package com.rivaldofez.cubihub

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rivaldofez.cubihub.databinding.FragmentFollowBinding
import com.rivaldofez.cubihub.databinding.FragmentSettingsBinding
import java.util.*

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var alarmReceiver: AlarmReceiver

    companion object {
        private const val DATE_PICKER_TAG = "DatePicker"
        private const val TIME_PICKER_ONCE_TAG = "TimePickerOnce"
        private const val TIME_PICKER_REPEAT_TAG = "TimePickerRepeat"
    }

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
                alarmReceiver.setRepeatingAlarm(requireContext(), AlarmReceiver.TYPE_REPEATING,
                    time, "Testing")
            }else{
                alarmReceiver.cancelAlarm(requireContext(), AlarmReceiver.TYPE_REPEATING)
            }
        })

        binding.btnBack.setOnClickListener{
            requireActivity().onBackPressed()
        }
    }

}