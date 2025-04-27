package com.orb.bmdfoods.repository

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepository @Inject constructor() {
    val currentUser: FirebaseUser? = Firebase.auth.currentUser

    fun hasUser(): Boolean = Firebase.auth.currentUser != null

    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    suspend fun createUser(
        email: String,
        password: String,
        onComplete: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {
        try {
            Firebase.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    onComplete(task.isSuccessful)
                }.await()
        } catch (e: Exception) {
            onComplete(false)
        }
    }

    suspend fun login(
        email: String,
        password: String,
        onComplete: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {
        try {
            Firebase.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    onComplete(task.isSuccessful)
                }.await()
        } catch (e: Exception) {
            onComplete(false)
        }
    }

    fun signOut() = Firebase.auth.signOut()
}