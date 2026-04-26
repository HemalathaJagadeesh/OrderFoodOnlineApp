package com.android.onlinefoodorderingapp.domain.util

import com.android.onlinefoodorderingapp.domain.model.User

sealed class AuthUiState {

    //data object Idle : AuthUiState()

   // data object isLoading : AuthUiState()

    data class EnterPhone(
        val phone: String = "",
        val error: String? = null,
        val isLoading: Boolean = false
    ) : AuthUiState()

    data class OtpSent(
        val phone: String,
        val otp: String = "",
        val timer: Int = 30,
        val error: String? = null,
        val isLoading: Boolean = false
    ) : AuthUiState()

    data class LoggedIn(val user: User) : AuthUiState()

    //data class Error(val message: String) : AuthUiState()
}