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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.onlinefoodorderingapp.domain.util.AuthUiState

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
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Column {
                Text(
                    text = "Login or Signup",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Enter your phone number",
                    color = Color.White.copy(alpha = 0.8f)
                )
            }

            Column {

                OutlinedTextField(
                    value = state.phone,
                    onValueChange = onPhoneChange,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    leadingIcon = {
                        Text("+91 ", color = Color.Black)
                    },
                    placeholder = { Text("Phone number") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                state.error?.let {
                    Spacer(Modifier.height(6.dp))
                    Text(it, color = Color.White)
                }

                Spacer(Modifier.height(16.dp))
                val isValidPhone = state.phone.length == 10
                Button(
                    onClick = onContinue,
                    enabled = isValidPhone && !state.isLoading ,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    )
                ) {
                    Text("Send OTP", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
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