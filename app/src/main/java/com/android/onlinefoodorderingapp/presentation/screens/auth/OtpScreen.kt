package com.android.onlinefoodorderingapp.presentation.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.android.onlinefoodorderingapp.domain.util.AuthUiState
import com.android.onlinefoodorderingapp.presentation.theme.LocalSpacing
import com.android.onlinefoodorderingapp.R
import com.android.onlinefoodorderingapp.presentation.util.AppConstants
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.size
import androidx.compose.ui.text.style.TextAlign

@Composable
fun OtpScreen(
    state: AuthUiState.OtpSent,
    onOtpChange: (String) -> Unit,
    onVerify: () -> Unit,
    onResend: () -> Unit,
    onBack: () -> Unit
) {

    BackHandler {
        onBack()  // Explicit back navigation
    }

    val isOtpValid = state.otp.length == AppConstants.OTP_LENGTH
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.large),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            Text(
                stringResource(R.string.verify_otp),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(Modifier.height(spacing.small))

            Text(
                stringResource(R.string.otp_sent_to, state.phone),
                color = Color.Gray
            )
        }

        Column {

            OtpInput(
                otp = state.otp,
                onOtpChange = onOtpChange
            )

            Spacer(Modifier.height(spacing.medium))

            if (state.timer > AppConstants.VALUE_ZERO) {
                Text(
                    stringResource(R.string.resend_in_seconds,state.timer),
                    color = Color.Gray
                )
            } else {
                TextButton(onClick = onResend) {
                    Text(stringResource(R.string.resend_otp))
                }
            }

            state.error?.let {
                Spacer(Modifier.height(spacing.small))
                Text(it, color = Color.Red)
            }
        }

        Button(
            onClick = onVerify,
            enabled = isOtpValid && !state.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(spacing.buttonHeight),
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            )
        ) {
            Text(stringResource(R.string.verify), color = Color.White)
        }
    }
}

@Composable
fun OtpInput(
    otp: String,
    onOtpChange: (String) -> Unit,
    otpLength: Int = AppConstants.OTP_LENGTH
) {
    val focusRequesters = List(otpLength) { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val spacing = LocalSpacing.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(otpLength) { index ->

            val char = otp.getOrNull(index)?.toString() ?: AppConstants.EMPTY_STRING

            OutlinedTextField(
                value = char,
                onValueChange = { value ->

                    if (value.length > AppConstants.VALUE_ONE) return@OutlinedTextField

                    val newOtp = otp.toMutableList()

                    if (value.isNotEmpty()) {
                        if (otp.length > index) {
                            newOtp[index] = value[AppConstants.VALUE_ZERO]
                        } else {
                            newOtp.add(value[AppConstants.VALUE_ZERO])
                        }

                        onOtpChange(newOtp.joinToString(AppConstants.EMPTY_STRING))

                        // Move to next field
                        if (index < otpLength - AppConstants.VALUE_ONE) {
                            focusRequesters[index + AppConstants.VALUE_ONE].requestFocus()
                        } else {
                            focusManager.clearFocus()
                        }

                    } else {
                        // Handle delete
                        if (otp.isNotEmpty() && index < otp.length) {
                            newOtp.removeAt(index)
                            onOtpChange(newOtp.joinToString(AppConstants.EMPTY_STRING))
                        }

                        // Move to previous field
                        if (index > 0) {
                            focusRequesters[index - AppConstants.VALUE_ONE].requestFocus()
                        }
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = spacing.medium)
                    .focusRequester(focusRequester = focusRequesters[index])
                    .onKeyEvent { event ->
                        if (
                            event.type == KeyEventType.KeyDown &&
                            event.key == Key.Backspace &&
                            char.isEmpty()
                        ) {
                            if (index > AppConstants.VALUE_ZERO) {
                                focusRequesters[index - AppConstants.VALUE_ONE].requestFocus()
                            }
                            true
                        } else {
                            false
                        }
                    },
                textStyle = MaterialTheme.typography.headlineMedium.copy(textAlign = TextAlign.Center),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                shape = MaterialTheme.shapes.medium
            )
        }
    }

    // 👉 Auto focus first box when screen opens
    LaunchedEffect(Unit) {
        focusRequesters.first().requestFocus()
    }
}

@Preview
@Composable
fun OtpScreenPreview() {
    OtpScreen(
        state = AuthUiState.OtpSent(
            phone = "+1234567890",
            otp = AppConstants.EMPTY_STRING,
            timer = 30,
            error = null
        ),
        onOtpChange = {},
        onVerify = {},
        onResend = {},
        onBack = {}
    )
}