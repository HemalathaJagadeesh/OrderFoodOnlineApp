package com.android.onlinefoodorderingapp.data.repository.auth

import com.android.onlinefoodorderingapp.domain.repository.auth.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl(
    private val firestore: FirebaseFirestore
) : UserRepository {

    override suspend fun createUser(userId: String, phone: String) {

        val user = mapOf(
            "phone" to phone,
            "createdAt" to System.currentTimeMillis()
        )

        firestore.collection("users")
            .document(userId)
            .set(user)
            .await()
    }
}