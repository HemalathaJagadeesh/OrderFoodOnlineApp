package com.android.onlinefoodorderingapp.presentation.screens.fooddetails


import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import com.android.onlinefoodorderingapp.presentation.screens.foodcustomization.FoodDetailsContent
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class FoodDetailsContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Fake FoodItem
    private fun fakeFood() = FoodItem(
        foodId = "1",
        name = "Pizza",
        price = 250.0,
        image = "",
        description = "Cheesy pizza",
        isSpicy = false,
        isVeg = true,
        category = "Fast Food"
    )

    // Screen renders
    @Test
    fun foodDetailsContent_rendersSuccessfully() {
        composeTestRule.setContent {
            FoodDetailsContent(
                foodItem = fakeFood(),
                onShareClick = {}
            )
        }

        composeTestRule
            .onNodeWithTag("food_details_screen")
            .assertIsDisplayed()
    }

    // Food info is displayed correctly

    @Test
    fun foodDetailsContent_displaysFoodData() {
        val food = fakeFood()

        composeTestRule.setContent {
            FoodDetailsContent(
                foodItem = food,
                onShareClick = {}
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag("food_name", useUnmergedTree = true)
            .assertTextEquals("Pizza")

        composeTestRule
            .onNodeWithTag("food_description", useUnmergedTree = true)
            .assertTextEquals("Cheesy pizza")

        composeTestRule
            .onNodeWithTag("food_price", useUnmergedTree = true)
            .assertTextEquals("₹250.0")
    }



    //  Share button click triggers callback

    @Test
    fun foodDetailsContent_shareClick_triggersCallback() {

        var clicked = false

        composeTestRule.setContent {
            FoodDetailsContent(
                foodItem = fakeFood(),
                onShareClick = {
                    clicked = true
                }
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag("share_button", useUnmergedTree = true)
            .assertExists()
            .performClick()

        assertTrue(clicked)
    }


    //  Works when foodItem is null (no crash case)
    @Test
    fun foodDetailsContent_nullFood_doesNotCrash() {

        composeTestRule.setContent {
            FoodDetailsContent(
                foodItem = null,
                onShareClick = {}
            )
        }

        composeTestRule
            .onNodeWithTag("food_details_screen")
            .assertIsDisplayed()
    }
}
