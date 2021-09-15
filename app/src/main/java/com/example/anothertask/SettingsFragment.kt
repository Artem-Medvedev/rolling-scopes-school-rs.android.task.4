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
        val switchTitle = findPreference<SwitchPreference>("title")
        val switchDesc = findPreference<SwitchPreference>("desc")
        val switchAge = findPreference<SwitchPreference>("age")

        switchTitle?.setOnPreferenceClickListener {
            if(switchTitle.isChecked){
                switchDesc?.isChecked = false
                switchAge?.isChecked = false
            }
            true
        }

        switchDesc?.setOnPreferenceClickListener {
            if(switchDesc.isChecked){
                switchTitle?.isChecked = false
                switchAge?.isChecked = false
            }
            true
        }

        switchAge?.setOnPreferenceClickListener {
            if(switchAge.isChecked){
                switchDesc?.isChecked = false
                switchTitle?.isChecked = false
            }
            true
        }

    }
}