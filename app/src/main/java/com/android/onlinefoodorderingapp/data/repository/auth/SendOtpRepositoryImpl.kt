package com.android.onlinefoodorderingapp.data.repository.auth

import com.android.onlinefoodorderingapp.data.local.dao.UserDao
import com.android.onlinefoodorderingapp.domain.repository.auth.SendOtpRepository
import com.android.onlinefoodorderingapp.presentation.util.OtpManager

class SendOtpRepositoryImpl( private val otpManager: OtpManager):SendOtpRepository {
    override suspend fun sendOtp(phoneNumber: String): Result<Unit> {
        return try {

            // Generate OTP via OtpManager
            val otp = otpManager.generate(phoneNumber)

            println("Generated OTP: $otp")

            Result.success(Unit)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}