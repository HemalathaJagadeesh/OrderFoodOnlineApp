package com.android.onlinefoodorderingapp.data.repository.auth

import android.app.Activity
import android.util.Log
import com.android.onlinefoodorderingapp.domain.model.auth.AuthError
import com.android.onlinefoodorderingapp.domain.model.auth.AuthResult
import com.android.onlinefoodorderingapp.domain.model.auth.SendOtpResult
import com.android.onlinefoodorderingapp.domain.repository.auth.AuthRepository
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthRepositoryImpl  @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun sendOtp(
        phone: String,
        countryCode: String,
        activityProvider: () -> Activity
    ): SendOtpResult {
        return suspendCancellableCoroutine { continuation ->
            val activity = activityProvider()
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber("$countryCode$phone")
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        auth.signInWithCredential(credential)


                    }

                    override fun onVerificationFailed(e: com.google.firebase.FirebaseException) {
                        //onError(e.message ?: AppConstants.OTP_FAILED)

                        val error = mapError(e)

                        if (continuation.isActive) {
                            continuation.resume(
                                SendOtpResult.Failure(error),  //convert to domain-safe error
                                onCancellation = null
                            )
                        }


                    }

                    override fun onCodeSent(
                        verificationId: String,
                        token: PhoneAuthProvider.ForceResendingToken
                    ) {
                       // onCodeSent(verificationId)

                        if (continuation.isActive) {
                            continuation.resume(
                                SendOtpResult.Success(verificationId),
                                onCancellation = null
                            )
                        }

                    }
                })
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }

    override suspend fun verifyOtp(
        verificationId: String,
        code: String
    ): AuthResult {
        return try {
            val credential = PhoneAuthProvider.getCredential(verificationId, code)
            val result = auth.signInWithCredential(credential).await()

            /*val userId = result.user?.uid ?: AppConstants.EMPTY_STRING
            Result.success(userId)*/
            AuthResult.Success(result.user?.uid.orEmpty())

        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "verifyOtp: ${e.message}")
            AuthResult.Failure(mapError(e))
           // Result.failure(e)
        }
    }
}

private fun mapError(e: Exception): AuthError {
    return when (e) {
        is FirebaseAuthInvalidCredentialsException -> AuthError.InvalidOtp
        is FirebaseTooManyRequestsException -> AuthError.TooManyRequests
        is FirebaseNetworkException -> AuthError.Network
        else -> AuthError.Unknown
    }
}
