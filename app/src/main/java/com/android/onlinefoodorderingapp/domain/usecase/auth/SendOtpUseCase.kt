package com.android.onlinefoodorderingapp.domain.usecase.auth

import com.android.onlinefoodorderingapp.domain.repository.auth.SendOtpRepository
import javax.inject.Inject

class SendOtpUseCase @Inject constructor(private val sendOtpRepository: SendOtpRepository) {
    suspend operator fun invoke(phoneNumber: String): Result<Unit> {
        return sendOtpRepository.sendOtp(phoneNumber)
    }
}