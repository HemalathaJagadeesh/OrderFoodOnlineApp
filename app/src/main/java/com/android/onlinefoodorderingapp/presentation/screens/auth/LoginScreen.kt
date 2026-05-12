package com.android.onlinefoodorderingapp.presentation.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.android.onlinefoodorderingapp.R
import com.android.onlinefoodorderingapp.presentation.theme.AppColors
import com.android.onlinefoodorderingapp.presentation.theme.spacing
import com.android.onlinefoodorderingapp.presentation.util.AppConstants

@Composable
fun LoginScreen(
    error: String? = null,
    onSendOtp: (String) -> Unit
) {

    var phone by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf<String?>(null)}
    val phoneFocusRequester = remember { FocusRequester() }



    LaunchedEffect(Unit) {
        phoneFocusRequester.requestFocus()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(AppColors.loginOrangeDark.toArgb()), Color(AppColors.loginOrangeLight.toArgb()))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.large),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                "Foodie 🍔",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(Modifier.height(MaterialTheme.spacing.small))

            Text(
                "Order your favorite meals",
                color = Color.White.copy(alpha = 0.8f)
            )

            Spacer(Modifier.height(MaterialTheme.spacing.xLarge))

            Card(
                shape = MaterialTheme.shapes.large,
                elevation = CardDefaults.cardElevation(MaterialTheme.spacing.small),
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(MaterialTheme.spacing.large)
                ) {

                    Text(
                        stringResource(R.string.login),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(Modifier.height(MaterialTheme.spacing.medium))

                    OutlinedTextField(
                        value = phone,
                        onValueChange = {
                            if (it.length <= AppConstants.PHONE_LENGTH && it.all(Char::isDigit)) {
                                phone = it
                                phoneError = if (
                                    it.length == AppConstants.PHONE_LENGTH &&
                                    it.first() !in listOf('6', '7', '8', '9')
                                ) {
                                    AppConstants.INVALID_PHONE_NUMBER
                                } else null
                            }

                        },
                        label = { Text(AppConstants.PHONE_NUMBER) },
                        leadingIcon = {
                            Text(stringResource(R.string.india_code))
                        },
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier.fillMaxWidth()
                            .focusRequester(phoneFocusRequester)
                    )

                    Spacer(Modifier.height(MaterialTheme.spacing.large))

                    Button(
                        onClick = { onSendOtp(phone) },
                        enabled = phoneError == null && phone.length == AppConstants.PHONE_LENGTH,
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(MaterialTheme.spacing.buttonHeight)
                    ) {
                        Text(stringResource(R.string.send_otp))
                    }

                    error?.let {
                        Spacer(Modifier.height(MaterialTheme.spacing.medium))
                        Text(it, color = Color.Red)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(onSendOtp = {})
}