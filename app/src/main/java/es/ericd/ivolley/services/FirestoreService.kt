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

        suspend fun  getUsersIDs(): MutableList<ChatUIDItem> {
            val firebaseDB = getInstance()

            val chatsFromDB = firebaseDB.collection(CHATS_PREFS).get().await()

            val UIDs = mutableListOf<ChatUIDItem>()

            chatsFromDB.forEach {
                UIDs.add(
                    ChatUIDItem(
                        it.reference.id,
                        it.get("displayUsername").toString()
                    )
                )
            }

            return UIDs

        }

        suspend fun getChats(): MutableList<Chatitem> {
            val firebaseDB = getInstance()

            // val chatsFromDB = firebaseDB.collection("chats").get().await()

            val start = GregorianCalendar(TimeZone.getDefault())
            start.add(Calendar.DAY_OF_MONTH, -2)

            val end = GregorianCalendar(TimeZone.getDefault())
            end.add(Calendar.DAY_OF_MONTH, 1)

            // val timestamp = FieldValue.serverTimestamp()

            // Get chats between a timestamp
            val chatsFromDB = firebaseDB.collection("chats")
                .whereGreaterThan("timestamp", start.time)
                .whereLessThan("timestamp", end.time)
                .get().await()

            val ids = getUsersIDs()

            val chats = mutableListOf<Chatitem>()

            chatsFromDB.forEach {

                Log.d("data", it.toString())

                val userId = it.get("userUID")
                val displayName = ids.find { it.uid.equals(userId) }?.displayName

                chats.add(
                    Chatitem(
                        displayName ?: "",
                        it.get("message").toString()
                    )
                )
            }

            Log.d("data","UIDS")

            ids.forEach {
                Log.d("data", it.toString())
            }

            return chats

        }

        suspend fun addChat(userUID: String, message: String) {
            val firebaseDB = getInstance()

            // Get UTC timestamp from firebase
            val timestamp = FieldValue.serverTimestamp()

            firebaseDB.collection("chats").add(mapOf(
                    "userUID" to userUID,
                    "message" to message,
                    "timestamp" to timestamp
                )
            ).await()

        }

        suspend fun saveUserPreferences(user: FirebaseUser) {
            val firebaseDB = getInstance()

            firebaseDB.collection(CHATS_PREFS).document(user.uid).set(
                mapOf(
                    "displayUsername" to user.displayName
                )
            ).await()
        }

    }
}