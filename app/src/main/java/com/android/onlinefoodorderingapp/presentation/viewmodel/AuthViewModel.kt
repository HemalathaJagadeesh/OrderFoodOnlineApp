package com.android.onlinefoodorderingapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.onlinefoodorderingapp.domain.usecase.auth.SendOtpUseCase
import com.android.onlinefoodorderingapp.domain.usecase.auth.VerifyOtpUsecase
import com.android.onlinefoodorderingapp.domain.util.AuthUiState
import com.android.onlinefoodorderingapp.presentation.screens.auth.AuthValidator
import com.android.onlinefoodorderingapp.data.local.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.android.onlinefoodorderingapp.domain.util.toUserMessage
import com.android.onlinefoodorderingapp.presentation.util.AppConstants
import kotlinx.coroutines.Dispatchers

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val sendOtpUseCase: SendOtpUseCase,
    private val verifyOtpUsecase: VerifyOtpUsecase,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _state = MutableStateFlow<AuthUiState>(AuthUiState.EnterPhone())
    val state = _state.asStateFlow()

    private var timerJob: Job? = null

    /* ---------------- PHONE ---------------- */

    fun onPhoneChange(phone: String) {
        val current = _state.value as? AuthUiState.EnterPhone ?: return

        val clean = phone.filter { it.isDigit() }.take(AppConstants.PHONE_LENGTH)

        _state.update {
            current.copy(
                phone = clean,
                error = null
            )
        }
    }

    fun sendOtp() {
        val current = _state.value as? AuthUiState.EnterPhone ?: return

        val error = AuthValidator.validatePhone(current.phone)
        if (error != null) {
            _state.update { current.copy(error = error) }
            return
        }

        if (current.isLoading) return
        viewModelScope.launch {

            _state.update { current.copy(isLoading = true, error = null) }

            val result = sendOtpUseCase(current.phone)

            result.fold(
                onSuccess = {
                    startTimer(current.phone)

                    _state.value = AuthUiState.OtpSent(
                        phone = current.phone,
                        timer = 30
                    )
                },
                onFailure = {
                    _state.update {
                        current.copy(
                            isLoading = false,
                            error = error?.toUserMessage() ?: "Failed to send OTP"
                        )
                    }
                }
            )
        }
    }

    /* ---------------- OTP ---------------- */

    fun onOtpChange(otp: String) {
        val current = _state.value as? AuthUiState.OtpSent ?: return

        val clean = otp.filter { it.isDigit() }.take(4)

        _state.update {
            current.copy(
                otp = clean,
                error = null
            )
        }
    }

    fun verifyOtp() {
        val current = _state.value as? AuthUiState.OtpSent ?: return

        val error = AuthValidator.validateOtp(current.otp)
        if (error != null) {
            _state.update { current.copy(error = error) }
            return
        }

        if (current.isLoading) return

        viewModelScope.launch {

            _state.update { current.copy(isLoading = true, error = null) }

            val result = verifyOtpUsecase(current.phone, current.otp)

            result.fold(
                onSuccess = { user ->

                    viewModelScope.launch(Dispatchers.IO) {
                        sessionManager.saveSession(
                            isLoggedIn = true,
                            phone = user.phone
                        )
                    }

                    _state.value = AuthUiState.LoggedIn(user)
                },
                onFailure = {
                    _state.update {
                        current.copy(
                            isLoading = false,
                            error = error?.toUserMessage() ?: "Invalid OTP"
                        )
                    }
                }
            )
        }
    }

    /* ---------------- TIMER ---------------- */

    private fun startTimer(phone: String) {
        timerJob?.cancel()

        timerJob = viewModelScope.launch {
            for (i in 30 downTo 0) {


                val current = _state.value as? AuthUiState.OtpSent ?: return@launch

                _state.value = current.copy(timer = i)
                delay(1000)
            }
        }
    }

    fun resendOtp() {
        val current = _state.value as? AuthUiState.OtpSent ?: return

        if (current.timer > 0 || current.isLoading) return

        viewModelScope.launch {

            _state.update { current.copy(isLoading = true, error = null) }

            val result = sendOtpUseCase(current.phone)

            result.fold(
                onSuccess = {
                    startTimer(current.phone)

                    _state.value = current.copy(
                        otp = "",
                        timer = 30,
                        isLoading = false
                    )
                },
                onFailure = { error ->
                    _state.update {
                        current.copy(
                            isLoading = false,
                            error = error.toUserMessage()
                        )
                    }
                }
            )
        }
    }

    fun onOtpBack() {
        val currentState = _state.value
        if (currentState is AuthUiState.OtpSent) {
            _state.value = AuthUiState.EnterPhone(
                phone = currentState.phone
            )
        }
    }

}