package com.android.onlinefoodorderingapp.domain.util

fun Throwable.toUserMessage(): String {
    return when (this) {
        is java.net.UnknownHostException -> "No internet connection"
        is java.net.SocketTimeoutException -> "Request timed out"
        else -> this.message ?: "Something went wrong"
    }
}