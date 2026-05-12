package com.android.onlinefoodorderingapp.domain.model.auth

import com.google.firebase.auth.PhoneAuthCredential


sealed class SendOtpResult {

    data class Success(
        val verificationId: String
    ) : SendOtpResult()

    data class Failure(
        val error: AuthError
    ) : SendOtpResult()


}
