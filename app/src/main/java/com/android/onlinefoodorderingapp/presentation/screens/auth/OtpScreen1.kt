package com.android.onlinefoodorderingapp.presentation.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OtpScreen1(
    error: String? = null,
    onVerify: (String) -> Unit
) {

    var otp by remember { mutableStateOf("") }

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
                "Verify 🔐",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "Enter the OTP sent to your phone",
                color = Color.White.copy(alpha = 0.8f)
            )

            Spacer(Modifier.height(32.dp))

            Card(
                shape = RoundedCornerShape(28.dp),
                elevation = CardDefaults.cardElevation(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    OtpInputField(
                        otp = otp,
                        onOtpChange = { otp = it }
                    )

                    Spacer(Modifier.height(24.dp))

                    Button(
                        onClick = { onVerify(otp) },
                        enabled = otp.length == 6,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                    ) {
                        Text("Verify OTP")
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
@Composable
fun OtpInputField(
    otp: String,
    onOtpChange: (String) -> Unit
) {
    val focusRequesters = List(6) { remember { FocusRequester() } }

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        repeat(6) { index ->
            OutlinedTextField(
                value = otp.getOrNull(index)?.toString() ?: "",
                onValueChange = { value ->
                    if (value.length <= 1) {
                        val newOtp = otp.padEnd(6, ' ').toCharArray()
                        newOtp[index] = value.firstOrNull() ?: ' '
                        onOtpChange(newOtp.joinToString("").trim())

                        if (value.isNotEmpty() && index < 5) {
                            focusRequesters[index + 1].requestFocus()
                        }
                    }
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.titleMedium,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = if (index == 5) ImeAction.Done else ImeAction.Next
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .focusRequester(focusRequesters[index]),
                shape = RoundedCornerShape(12.dp)
            )
        }
    }

    LaunchedEffect(Unit) {
        focusRequesters[0].requestFocus()
    }
}

@Preview
@Composable
fun OtpScreen1Preview() {
    OtpScreen1(onVerify = {})
}