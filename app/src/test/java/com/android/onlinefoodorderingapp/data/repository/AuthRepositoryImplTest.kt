package com.android.onlinefoodorderingapp.data.repository

import android.app.Activity
import com.android.onlinefoodorderingapp.data.repository.auth.AuthRepositoryImpl
import com.android.onlinefoodorderingapp.domain.model.auth.AuthError
import com.android.onlinefoodorderingapp.domain.model.auth.AuthResult
import com.android.onlinefoodorderingapp.domain.model.auth.SendOtpResult
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.CapturingSlot
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class AuthRepositoryImplTest : BehaviorSpec({
/*

    lateinit var auth: FirebaseAuth
    lateinit var repository: AuthRepositoryImpl

    beforeTest {
        auth = mockk(relaxed = true)
        repository = AuthRepositoryImpl(auth)
    }

    afterTest {
        unmockkAll()
    }

    given("AuthRepositoryImpl") {

        // VERIFY OTP SUCCESS
        `when`("verifyOtp succeeds") {

            val verificationId = "verif_123"
            val code = "123456"

            then("it should return Success with userId") {
                runTest {

                    val credential = mockk<PhoneAuthCredential>()
                    val firebaseUser = mockk<FirebaseUser>()
                    val authResult = mockk<com.google.firebase.auth.AuthResult>()
                    val task = mockk<Task<com.google.firebase.auth.AuthResult>>()

                    mockkStatic(PhoneAuthProvider::class)

                    every {
                        PhoneAuthProvider.getCredential(verificationId, code)
                    } returns credential

                    every { firebaseUser.uid } returns "user_123"
                    every { authResult.user } returns firebaseUser

                    coEvery { auth.signInWithCredential(credential) } returns task
                    coEvery { task.await() } returns authResult

                    val result = repository.verifyOtp(verificationId, code)

                    result shouldBe AuthResult.Success("user_123")
                }
            }
        }

        // VERIFY OTP FAILURE
        `when`("verifyOtp fails") {

            val verificationId = "verif_123"
            val code = "123456"

            then("it should return Failure") {
                runTest {

                    val credential = mockk<PhoneAuthCredential>()
                    val task = mockk<Task<com.google.firebase.auth.AuthResult>>()

                    mockkStatic(PhoneAuthProvider::class)

                    every {
                        PhoneAuthProvider.getCredential(verificationId, code)
                    } returns credential

                    coEvery { auth.signInWithCredential(credential) } returns task
                    coEvery { task.await() } throws FirebaseNetworkException("error")

                    val result = repository.verifyOtp(verificationId, code)

                    result shouldBe AuthResult.Failure(AuthError.Network)
                }
            }
        }

       *//* //SEND OTP SUCCESS
        `when`("sendOtp succeeds with code sent") {

            then("it should return Success with verificationId") {
                runTest {

                    val activity = mockk<Activity>()
                    val activityProvider = { activity }

                    mockkStatic(PhoneAuthProvider::class)

                    val slot = slot<PhoneAuthOptions>()

                    every {
                        PhoneAuthProvider.verifyPhoneNumber(capture(slot))
                    } answers {

                        val callbacks = slot.captured.callbacks

                        callbacks.onCodeSent(
                            "verification_123",
                            mockk<PhoneAuthProvider.ForceResendingToken>()
                        )
                    }

                    val result = repository.sendOtp(
                        phone = "9999999999",
                        countryCode = "+91",
                        activityProvider = activityProvider
                    )

                    result shouldBe SendOtpResult.Success("verification_123")
                }
            }
        }

        //  SEND OTP FAILURE
        `when`("sendOtp fails") {

            then("it should return Failure") {
                runTest {

                    val activity = mockk<Activity>()
                    val activityProvider = { activity }

                    mockkStatic(PhoneAuthProvider::class)

                    val slot = slot<PhoneAuthOptions>()

                    every {
                        PhoneAuthProvider.verifyPhoneNumber(capture(slot))
                    } answers {

                        val callbacks = slot.captured.getCallbacks()

                        callbacks.onVerificationFailed(
                            FirebaseNetworkException("error")
                        )
                    }

                    val result = repository.sendOtp(
                        phone = "9999999999",
                        countryCode = "+91",
                        activityProvider = activityProvider
                    )

                    result shouldBe SendOtpResult.Failure(AuthError.Network)
                }
            }
        }*//*
    }*/


})


    /**
     *  Safe builder mocking (NO Android execution)
     */
    fun mockPhoneAuthBuilder(
        slot: CapturingSlot<PhoneAuthProvider.OnVerificationStateChangedCallbacks>
    ) {
        val builder = mockk<PhoneAuthOptions.Builder>(relaxed = true)

        every { PhoneAuthOptions.newBuilder(any()) } returns builder
        every { builder.setPhoneNumber(any()) } returns builder
        every { builder.setTimeout(any(), any()) } returns builder
        every { builder.setActivity(any()) } returns builder
        every { builder.setCallbacks(capture(slot)) } returns builder
        every { builder.build() } returns mockk(relaxed = true)
    }

