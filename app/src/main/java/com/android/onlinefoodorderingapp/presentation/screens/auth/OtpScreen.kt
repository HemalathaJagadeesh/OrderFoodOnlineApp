package com.android.onlinefoodorderingapp.presentation.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.onlinefoodorderingapp.domain.util.AuthUiState

@Composable
fun OtpScreen(
    state: AuthUiState.OtpSent,
    onOtpChange: (String) -> Unit,
    onVerify: () -> Unit,
    onResend: () -> Unit
) {
    val isOtpValid = state.otp.length == 4
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            Text(
                "Verify OTP",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "OTP sent to ${state.phone}",
                color = Color.Gray
            )
        }

        Column {

            OtpInput(
                otp = state.otp,
                onOtpChange = onOtpChange
            )

            Spacer(Modifier.height(16.dp))

            if (state.timer > 0) {
                Text(
                    "Resend in ${state.timer}s",
                    color = Color.Gray
                )
            } else {
                TextButton(onClick = onResend) {
                    Text("Resend OTP")
                }
            }

            state.error?.let {
                Spacer(Modifier.height(6.dp))
                Text(it, color = Color.Red)
            }
        }

        Button(
            onClick = onVerify,
            enabled = isOtpValid && !state.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            )
        ) {
            Text("Verify", color = Color.White)
        }
    }
}

@Composable
fun OtpInput(
    otp: String,
    onOtpChange: (String) -> Unit,
    otpLength: Int = 4
) {
    val focusRequesters = List(otpLength) { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(otpLength) { index ->

            val char = otp.getOrNull(index)?.toString() ?: ""

            OutlinedTextField(
                value = char,
                onValueChange = { value ->

                    if (value.length > 1) return@OutlinedTextField

                    val newOtp = otp.toMutableList()

                    if (value.isNotEmpty()) {
                        if (otp.length > index) {
                            newOtp[index] = value[0]
                        } else {
                            newOtp.add(value[0])
                        }

                        onOtpChange(newOtp.joinToString(""))

                        // 👉 Move to next field
                        if (index < otpLength - 1) {
                            focusRequesters[index + 1].requestFocus()
                        } else {
                            focusManager.clearFocus()
                        }

                    } else {
                        // Handle delete
                        if (otp.isNotEmpty() && index < otp.length) {
                            newOtp.removeAt(index)
                            onOtpChange(newOtp.joinToString(""))
                        }

                        // 👉 Move to previous field
                        if (index > 0) {
                            focusRequesters[index - 1].requestFocus()
                        }
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
                    .focusRequester(focusRequester = focusRequesters[index])
                    .onKeyEvent { event ->
                        if (
                            event.type == KeyEventType.KeyDown &&
                            event.key == Key.Backspace &&
                            char.isEmpty()
                        ) {
                            if (index > 0) {
                                focusRequesters[index - 1].requestFocus()
                            }
                            true
                        } else {
                            false
                        }
                    },
                textStyle = MaterialTheme.typography.headlineMedium,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                shape = RoundedCornerShape(12.dp)
            )
        }
    }

    // 👉 Auto focus first box when screen opens
    LaunchedEffect(Unit) {
        focusRequesters.first().requestFocus()
    }
}
fun validateOtp(otp: String, expectedLength: Int = 4): String? {
    if (otp.isEmpty()) return "Enter OTP"

    if (otp.length < expectedLength) return "Incomplete OTP"

    if (!otp.all { it.isDigit() }) return "Invalid OTP"

    return null
}

@Preview
@Composable
fun OtpScreenPreview() {
    OtpScreen(
        state = AuthUiState.OtpSent(
            phone = "+1234567890",
            otp = "",
            timer = 30,
            error = null
        ),
        onOtpChange = {},
        onVerify = {},
        onResend = {}
    )
}