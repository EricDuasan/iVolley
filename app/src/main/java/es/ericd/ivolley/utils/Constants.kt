package es.ericd.ivolley.utils

class Constants {
    companion object {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@(.+)\$")
        val preferences = "es.ericd.ivolley_preferences"
        val NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL_ID"
        val NOTIFICATION_CHANNEL_NAME = "NOTIFICATION_CHANNEL_NAME"
    }
}