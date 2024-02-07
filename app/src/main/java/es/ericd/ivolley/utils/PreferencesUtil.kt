package es.ericd.ivolley.utils

import android.content.Context
import android.content.SharedPreferences

class PreferencesUtil {
    companion object {

        val USERNAME = "username"
        val USERUID = "userUID"

        fun getPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences(Constants.preferences, Context.MODE_PRIVATE)

        }
    }
}