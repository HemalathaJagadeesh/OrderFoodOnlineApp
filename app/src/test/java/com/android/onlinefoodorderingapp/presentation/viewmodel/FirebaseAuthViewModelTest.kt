package com.android.onlinefoodorderingapp.presentation.viewmodel

import android.app.Activity
import app.cash.turbine.test
import com.android.onlinefoodorderingapp.data.local.SessionManager
import com.android.onlinefoodorderingapp.domain.model.auth.AuthError
import com.android.onlinefoodorderingapp.domain.model.auth.AuthResult
import com.android.onlinefoodorderingapp.domain.model.auth.SendOtpResult
import com.android.onlinefoodorderingapp.domain.usecase.auth.SendFirebaseOtpUsecase
import com.android.onlinefoodorderingapp.domain.usecase.auth.VerifyFirebaseOtpUsecase
import com.android.onlinefoodorderingapp.presentation.util.AuthUiState1
import com.android.onlinefoodorderingapp.presentation.util.toUiMessage
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class FirebaseAuthViewModelTest : BehaviorSpec({

    //  Mocks
    val sendOtpUseCase = mockk<SendFirebaseOtpUsecase>()
    val verifyOtpUseCase = mockk<VerifyFirebaseOtpUsecase>()
    val sessionManager = mockk<SessionManager>(relaxed = true)
    val activity = mockk<Activity>(relaxed = true)

    val dispatcher = StandardTestDispatcher()
    lateinit var viewModel: FirebaseAuthViewModel

    beforeTest {
        Dispatchers.setMain(dispatcher)

        viewModel = FirebaseAuthViewModel(
            sendOtpUseCase,
            verifyOtpUseCase,
            sessionManager
        )
    }

    afterTest {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    //  SEND OTP
    given("sendOtp") {

        `when`("API returns success") {

            then("should emit Login -> Loading -> Otp") {

                coEvery {
                    sendOtpUseCase(any(), any(), any())
                } returns SendOtpResult.Success("vid")

                viewModel.authState.test {
                    awaitItem() shouldBe AuthUiState1.Login
                    viewModel.sendOtp({ activity }, "999")
                    awaitItem() shouldBe AuthUiState1.Loading
                    dispatcher.scheduler.runCurrent()
                    awaitItem() shouldBe AuthUiState1.Otp
                    cancelAndIgnoreRemainingEvents()
                }
            }
        }



            `when`("API returns failure") {

            val error = AuthError.Network

            beforeTest {
                coEvery {
                    sendOtpUseCase(any(), any(), any())
                } returns SendOtpResult.Failure(error)
            }

            then("should emit Login -> Loading -> Login and show error") {

                val expectedMessage = error.toUiMessage()

                viewModel.authState.test {

                    awaitItem() shouldBe AuthUiState1.Login

                    viewModel.sendOtp({ activity }, "999")

                    awaitItem() shouldBe AuthUiState1.Loading

                    dispatcher.scheduler.runCurrent()

                    awaitItem() shouldBe AuthUiState1.Login

                    cancelAndIgnoreRemainingEvents()
                }

                dispatcher.scheduler.runCurrent()

                viewModel.errorMessage.value shouldBe expectedMessage
            }
        }

    //  VERIFY OTP
        given("verifyOtp") {

            val verificationId = "vid"

            beforeTest {
                viewModel.verificationId = verificationId

                coEvery {
                    verifyOtpUseCase(any(), any())
                } returns AuthResult.Success("uid123")
            }

            `when`("OTP verification succeeds") {

                then("should emit Login -> Loading -> Authenticated and save session") {

                    viewModel.authState.test {


                        awaitItem() shouldBe AuthUiState1.Login


                        viewModel.verifyOtp("1234")


                        dispatcher.scheduler.runCurrent()


                        awaitItem() shouldBe AuthUiState1.Loading
                        awaitItem() shouldBe AuthUiState1.Authenticated

                        cancelAndIgnoreRemainingEvents()
                    }

                    dispatcher.scheduler.runCurrent()

                    coVerify {
                        sessionManager.saveSession(true, any())
                    }

                    viewModel.errorMessage.value shouldBe null
                }
            }
        }

        `when`("OTP verification fails") {

            val error = AuthError.InvalidOtp

            beforeTest {
                viewModel.verificationId = "vid"

                coEvery {
                    verifyOtpUseCase(any(), any())
                } returns AuthResult.Failure(error)
            }

            then("should emit Loading -> Otp and show error") {

                val expectedMessage = error.toUiMessage()

                viewModel.authState.test {


                    awaitItem() shouldBe AuthUiState1.Login


                    viewModel.verifyOtp("0000")


                    dispatcher.scheduler.runCurrent()


                    awaitItem() shouldBe AuthUiState1.Loading
                    awaitItem() shouldBe AuthUiState1.Otp

                    cancelAndIgnoreRemainingEvents()
                }


                dispatcher.scheduler.runCurrent()

                viewModel.errorMessage.value shouldBe expectedMessage
            }
        }

    // EDGE / NEGATIVE CASES
        given("edge cases") {

            `when`("verifyOtp called without verificationId") {

                val error = AuthError.InvalidOtp

                beforeTest {
                    viewModel.verificationId = ""

                    coEvery {
                        verifyOtpUseCase(any(), any())
                    } returns AuthResult.Failure(error)
                }

                then("should not crash and fallback to Otp state with error") {

                    val expectedMessage = error.toUiMessage()

                    viewModel.authState.test {


                        awaitItem() shouldBe AuthUiState1.Login


                        viewModel.verifyOtp("1234")


                        dispatcher.scheduler.runCurrent()


                        awaitItem() shouldBe AuthUiState1.Loading
                        awaitItem() shouldBe AuthUiState1.Otp

                        cancelAndIgnoreRemainingEvents()
                    }

                    dispatcher.scheduler.runCurrent()

                    viewModel.errorMessage.value shouldBe expectedMessage
                }
            }
        }


        `when`("multiple sendOtp calls happen quickly") {

            beforeTest {
                coEvery {
                    sendOtpUseCase(any(), any(), any())
                } returns SendOtpResult.Success("vid")
            }

            then("should handle gracefully") {

                viewModel.sendOtp({ activity }, "999")
                viewModel.sendOtp({ activity }, "999")

                dispatcher.scheduler.runCurrent()

                viewModel.authState.value shouldBe AuthUiState1.Otp
            }
        }

        `when`("API returns empty verificationId") {

            beforeTest {
                coEvery {
                    sendOtpUseCase(any(), any(), any())
                } returns SendOtpResult.Success("")
            }

            then("should still transition to Otp safely") {

                viewModel.sendOtp({ activity }, "999")

                dispatcher.scheduler.runCurrent()

                viewModel.verificationId shouldBe ""
                viewModel.authState.value shouldBe AuthUiState1.Otp
            }
        }

        `when`("error exists before success call") {

            beforeTest {

                // Step 1: First create an error state via failure
                coEvery {
                    sendOtpUseCase(any(), any(), any())
                } returns SendOtpResult.Failure(AuthError.Network)

                viewModel.sendOtp({ activity }, "999")

                dispatcher.scheduler.runCurrent()

                // Step 2: Now next call returns success
                coEvery {
                    sendOtpUseCase(any(), any(), any())
                } returns SendOtpResult.Success("vid")
            }

            then("should clear previous error") {

                viewModel.authState.test {

                    // initial state already changed from previous call
                    awaitItem() // Login

                    // call again for success
                    viewModel.sendOtp({ activity }, "999")

                    // loading state
                    awaitItem() shouldBe AuthUiState1.Loading

                    dispatcher.scheduler.runCurrent()

                    // success → OTP
                    awaitItem() shouldBe AuthUiState1.Otp

                    cancelAndIgnoreRemainingEvents()
                }

                //  ensure coroutine finished
                dispatcher.scheduler.runCurrent()

                viewModel.errorMessage.value shouldBe null
            }
        }

        `when`("verifyOtp called before sendOtp happens") {

            val error = AuthError.Unknown

            beforeTest {
                viewModel.verificationId = ""

                coEvery {
                    verifyOtpUseCase("", "1234")
                } returns AuthResult.Failure(error)
            }

            then("should not crash and fallback to Otp") {

                val expectedMessage = error.toUiMessage()

                viewModel.authState.test {


                    awaitItem() shouldBe AuthUiState1.Login


                    viewModel.verifyOtp("1234")


                    dispatcher.scheduler.runCurrent()


                    awaitItem() shouldBe AuthUiState1.Loading
                    awaitItem() shouldBe AuthUiState1.Otp

                    cancelAndIgnoreRemainingEvents()
                }


                dispatcher.scheduler.runCurrent()

                viewModel.errorMessage.value shouldBe expectedMessage
            }
        }


    }
})