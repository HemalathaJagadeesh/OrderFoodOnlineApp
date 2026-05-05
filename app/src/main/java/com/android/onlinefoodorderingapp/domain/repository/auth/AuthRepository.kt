package com.android.onlinefoodorderingapp.domain.repository.auth

import android.app.Activity

interface AuthRepository {
    fun sendOtp(
        phone: String,
        countryCode: String,
        activity: Activity,
        onCodeSent: (verificationId: String) -> Unit,
        onError: (message: String) -> Unit
    )

    suspend fun verifyOtp(
        verificationId: String,
        code: String
    ): Result<String> // returns userId
}