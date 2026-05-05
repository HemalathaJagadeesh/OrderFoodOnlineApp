package com.android.onlinefoodorderingapp.presentation.util

sealed class LoginUiState {
    data object Idle : LoginUiState()
    data object Loading : LoginUiState()
    data object CodeSent : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}