package com.android.onlinefoodorderingapp.domain.repository.auth

interface UserRepository {
    suspend fun createUser(userId: String, phone: String)
}