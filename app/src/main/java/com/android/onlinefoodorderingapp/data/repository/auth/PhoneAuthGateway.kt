package com.android.onlinefoodorderingapp.data.repository.auth

import android.app.Activity
import com.google.firebase.auth.PhoneAuthProvider

interface PhoneAuthGateway {

    fun verifyPhoneNumber(
        phone: String,
        countryCode: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    )

}