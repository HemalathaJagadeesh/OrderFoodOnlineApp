package com.android.onlinefoodorderingapp.presentation.screens.cart


import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class CartBottomBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun setContent(
        total: Double = 250.0,
        onCheckoutClick: () -> Unit = {}
    ) {
        composeTestRule.setContent {
            CartBottomBar(
                total = total,
                onCheckoutClick = onCheckoutClick
            )
        }
    }

    // Total is displayed correctly
    @Test
    fun total_isDisplayed() {
        setContent(total = 250.0)

        // Replace with actual formatted text if needed
        composeTestRule
            .onNodeWithTag("cart_total_text")
            .assertExists()
    }

    //  Checkout button is visible
    @Test
    fun checkoutButton_isDisplayed() {
        setContent()

        composeTestRule
            .onNodeWithTag("checkout_button")
            .assertIsDisplayed()
    }

    // Click triggers callback
    @Test
    fun checkoutButton_click_callsCallback() {

        var clicked = false

        setContent {
            clicked = true
        }

        composeTestRule
            .onNodeWithTag("checkout_button")
            .performClick()

        assert(clicked)
    }

    // Text contains total value (optional check)
    @Test
    fun totalText_containsCorrectValue() {
        setContent(total = 150.0)
        composeTestRule
            .onNodeWithTag("cart_total_text")
            .assertTextContains("150.0")

    }
}
