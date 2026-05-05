package com.android.onlinefoodorderingapp.data.repository.auth

import android.app.Activity
import com.android.onlinefoodorderingapp.domain.repository.auth.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthRepositoryImpl  @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override fun sendOtp(
        phone: String,
        countryCode: String,
        activity: Activity,
        onCodeSent: (String) -> Unit,
        onError: (String) -> Unit
    ) {

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("$countryCode$phone")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    auth.signInWithCredential(credential)
                }

                override fun onVerificationFailed(e: com.google.firebase.FirebaseException) {
                    onError(e.message ?: "OTP failed")
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    onCodeSent(verificationId)
                }
            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override suspend fun verifyOtp(
        verificationId: String,
        code: String
    ): Result<String> {
        return try {
            val credential = PhoneAuthProvider.getCredential(verificationId, code)
            val result = auth.signInWithCredential(credential).await()

            val userId = result.user?.uid ?: ""
            Result.success(userId)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}