package com.android.onlinefoodorderingapp.domain.repository.auth

interface LogoutRepository {
    suspend fun logout()
}