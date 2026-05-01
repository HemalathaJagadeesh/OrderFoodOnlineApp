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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.android.onlinefoodorderingapp.domain.util.AuthUiState
import com.android.onlinefoodorderingapp.presentation.theme.spacing
import com.android.onlinefoodorderingapp.R
import com.android.onlinefoodorderingapp.presentation.util.AppConstants


@Composable
fun PhoneInputScreen(
    state: AuthUiState.EnterPhone,
    onPhoneChange: (String) -> Unit,
    onContinue: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE91E63),
                        Color(0xFFD81B60)
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.large),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.spacing40))

            Column {
                Text(
                    text = stringResource(R.string.login_or_signup),
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )

                Spacer(Modifier.height(MaterialTheme.spacing.small))

                Text(
                    text = stringResource(R.string.enter_your_phone_number),
                    color = Color.White.copy(alpha = 0.8f)
                )
            }

            Column {

                OutlinedTextField(
                    value = state.phone,
                    onValueChange = onPhoneChange,
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    singleLine = true,
                    leadingIcon = {
                        Text(stringResource(R.string.country_code), color = Color.Black)
                    },
                    placeholder = { Text(stringResource(R.string.phone_number)) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Black,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.Black
                    )
                )

                state.error?.let {
                    Spacer(Modifier.height(MaterialTheme.spacing.small))
                    Text(it, color = Color.White)
                }

                Spacer(Modifier.height(MaterialTheme.spacing.medium))
                val isValidPhone = state.phone.length == AppConstants.PHONE_LENGTH
                Button(
                    onClick = onContinue,
                    enabled = isValidPhone && !state.isLoading ,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(MaterialTheme.spacing.buttonHeight),
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    )
                ) {
                    Text(stringResource(R.string.send_otp), color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.spacing40))
        }
    }
}

@Preview
@Composable
fun PhoneInputeScreenPreview() {
    PhoneInputScreen(
        state = AuthUiState.EnterPhone(phone = "1234567890", error = "Invalid phone number"),
        onPhoneChange = {},
        onContinue = {}
    )
}