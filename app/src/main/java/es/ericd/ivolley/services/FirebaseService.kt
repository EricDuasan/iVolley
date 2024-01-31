package es.ericd.ivolley.services

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirebaseService {
    companion object {
        private val auth: FirebaseAuth by lazy { Firebase.auth }

        suspend fun logIn(email: String, password: String): FirebaseUser? {
            return try {
                val authResult = auth.signInWithEmailAndPassword(email, password).await()
                authResult.user
            } catch (e: Exception) {
                null
            }
        }

        fun logOut() = auth.signOut()

        fun getCurrentUser() : FirebaseUser? = auth.currentUser

    }

}