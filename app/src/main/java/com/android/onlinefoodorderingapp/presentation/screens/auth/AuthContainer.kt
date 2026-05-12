package com.android.onlinefoodorderingapp.presentation.screens.auth

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.onlinefoodorderingapp.presentation.theme.spacing
import com.android.onlinefoodorderingapp.presentation.util.AppConstants
import com.android.onlinefoodorderingapp.presentation.util.AuthUiState1
import com.android.onlinefoodorderingapp.presentation.viewmodel.FirebaseAuthViewModel

@Composable
fun AuthContainer( navController: NavController,
                   viewModel: FirebaseAuthViewModel = hiltViewModel()) {
    val state by viewModel.authState.collectAsState()
    val error by viewModel.errorMessage.collectAsState()
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {

        when (state) {

            is AuthUiState1.Login -> {
                LoginScreen(
                    error = error,
                    onSendOtp = { phone ->

                        viewModel.sendOtp(
                            activityProvider = {
                                context.findActivity()
                            },
                            phone = phone
                        )

                    }
                )
            }

            is AuthUiState1.Otp -> {
                OtpScreen1(
                    error = error,
                    onVerify = { code ->
                        viewModel.verifyOtp(code)
                    }
                )
            }

            is AuthUiState1.Authenticated -> {
                LaunchedEffect(Unit) {
                    navController.navigate("home") {
                        popUpTo("auth") { inclusive = true }
                    }
                }
            }
/*
            is AuthUiState1.Error -> {
                val message = (state as AuthUiState1.Error).message

                OtpScreen1(
                    error = message,
                    onVerify = { code ->
                        viewModel.verifyOtp(code)
                    }
                )

            }*/

            else -> Unit
        }

        // 🔥 Loader overlay
        if (state is AuthUiState1.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.25f))
                    .blur(MaterialTheme.spacing.medium),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

fun Context.findActivity(): Activity {
    return when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> throw IllegalStateException(AppConstants.NO_ACTIVITY_FOUND)
    }
}
