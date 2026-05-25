package com.android.onlinefoodorderingapp.presentation.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.android.onlinefoodorderingapp.R
import com.android.onlinefoodorderingapp.presentation.theme.AppColors
import com.android.onlinefoodorderingapp.presentation.theme.spacing
import com.android.onlinefoodorderingapp.presentation.util.AppConstants

@Composable
fun OtpScreen1(
    error: String? = null,
    onVerify: (String) -> Unit
) {

    var otp by remember { mutableStateOf(AppConstants.EMPTY_STRING) }

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
                "Verify",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(Modifier.height(MaterialTheme.spacing.small))

            Text(
                "Enter the OTP sent to your phone",
                color = Color.White.copy(alpha = 0.8f)
            )

            Spacer(Modifier.height(MaterialTheme.spacing.xLarge))

            Card(
                shape = MaterialTheme.shapes.large,
                elevation = CardDefaults.cardElevation(MaterialTheme.spacing.small),
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(MaterialTheme.spacing.large),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    OtpInputField(
                        otp = otp,
                        onOtpChange = { otp = it }
                    )

                    Spacer(Modifier.height(MaterialTheme.spacing.large))

                    Button(
                        onClick = { onVerify(otp) },
                        enabled = otp.length == AppConstants.OTP_LENGTH,
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(MaterialTheme.spacing.buttonHeight)
                            .testTag(stringResource(R.string.tt_verify_button))
                    ) {
                        Text(stringResource(R.string.verify_otp))
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
@Composable
fun OtpInputField(
    otp: String,
    onOtpChange: (String) -> Unit
) {
    val focusRequesters = List(AppConstants.COUNT_6) { remember { FocusRequester() } }

    Row(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        modifier = Modifier.fillMaxWidth()
    ) {
        repeat(AppConstants.COUNT_6) { index ->
            OutlinedTextField(
                value = otp.getOrNull(index)?.toString() ?: AppConstants.EMPTY_STRING,
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
                    .height(MaterialTheme.spacing.buttonHeight)
                    .focusRequester(focusRequesters[index])
                    .testTag("otp_input_$index"),
                shape = MaterialTheme.shapes.large
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