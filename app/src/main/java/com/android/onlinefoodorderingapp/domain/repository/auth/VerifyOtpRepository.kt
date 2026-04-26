package com.android.onlinefoodorderingapp.domain.repository.auth

import com.android.onlinefoodorderingapp.domain.model.User

interface VerifyOtpRepository {
    suspend fun verifyOtp(phone: String, otp: String): Result<User>
}