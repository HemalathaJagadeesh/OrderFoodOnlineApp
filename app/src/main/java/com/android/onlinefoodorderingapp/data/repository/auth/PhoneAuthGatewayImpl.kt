package com.android.onlinefoodorderingapp.data.repository.auth

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class PhoneAuthGatewayImpl @Inject constructor(
    private val auth: FirebaseAuth
) : PhoneAuthGateway {

    override fun verifyPhoneNumber(
        phone: String,
        countryCode: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        /*val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("$countryCode$phone")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)*/
    }
}
