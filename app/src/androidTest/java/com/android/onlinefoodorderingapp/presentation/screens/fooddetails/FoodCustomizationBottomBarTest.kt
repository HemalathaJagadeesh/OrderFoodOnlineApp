package com.android.onlinefoodorderingapp.presentation.screens.fooddetails


import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import com.android.onlinefoodorderingapp.presentation.screens.foodcustomization.FoodCustomizationBottomBar
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class FoodCustomizationBottomBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun fakeFood() = FoodItem(
        foodId = "1",
        name = "Pizza",
        price = 100.0,
        image = "",
        description = "",
        isSpicy = false,
        isVeg = true,
        category = "Fast Food"
    )

    //  Check UI renders correctly
    @Test
    fun bottomBar_rendersCorrectly() {
        composeTestRule.setContent {
            FoodCustomizationBottomBar(
                item = fakeFood(),
                quantity = 2,
                onIncrease = {},
                onDecrease = {},
                onAddToCart = {}
            )
        }

        composeTestRule
            .onNodeWithTag("add_to_cart_button")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("quantity_text")
            .assertTextEquals("2")
    }

    // Check price calculation
    @Test
    fun bottomBar_displaysCorrectTotalPrice() {
        composeTestRule.setContent {
            FoodCustomizationBottomBar(
                item = fakeFood(), // price = 100
                quantity = 3,
                onIncrease = {},
                onDecrease = {},
                onAddToCart = {}
            )
        }

        // total = 300
        composeTestRule
            .onNodeWithTag("total_price_text")
            .assertTextContains("300")
    }

    //  Increase button click

    @Test
    fun increaseButton_triggersCallback() {

        var clicked = false

        composeTestRule.setContent {
            FoodCustomizationBottomBar(
                item = fakeFood(),
                quantity = 1,
                onIncrease = { clicked = true },
                onDecrease = {},
                onAddToCart = {}
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag("increase_button", useUnmergedTree = true)
            .assertExists()
            .performClick()

        assertTrue(clicked)
    }


    //  Decrease button click
    @Test
    fun decreaseButton_triggersCallback() {
        var clicked = false

        composeTestRule.setContent {
            FoodCustomizationBottomBar(
                item = fakeFood(),
                quantity = 2,
                onIncrease = {},
                onDecrease = { clicked = true },
                onAddToCart = {}
            )
        }

        composeTestRule
            .onNodeWithTag("decrease_button")
            .performClick()

        assertTrue(clicked)
    }

    //  Decrease button disabled when quantity = 1
    @Test
    fun decreaseButton_isDisabled_whenQuantityIsOne() {

        composeTestRule.setContent {
            FoodCustomizationBottomBar(
                item = fakeFood(),
                quantity = 1,
                onIncrease = {},
                onDecrease = {},
                onAddToCart = {}
            )
        }

        composeTestRule
            .onNodeWithTag("decrease_button")
            .assertIsNotEnabled()
    }

    // Add to cart click passes correct quantity
    @Test
    fun addToCart_passesCorrectQuantity() {

        var receivedQty = 0

        composeTestRule.setContent {
            FoodCustomizationBottomBar(
                item = fakeFood(),
                quantity = 4,
                onIncrease = {},
                onDecrease = {},
                onAddToCart = { qty ->
                    receivedQty = qty
                }
            )
        }

        composeTestRule
            .onNodeWithTag("add_to_cart_button")
            .performClick()

        assertEquals(4, receivedQty)
    }
}
