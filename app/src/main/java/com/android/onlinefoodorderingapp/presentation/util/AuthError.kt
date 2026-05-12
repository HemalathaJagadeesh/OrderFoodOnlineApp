package com.android.onlinefoodorderingapp.presentation.util

import com.android.onlinefoodorderingapp.domain.model.auth.AuthError


fun AuthError.toUiMessage(): String {
    return when (this) {
        AuthError.InvalidOtp -> "Invalid OTP"
        AuthError.InvalidPhone -> "Invalid phone number"
        AuthError.Network -> "Check your internet connection"
        AuthError.TooManyRequests -> "Too many attempts. Try later"
        AuthError.Unknown -> "Something went wrong"
    }
}
