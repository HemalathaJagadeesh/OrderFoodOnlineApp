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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
                    listOf(Color(0xFFFF7043), Color(0xFFFFA726))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                "Foodie 🍔",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "Order your favorite meals",
                color = Color.White.copy(alpha = 0.8f)
            )

            Spacer(Modifier.height(32.dp))

            Card(
                shape = RoundedCornerShape(28.dp),
                elevation = CardDefaults.cardElevation(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(24.dp)
                ) {

                    Text(
                        "Login",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(Modifier.height(16.dp))

                    OutlinedTextField(
                        value = phone,
                        onValueChange = {
                            if (it.length <= 10 && it.all(Char::isDigit)) {
                                phone = it
                                phoneError = if (
                                    it.length == 10 &&
                                    it.first() !in listOf('6', '7', '8', '9')
                                ) {
                                    "Invalid phone number"
                                } else null
                            }

                        },
                        label = { Text("Phone Number") },
                        leadingIcon = {
                            Text("+91")
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                            .focusRequester(phoneFocusRequester)
                    )

                    Spacer(Modifier.height(20.dp))

                    Button(
                        onClick = { onSendOtp(phone) },
                        enabled = phoneError == null && phone.length == 10,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                    ) {
                        Text("Send OTP")
                    }

                    error?.let {
                        Spacer(Modifier.height(12.dp))
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