package com.android.onlinefoodorderingapp.presentation.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.onlinefoodorderingapp.domain.usecase.auth.SendFirebaseOtpUsecase
import com.android.onlinefoodorderingapp.domain.usecase.auth.VerifyFirebaseOtpUsecase
import com.android.onlinefoodorderingapp.presentation.util.AuthUiState1
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirebaseAuthViewModel  @Inject constructor(
    private val sendOtpUseCase: SendFirebaseOtpUsecase,
    private val verifyOtpUseCase: VerifyFirebaseOtpUsecase
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthUiState1>(AuthUiState1.Login)
    val authState = _authState.asStateFlow()


    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()


    var verificationId = ""
    var phoneNumber = ""

    fun sendOtp(activity: Activity, phone: String) {

        phoneNumber = phone
        _errorMessage.value = null
        _authState.value = AuthUiState1.Loading
        viewModelScope.launch {
            sendOtpUseCase(
                phone = phone,
                countryCode = "+91",
                activity = activity,

                onCodeSent = { verification ->
                    verificationId = verification
                    _errorMessage.value = null
                    _authState.value = AuthUiState1.Otp
                },

                onError = { error ->
                    _errorMessage.value = error
                    _authState.value = AuthUiState1.Login
                }
            )
        }
    }
    fun verifyOtp(code: String) {
        viewModelScope.launch {
            _authState.value = AuthUiState1.Loading

            val result = verifyOtpUseCase(
                verificationId,
                code
            )


            result.fold(
                onSuccess = {
                    _errorMessage.value = null
                    _authState.value = AuthUiState1.Authenticated
                },
                onFailure = { error ->
                    _errorMessage.value = error.message ?: "Invalid OTP"
                    _authState.value = AuthUiState1.Otp // ✅ stay on OTP screen
                }
            )


        }
    }
}