package com.android.onlinefoodorderingapp.domain.usecase.auth

import android.app.Activity
import com.android.onlinefoodorderingapp.domain.model.auth.AuthError
import com.android.onlinefoodorderingapp.domain.model.auth.SendOtpResult
import com.android.onlinefoodorderingapp.domain.repository.auth.AuthRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class SendFirebaseOtpUsecaseTest: BehaviorSpec( {

    val repo = mockk<AuthRepository>()
    val useCase = SendFirebaseOtpUsecase(repo)

    val mockActivity = mockk<Activity>(relaxed = true)
    val activityProvider: () -> Activity = {mockActivity}


    given("SendFirebaseOtpUsecase") {

        //  Invalid phone
        `when`("phone number is invalid") {
            val phone = "12345"
            val countryCode = "+91"

            then("returns InvalidPhone and repo not called") {
                runTest {
                    val result = useCase(phone, countryCode, activityProvider)

                    result shouldBe SendOtpResult.Failure(AuthError.InvalidPhone)

                    coVerify(exactly = 0) {
                        repo.sendOtp(any(), any(), any())
                    }
                }
            }
        }

        // Success from repo
        `when`("valid phone and repo returns success") {
            val phone = "9876543210"
            val countryCode = "+91"

            val verificationId = "verif_123"

            coEvery {
                repo.sendOtp(phone, countryCode, activityProvider)
            } returns SendOtpResult.Success(verificationId)

            then("returns success with correct verificationId") {
                runTest {
                    val result = useCase(phone, countryCode, activityProvider)

                    result shouldBe SendOtpResult.Success(verificationId)

                    coVerify(exactly = 1) {
                        repo.sendOtp(phone, countryCode, activityProvider)
                    }
                }
            }
        }

        //  Repo returns failure
        `when`("valid phone and repo returns failure") {
            val phone = "9876543210"
            val countryCode = "+91"

            val failure = SendOtpResult.Failure(AuthError.Network)

            coEvery {
                repo.sendOtp(phone, countryCode, activityProvider)
            } returns failure

            then("returns failure") {
                runTest {
                    val result = useCase(phone, countryCode, activityProvider)

                    result shouldBe failure

                    coVerify {
                        repo.sendOtp(phone, countryCode, activityProvider)
                    }
                }
            }
        }

        //  Repo throws exception
        `when`("repo throws exception") {
            val phone = "9876543210"
            val countryCode = "+91"

            val exception = RuntimeException("Firebase crash")

            coEvery {
                repo.sendOtp(phone, countryCode, activityProvider)
            } throws exception

            then("exception propagates") {
                runTest {
                    try {
                        useCase(phone, countryCode, activityProvider)
                    } catch (e: Exception) {
                        e shouldBe exception
                    }

                    coVerify {
                        repo.sendOtp(phone, countryCode, activityProvider)
                    }
                }
            }
        }

        //  Verify exact parameters + λ propagation
        `when`("valid input is passed") {
            val phone = "9876543210"
            val countryCode = "+1"

            coEvery {
                repo.sendOtp(any(), any(), any())
            } returns SendOtpResult.Success("abc123")

            then("correct params are passed to repository") {
                runTest {
                    useCase(phone, countryCode, activityProvider)

                    coVerify {
                        repo.sendOtp(
                            phone = phone,
                            countryCode = countryCode,
                            activityProvider = activityProvider
                        )
                    }
                }
            }
        }

        //  Multiple invocations
        /*`when`("called multiple times") {
            val phone = "9876543210"
            val countryCode = "+91"

            coEvery {
                repo.sendOtp(phone, countryCode, activityProvider)
            } returns SendOtpResult.Success("multi_123")

            then("repo should be called each time") {
                runTest {
                    useCase(phone, countryCode, activityProvider)
                    useCase(phone, countryCode, activityProvider)

                    coVerify(exactly = 2) {
                        repo.sendOtp(phone, countryCode, activityProvider)
                    }
                }
            }
        }*/
    }
    })