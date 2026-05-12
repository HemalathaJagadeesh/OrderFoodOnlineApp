package com.android.onlinefoodorderingapp.domain.usecase.auth

import com.android.onlinefoodorderingapp.domain.model.auth.AuthResult
import com.android.onlinefoodorderingapp.domain.repository.auth.AuthRepository
import javax.inject.Inject

class VerifyFirebaseOtpUsecase @Inject constructor(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(
        verificationId: String,
        code: String
    ): AuthResult {
        return repo.verifyOtp(verificationId, code)
    }
}