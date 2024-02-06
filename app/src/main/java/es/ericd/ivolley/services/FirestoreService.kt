package es.ericd.ivolley.services

import com.google.firebase.firestore.FirebaseFirestore
import es.ericd.ivolley.dataclases.Chatitem
import kotlinx.coroutines.tasks.await

class FirestoreService {
    companion object {
        private var firebaseDB : FirebaseFirestore? = null

        private fun getInstance(): FirebaseFirestore {
            if (firebaseDB == null) {
                firebaseDB = FirebaseFirestore.getInstance()
            }

            return firebaseDB!!

        }

        suspend fun getChats(): MutableList<Chatitem> {
            val firebaseDB = getInstance()

            val chatsFromDB = firebaseDB.collection("chats").get().await()

            val chats = mutableListOf<Chatitem>()

            chatsFromDB.forEach {
                chats.add(
                    Chatitem(
                        it.get("username").toString(),
                        it.get("message").toString()
                    )
                )
            }

            return chats

        }

        suspend fun addChat(chat: Chatitem) {
            val firebaseDB = getInstance()

            firebaseDB.collection("chats").add(mapOf(
                    "username" to chat.username,
                    "message" to chat.message
                )
            ).await()

        }

    }
}