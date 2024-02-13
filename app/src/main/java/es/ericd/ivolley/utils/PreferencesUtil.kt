package es.ericd.ivolley.utils

import android.content.Context
import android.content.SharedPreferences

class PreferencesUtil {
    companion object {

        val USERNAME = "username"
        val COLOR = "chatUsernameColor"
        val CACHE = "cache"

        fun getPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences(Constants.preferences, Context.MODE_PRIVATE)

        }
    }
}