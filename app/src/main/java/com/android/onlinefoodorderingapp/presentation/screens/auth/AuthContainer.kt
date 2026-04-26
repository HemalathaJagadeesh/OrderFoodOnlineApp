package com.android.onlinefoodorderingapp.presentation.screens.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.onlinefoodorderingapp.domain.util.AuthUiState
import com.android.onlinefoodorderingapp.presentation.viewmodel.AuthViewModel

@Composable
fun AuthContainer(viewModel: AuthViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState()

    when (val current = state) {

        is AuthUiState.EnterPhone -> {
            PhoneInputScreen(
                state = current,
                onPhoneChange = viewModel::onPhoneChange,
                onContinue = viewModel::sendOtp
            )
        }

        is AuthUiState.OtpSent -> {
            OtpScreen(
                state = current,
                onOtpChange = viewModel::onOtpChange,
                onVerify = viewModel::verifyOtp,
                onResend = viewModel::resendOtp
            )
        }

        is AuthUiState.LoggedIn -> {
            // Do nothing here
        }
    }
}