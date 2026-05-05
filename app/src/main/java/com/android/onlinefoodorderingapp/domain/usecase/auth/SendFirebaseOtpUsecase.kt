package com.android.onlinefoodorderingapp.domain.usecase.auth

import android.app.Activity
import com.android.onlinefoodorderingapp.domain.repository.auth.AuthRepository
import javax.inject.Inject

class SendFirebaseOtpUsecase @Inject constructor(
    private val repo: AuthRepository
) {

    operator fun invoke(
        phone: String,
        countryCode:String,
        activity: Activity,
        onCodeSent: (String) -> Unit,
        onError: (String) -> Unit
    ) {

        if (phone.length != 10) {
            onError("Invalid phone number")
            return
        }

        repo.sendOtp(phone, countryCode,activity, onCodeSent, onError)
    }

}