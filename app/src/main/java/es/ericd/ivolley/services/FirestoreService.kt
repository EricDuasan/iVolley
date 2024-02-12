package es.ericd.ivolley.services

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import es.ericd.ivolley.dataclases.ChatUIDItem
import es.ericd.ivolley.dataclases.Chatitem
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.TimeZone
import com.google.firebase.Timestamp

class FirestoreService {
    companion object {

        private val CHATS = "chats"
        private val CHATS_PREFS = "chatsPrefs"

        private var firebaseDB : FirebaseFirestore? = null

        private fun getInstance(): FirebaseFirestore {
            if (firebaseDB == null) {
                firebaseDB = FirebaseFirestore.getInstance()
            }

            return firebaseDB!!

        }

        suspend fun getChats(): MutableList<Chatitem> {
            val firebaseDB = getInstance()

            val chatsFromDB = firebaseDB.collection(CHATS).orderBy("timestamp").get().await()

            val data = mutableListOf<Chatitem>()

            chatsFromDB.forEach {
                data.add(
                    Chatitem(
                        username = it.get("username").toString(),
                        message = it.get("message").toString()
                    )
                )
            }


            return data

        }

        suspend fun addChat(message: String) {
            val firebaseDB = getInstance()

            // Get UTC timestamp from firebase
            val firebaseUser = FirebaseService.getCurrentUser()?.displayName ?: FirebaseService.getCurrentUser().toString()

            firebaseDB.collection(CHATS).add(mapOf(
                    "username" to firebaseUser,
                    "message" to message,
                    "timestamp" to Timestamp.now()
                )
            ).await()

        }

        suspend fun updateUser(prevUsername: String, newUser: String) {
            val data = getInstance().collection(CHATS).whereEqualTo("username", prevUsername).get().await()

            data.forEach {
                it.reference.update("username", newUser).await()
            }
        }

    }
}