package es.ericd.ivolley

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.ericd.ivolley.services.FirebaseService
import es.ericd.ivolley.utils.Constants

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


    }

    fun setDefaultUsernameOnPreferences() {
        val prefs = getSharedPreferences(Constants.preferences, Context.MODE_PRIVATE)

        val username = prefs.getString("username", null)

        if (username == null || username.equals("")) {
            // TODO: SET DEFAULT FIREBASE USERNAME
        }
    }
}