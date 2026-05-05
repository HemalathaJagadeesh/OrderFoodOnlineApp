package com.android.onlinefoodorderingapp.presentation.util

sealed class AuthUiState1 {
    object Login : AuthUiState1()
    object Otp : AuthUiState1()
    object Loading : AuthUiState1()
    object Authenticated : AuthUiState1()
    //data class Error(val message: String) : AuthUiState1()
}