package com.android.onlinefoodorderingapp.domain.repository.auth

import com.android.onlinefoodorderingapp.domain.model.User

interface LoggedInUserRepository {
    suspend fun getLoggedInUser(phone: String): User?
}