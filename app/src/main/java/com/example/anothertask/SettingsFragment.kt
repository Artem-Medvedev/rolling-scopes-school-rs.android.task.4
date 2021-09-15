package com.example.anothertask

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference

class SettingsFragment: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        return
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.settings)
    }

    override fun onResume() {
        super.onResume()
        val switchName = findPreference<SwitchPreference>("name")
        val switchSurname = findPreference<SwitchPreference>("surname")
        val switchAge = findPreference<SwitchPreference>("age")

        switchName?.setOnPreferenceClickListener {
            if(switchName.isChecked){
                switchSurname?.isChecked = false
                switchAge?.isChecked = false
            }
            true
        }

        switchSurname?.setOnPreferenceClickListener {
            if(switchSurname.isChecked){
                switchName?.isChecked = false
                switchAge?.isChecked = false
            }
            true
        }

        switchAge?.setOnPreferenceClickListener {
            if(switchAge.isChecked){
                switchName?.isChecked = false
                switchSurname?.isChecked = false
            }
            true
        }

    }
}