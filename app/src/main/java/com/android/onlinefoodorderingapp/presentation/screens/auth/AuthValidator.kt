package com.android.onlinefoodorderingapp.presentation.screens.auth

import com.android.onlinefoodorderingapp.presentation.util.AppConstants

object AuthValidator {

    fun validatePhone(phone: String): String? {
        val trimmed = phone.trim()

        if (trimmed.isEmpty()) return AppConstants.PHONE_NUM_IS_REQ

        if (!trimmed.all { it.isDigit() }) {
            return AppConstants.ONLY_DIGITS_ARE_ALLOWED
        }

        if (trimmed.length != AppConstants.PHONE_LENGTH) {
            return AppConstants.PHONE_SHOULD_MUST_BE_10_DIGIT
        }

        if (!trimmed.startsWith("6") &&
            !trimmed.startsWith("7") &&
            !trimmed.startsWith("8") &&
            !trimmed.startsWith("9")
        ) {
            return "Invalid Indian mobile number"
        }

        return null
    }
    fun validateOtp(otp: String): String? {
        if (otp.isEmpty()) return AppConstants.ENTER_OTP
        if (otp.length < AppConstants.OTP_LENGTH) return AppConstants.INCOMPLETE_OTP
        if (!otp.all { it.isDigit() }) return AppConstants.INVALID_OTP
        return null
    }

}