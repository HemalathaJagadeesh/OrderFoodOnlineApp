package com.android.onlinefoodorderingapp.presentation.util

sealed class OtpUiState {
    data object Idle : OtpUiState()
    data object Loading : OtpUiState()
    data object Success : OtpUiState()
    data class Error(val message: String) : OtpUiState()
}