package com.android.onlinefoodorderingapp.presentation.screens.auth

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.text.AnnotatedString
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Helper to launch screen
    private fun setContent(error: String? = null, onSendOtp: (String) -> Unit = {}) {
        composeTestRule.setContent {
            LoginScreen(
                error = error,
                onSendOtp = onSendOtp
            )
        }
    }


    @Test
    fun phoneInput_acceptsValidDigits() {
        setContent()

        composeTestRule
            .onNodeWithTag("phone_input")
            .performTextInput("9876543210")

        composeTestRule
            .onNodeWithTag("phone_input")
            .assert(
                SemanticsMatcher.expectValue(
                    SemanticsProperties.EditableText,
                    AnnotatedString("9876543210")
                )
            )
    }

    //Button disabled initially
    @Test
    fun sendOtpButton_disabledInitially() {
        setContent()

        composeTestRule
            .onNodeWithTag("send_otp_button")
            .assertIsNotEnabled()
    }

    // Button enabled for valid phone
    @Test
    fun sendOtpButton_enabled_whenPhoneValid() {
        setContent()

        composeTestRule
            .onNodeWithTag("phone_input")
            .performTextInput("9876543210")

        composeTestRule
            .onNodeWithTag("send_otp_button")
            .assertIsEnabled()
    }

    // Invalid phone disables button
    @Test
    fun sendOtpButton_disabled_forInvalidPhone() {
        setContent()

        composeTestRule
            .onNodeWithTag("phone_input")
            .performTextInput("12345")

        composeTestRule
            .onNodeWithTag("send_otp_button")
            .assertIsNotEnabled()
    }

    // Click triggers callback
    @Test
    fun sendOtpButton_click_callsCallback() {

        var capturedPhone: String? = null

        setContent {
            capturedPhone = it
        }

        composeTestRule
            .onNodeWithTag("phone_input")
            .performTextInput("9876543210")

        composeTestRule
            .onNodeWithTag("send_otp_button")
            .performClick()

        assert(capturedPhone == "9876543210")
    }

    // Error text is displayed
    @Test
    fun errorMessage_isDisplayed() {
        setContent(error = "Invalid OTP")

        composeTestRule
            .onNodeWithText("Invalid OTP")
            .assertIsDisplayed()
    }
}