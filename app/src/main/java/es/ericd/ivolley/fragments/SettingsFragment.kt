package es.ericd.ivolley.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import es.ericd.ivolley.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}