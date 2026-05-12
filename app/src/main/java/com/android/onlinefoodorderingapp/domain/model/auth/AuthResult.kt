package com.android.onlinefoodorderingapp.domain.model.auth


sealed class AuthResult {
    data class Success(val userId: String) : AuthResult()
    data class Failure(val error: AuthError) : AuthResult()
}
