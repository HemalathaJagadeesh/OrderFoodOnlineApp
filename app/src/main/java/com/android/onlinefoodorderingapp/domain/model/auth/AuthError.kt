package com.android.onlinefoodorderingapp.domain.model.auth


sealed class AuthError {
    data object InvalidOtp : AuthError()
    data object Network : AuthError()
    data object TooManyRequests : AuthError()
    data object InvalidPhone : AuthError()
    data object Unknown : AuthError()
}
