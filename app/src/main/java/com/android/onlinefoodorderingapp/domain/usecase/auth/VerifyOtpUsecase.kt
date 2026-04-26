package com.android.onlinefoodorderingapp.domain.usecase.auth

import com.android.onlinefoodorderingapp.domain.model.User
import com.android.onlinefoodorderingapp.domain.repository.auth.VerifyOtpRepository
import javax.inject.Inject

class VerifyOtpUsecase @Inject constructor(private val verifyOtpRepository: VerifyOtpRepository) {
    suspend operator fun invoke(
        phone: String,
        otp: String
    ): Result<User> {
        return verifyOtpRepository.verifyOtp(phone, otp)

    }
}