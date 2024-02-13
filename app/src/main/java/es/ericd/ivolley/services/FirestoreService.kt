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
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.Date

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

                val time = it.get("timestamp") as Timestamp

                data.add(
                    Chatitem(
                        username = it.get("username").toString(),
                        message = it.get("message").toString(),
                        timestamp = time
                    )
                )

                Log.d("time", it.get("timestamp").toString())

            }


            return data

        }

        suspend fun addChat(message: String) {
            val firebaseDB = getInstance()

            // Get UTC timestamp from firebase
            val firebaseUser = FirebaseService.getCurrentUser()?.displayName ?: FirebaseService.getCurrentUser()?.email.toString()

            firebaseDB.collection(CHATS).add(mapOf(
                    "username" to firebaseUser,
                    "message" to message,
                    "timestamp" to Timestamp.now()
                )
            ).await()

            ApiService.sendNotificationToAllDevices()

        }

        suspend fun updateUser(prevUsername: String, newUser: String) {
            val data = getInstance().collection(CHATS).whereEqualTo("username", prevUsername).get().await()

            data.forEach {
                it.reference.update("username", newUser).await()
            }
        }

        suspend fun getNotesFlow(): Flow<List<Chatitem>> = callbackFlow{
            try {
                //Get the reference of note collection
                val notesCollection = getInstance().collection(CHATS)

                // Registers callback to firestore, which will be called on collection updates
                val subscription = notesCollection?.addSnapshotListener { snapshot, _ ->
                    if (snapshot != null) {
                        // Format the results & send to the flow! Consumers will get the collection updated
                        val chat = mutableListOf<Chatitem>()

                        snapshot.forEach{
                            chat.add(
                                Chatitem(
                                    username = it.get("username").toString(),
                                    message = it.get("message").toString(),
                                    timestamp = it.get("timestamp") as Timestamp
                                )
                            )
                        }

                        chat.sortBy {
                            it.timestamp
                        }

                        trySend(chat)
                    }
                }

                // The callback inside awaitClose will be executed when the flow is
                // either closed or cancelled.
                // In this case, remove the callback from Firestore
                awaitClose { subscription?.remove() }

            } catch (e: Throwable) {
                // If Firebase cannot be initialized, close the stream of data
                // flow consumers will stop collecting and the coroutine will resume
                close(e)
            }
        }

    }
}