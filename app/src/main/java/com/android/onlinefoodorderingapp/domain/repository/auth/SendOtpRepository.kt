package com.android.onlinefoodorderingapp.domain.repository.auth

interface SendOtpRepository {
    suspend fun sendOtp(phoneNumber: String): Result<Unit>
}