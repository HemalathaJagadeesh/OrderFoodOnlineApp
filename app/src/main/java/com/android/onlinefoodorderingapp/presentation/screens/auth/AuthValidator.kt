package com.android.onlinefoodorderingapp.presentation.screens.auth

object AuthValidator {

    fun validatePhone(phone: String): String? {
        val trimmed = phone.trim()

        if (trimmed.isEmpty()) return "Phone number is required"

        if (!trimmed.all { it.isDigit() }) {
            return "Only digits are allowed"
        }

        if (trimmed.length != 10) {
            return "Phone number must be 10 digits"
        }

        if (!trimmed.startsWith("6") &&
            !trimmed.startsWith("7") &&
            !trimmed.startsWith("8") &&
            !trimmed.startsWith("9")
        ) {
            return "Invalid Indian mobile number"
        }

        return null // ✅ valid
    }
    fun validateOtp(otp: String): String? {
        if (otp.isEmpty()) return "Enter OTP"
        if (otp.length < 4) return "Incomplete OTP"
        if (!otp.all { it.isDigit() }) return "Invalid OTP"
        return null
    }

}