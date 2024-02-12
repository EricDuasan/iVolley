package es.ericd.ivolley

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import es.ericd.ivolley.services.FirebaseService
import es.ericd.ivolley.services.FirestoreService
import es.ericd.ivolley.utils.Constants
import es.ericd.ivolley.utils.PreferencesUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var currentUsername: String

    private val listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
        // Handle the preference change here
        if (key == PreferencesUtil.USERNAME) {
            val newUser = sharedPreferences.getString(PreferencesUtil.USERNAME, "")

            if (newUser != currentUsername) {
                lifecycleScope.launch(Dispatchers.IO) {
                    FirestoreService.updateUser(currentUsername, newUser ?: FirebaseService.getCurrentUser()?.email.toString())
                }
            }

            Log.d("sisi", newUser.toString())
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = PreferencesUtil.getPreferences(this)

        currentUsername = sharedPreferences.getString(PreferencesUtil.USERNAME, "").toString()

        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)


        setContentView(R.layout.activity_settings)

    }

    override fun onDestroy() {
        super.onDestroy()

        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)

    }
}