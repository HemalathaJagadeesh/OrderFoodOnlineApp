package com.android.onlinefoodorderingapp.presentation.viewmodel

import com.android.onlinefoodorderingapp.domain.model.User
import com.android.onlinefoodorderingapp.domain.usecase.auth.SendOtpUseCase
import com.android.onlinefoodorderingapp.domain.usecase.auth.VerifyOtpUsecase
import com.android.onlinefoodorderingapp.domain.util.AuthUiState
import com.android.onlinefoodorderingapp.data.local.SessionManager
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi :: class)
class AuthViewModelTest : FunSpec({
    val sendOtpUseCase = mockk<SendOtpUseCase>()
    val verifyOtpUseCase = mockk<VerifyOtpUsecase>()
    val sessionManager = mockk<SessionManager>(relaxed = true)

    lateinit var viewModel: AuthViewModel
    val dispatcher = StandardTestDispatcher()

    beforeTest {
        viewModel = AuthViewModel(sendOtpUseCase, verifyOtpUseCase, sessionManager)
        Dispatchers.setMain(dispatcher)
    }

    afterTest {
        Dispatchers.resetMain()
     }


    test("onPhoneChange should filter non digits and limit to 10") {
        viewModel.onPhoneChange("98ab76@543210")
        val state = viewModel.state.value as AuthUiState.EnterPhone

        state.phone shouldBe "9876543210"
        state.error shouldBe null

    }

    test("sendOtp should set error for invalid phone") {
        viewModel.onPhoneChange("1234")
        viewModel.sendOtp()
        val state = viewModel.state.value as AuthUiState.EnterPhone

        state.error shouldNotBe null
    }

    test("sendOtp success should move to OtpSent state") {
        runTest {

            coEvery { sendOtpUseCase(any()) } returns Result.success(Unit)

            viewModel.onPhoneChange("9876543210")
            viewModel.sendOtp()
            runCurrent()

            when (val state = viewModel.state.value) {
                is AuthUiState.OtpSent -> {
                    state.timer shouldBe 30
                }

                else -> error("Expected OtpSent but was $state")
            }
        }
    }

    test("sendOtp failure should show error") {
        runTest {

            coEvery { sendOtpUseCase(any()) } returns Result.failure(Exception("API Error"))

            viewModel.onPhoneChange("9876543210")
            viewModel.sendOtp()

            advanceUntilIdle()

            val state = viewModel.state.value as AuthUiState.EnterPhone

            state.isLoading shouldBe false
            state.error shouldNotBe null
        }
    }

    test("onOtpChange should clean input and limit to 4 digits") {
        runTest {

            coEvery { sendOtpUseCase(any()) } returns Result.success(Unit)

            viewModel.onPhoneChange("9876543210")
            viewModel.sendOtp()
            advanceUntilIdle()

            viewModel.onOtpChange("12ab34")

            val state = viewModel.state.value as AuthUiState.OtpSent

            state.otp shouldBe "1234"
        }
    }

    test("verifyOtp success should login user and save session") {
        runTest {

            val user = User(phone = "9876543210", id = 1, name = "Test User")

            coEvery { sendOtpUseCase(any()) } returns Result.success(Unit)
            coEvery { verifyOtpUseCase(any(), any()) } returns Result.success(user)

            viewModel.onPhoneChange("9876543210")
            viewModel.sendOtp()

            runCurrent() // ✅ run sendOtp coroutine (not full timer)

            viewModel.onOtpChange("1234")
            viewModel.verifyOtp()

            runCurrent() // ✅ run verifyOtp coroutine

            when (val state = viewModel.state.value) {
                is AuthUiState.LoggedIn -> {
                    state.user.phone shouldBe "9876543210"
                }
                else -> error("Expected LoggedIn but was $state")
            }

            coVerify {
                sessionManager.saveSession(true, "9876543210")
            }
        }
    }

    test("verifyOtp failure should show error") {
        runTest {

            coEvery { sendOtpUseCase(any()) } returns Result.success(Unit)
            coEvery { verifyOtpUseCase(any(), any()) } returns Result.failure(Exception())

            viewModel.onPhoneChange("9876543210")
            viewModel.sendOtp()
            advanceUntilIdle()

            viewModel.onOtpChange("1234")
            viewModel.verifyOtp()

            advanceUntilIdle()

            val state = viewModel.state.value as AuthUiState.OtpSent

            state.isLoading shouldBe false
            state.error shouldNotBe null
        }
    }

    test("timer should decrease from 30") {
        runTest {

            coEvery { sendOtpUseCase(any()) } returns Result.success(Unit)

            viewModel.onPhoneChange("9876543210")
            viewModel.sendOtp()

            advanceTimeBy(1000) // 1 second
            runCurrent()

            val state = viewModel.state.value as AuthUiState.OtpSent

            state.timer shouldBe 29
        }
    }

    test("resendOtp should reset timer and clear otp") {
        runTest {

            coEvery { sendOtpUseCase(any()) } returns Result.success(Unit)

            viewModel.onPhoneChange("9876543210")
            viewModel.sendOtp()
            advanceUntilIdle()

            // expire timer
            advanceTimeBy(31000)
            runCurrent()

            viewModel.resendOtp()

            // ✅ run ONLY the resend coroutine (not timer delay)
            runCurrent()

            val state = viewModel.state.value as AuthUiState.OtpSent

            state.timer shouldBe 30
            state.otp shouldBe ""
        }
    }

})