package com.android.onlinefoodorderingapp.presentation.util

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OtpManager @Inject constructor(@ApplicationContext private val context: Context) {

    private var otp: String? = null
    private var phone: String? = null

    fun generate(phone: String): String {
        otp = (1000..9999).random().toString()
        println( "Generated OTP in OtpManager $phone: $otp") // For testing, in real app use secure logging
        this.phone = phone
        return otp!!
    }

    fun verify(phone: String, input: String): Boolean {
        println("$phone: $input - $this.phone: $otp") // For testing, in real app use secure logging
        return phone == this.phone && input == otp
    }
}