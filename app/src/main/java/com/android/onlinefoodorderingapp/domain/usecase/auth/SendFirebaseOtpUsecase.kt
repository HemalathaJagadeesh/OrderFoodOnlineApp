package com.android.onlinefoodorderingapp.domain.usecase.auth

import android.app.Activity
import com.android.onlinefoodorderingapp.domain.model.auth.AuthError
import com.android.onlinefoodorderingapp.domain.model.auth.SendOtpResult
import com.android.onlinefoodorderingapp.domain.repository.auth.AuthRepository
import com.android.onlinefoodorderingapp.presentation.util.AppConstants
import javax.inject.Inject

class SendFirebaseOtpUsecase @Inject constructor(
    private val repo: AuthRepository
) {

    suspend operator fun invoke(
        phone: String,
        countryCode: String,
        activityProvider: () -> Activity
    ): SendOtpResult {

        // Validation
        if (phone.length != AppConstants.PHONE_LENGTH) {
            return SendOtpResult.Failure(AuthError.InvalidPhone)
        }

        // Call repository
        return repo.sendOtp(
            phone = phone,
            countryCode = countryCode,
            activityProvider  = activityProvider
        )
    }
}