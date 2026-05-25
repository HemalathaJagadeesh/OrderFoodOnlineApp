package com.android.onlinefoodorderingapp.presentation.screens.auth


import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class OtpScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun setContent(
        error: String? = null,
        onVerify: (String) -> Unit = {}
    ) {
        composeTestRule.setContent {
            OtpScreen1(error = error, onVerify = onVerify)
        }
    }

    // Enter OTP digits
    @Test
    fun otpInput_acceptsValues() {
        setContent()

        composeTestRule.onNodeWithTag("otp_input_0").performTextInput("1")
        composeTestRule.onNodeWithTag("otp_input_1").performTextInput("2")
        composeTestRule.onNodeWithTag("otp_input_2").performTextInput("3")
        composeTestRule.onNodeWithTag("otp_input_3").performTextInput("4")
        composeTestRule.onNodeWithTag("otp_input_4").performTextInput("5")
        composeTestRule.onNodeWithTag("otp_input_5").performTextInput("6")

        // Validate last field (simple check)
        composeTestRule
            .onNodeWithTag("otp_input_5")
            .assertTextContains("6")
    }

    //Button disabled initially
    @Test
    fun verifyButton_disabledInitially() {
        setContent()

        composeTestRule
            .onNodeWithTag("verify_button")
            .assertIsNotEnabled()
    }

    // Button enabled after full OTP
    @Test
    fun verifyButton_enabled_whenOtpComplete() {
        setContent()

        repeat(6) { index ->
            composeTestRule
                .onNodeWithTag("otp_input_$index")
                .performTextInput("${index + 1}")
        }

        composeTestRule
            .onNodeWithTag("verify_button")
            .assertIsEnabled()
    }

    // Verify click triggers callback
    @Test
    fun verifyButton_click_callsCallback() {

        var capturedOtp: String? = null

        setContent {
            capturedOtp = it
        }

        repeat(6) { index ->
            composeTestRule
                .onNodeWithTag("otp_input_$index")
                .performTextInput("${index + 1}")
        }

        composeTestRule
            .onNodeWithTag("verify_button")
            .performClick()

        assert(capturedOtp == "123456")
    }

    // Error message displayed
    @Test
    fun errorMessage_isDisplayed() {

        setContent(error = "Invalid OTP")

        composeTestRule
            .onNodeWithText("Invalid OTP")
            .assertIsDisplayed()
    }
}
