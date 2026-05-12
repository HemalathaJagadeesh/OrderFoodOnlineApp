package com.android.onlinefoodorderingapp.domain.repository.auth

import android.app.Activity
import com.android.onlinefoodorderingapp.domain.model.auth.AuthResult
import com.android.onlinefoodorderingapp.domain.model.auth.SendOtpResult

interface AuthRepository {
    suspend fun sendOtp(
        phone: String,
        countryCode: String,
        activityProvider: () -> Activity
    ): SendOtpResult

    suspend fun verifyOtp(
        verificationId: String,
        code: String
    ): AuthResult // returns userId
}