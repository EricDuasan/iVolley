package es.ericd.ivolley.dataclases

import com.google.firebase.Timestamp

data class Chatitem (
    val username: String,
    val message: String,
    val timestamp: Timestamp
)