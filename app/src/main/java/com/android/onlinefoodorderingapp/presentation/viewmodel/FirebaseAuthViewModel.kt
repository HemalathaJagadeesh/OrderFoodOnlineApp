package com.android.onlinefoodorderingapp.presentation.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.onlinefoodorderingapp.data.local.SessionManager
import com.android.onlinefoodorderingapp.domain.model.auth.AuthResult
import com.android.onlinefoodorderingapp.domain.model.auth.SendOtpResult
import com.android.onlinefoodorderingapp.domain.usecase.auth.SendFirebaseOtpUsecase
import com.android.onlinefoodorderingapp.domain.usecase.auth.VerifyFirebaseOtpUsecase
import com.android.onlinefoodorderingapp.presentation.util.AppConstants
import com.android.onlinefoodorderingapp.presentation.util.AuthUiState1
import com.android.onlinefoodorderingapp.presentation.util.toUiMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirebaseAuthViewModel  @Inject constructor(
    private val sendOtpUseCase: SendFirebaseOtpUsecase,
    private val verifyOtpUseCase: VerifyFirebaseOtpUsecase,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthUiState1>(AuthUiState1.Login)
    val authState = _authState.asStateFlow()


    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()


    var verificationId = AppConstants.EMPTY_STRING
    private var phoneNumber = AppConstants.EMPTY_STRING

    fun sendOtp(activityProvider: () -> Activity, phone: String) {


        phoneNumber = phone
        _authState.value = AuthUiState1.Loading

        viewModelScope.launch {

            when (val result = sendOtpUseCase(phone, AppConstants.COUNTRY_CODE, activityProvider)) {

                is SendOtpResult.Success -> {
                    _errorMessage.value = null
                    verificationId = result.verificationId
                    _authState.value = AuthUiState1.Otp

                }

                is SendOtpResult.Failure -> {
                    _authState.value = AuthUiState1.Login
                    _errorMessage.value = result.error.toUiMessage()
                }
            }
        }

    }
    fun verifyOtp(code: String) {
        viewModelScope.launch {
            _authState.value = AuthUiState1.Loading

            when (val result = verifyOtpUseCase(verificationId, code)) {

                is AuthResult.Success -> {
                    _errorMessage.value = null
                    _authState.value = AuthUiState1.Authenticated
                    sessionManager.saveSession(
                        isLoggedIn = true,
                        phone = phoneNumber
                    )
                }

                is AuthResult.Failure -> {

                    _authState.value = AuthUiState1.Otp
                    _errorMessage.value = result.error.toUiMessage()

                }

            }
        }
    }
}