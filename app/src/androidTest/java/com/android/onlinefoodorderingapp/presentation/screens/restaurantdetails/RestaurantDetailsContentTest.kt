package com.android.onlinefoodorderingapp.presentation.screens.restaurantdetails

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.android.onlinefoodorderingapp.domain.model.CartItem
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import com.android.onlinefoodorderingapp.presentation.util.FoodFilter
import com.android.onlinefoodorderingapp.presentation.util.RestaurantDetailUiState
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class RestaurantDetailsContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Fake state
    private fun fakeState() = RestaurantDetailUiState(
        selectedFilter = FoodFilter.ALL
    )

    // Fake food list
    private fun fakeFoodItems() = listOf(
        FoodItem(
            foodId = "1",
            name = "Burger",
            description = "Tasty burger",
            price = 100.0,
            image = "",
            isVeg = true,
            isSpicy = false,
            category = "Fast Food"
        )
    )

    // Fake cart
    private fun fakeCartItems() = listOf(
        CartItem(
            foodItem = fakeFoodItems()[0],
            quantity = 2
        )
    )

    //  Screen renders
    @Test
    fun screen_renders() {

        composeTestRule.setContent {
            RestaurantDetailsContent(
                state = fakeState(),
                foodItems = fakeFoodItems(),
                cartItems = emptyList(),
                isCollapsed = false,
                listState = LazyListState(),
                onFilterClick = {},
                onItemClick = {},
                onAddClick = { _, _ -> }
            )
        }

        composeTestRule
            .onNodeWithTag("restaurant_details_screen")
            .assertExists()
    }

    // Food item click
    @Test
    fun foodItem_click_triggersCallback() {

        var clicked = false

        composeTestRule.setContent {
            RestaurantDetailsContent(
                state = fakeState(),
                foodItems = fakeFoodItems(),
                cartItems = emptyList(),
                isCollapsed = false,
                listState = LazyListState(),
                onFilterClick = {},
                onItemClick = { clicked = true },
                onAddClick = { _, _ -> }
            )
        }

        composeTestRule
            .onNodeWithTag("food_item_1")
            .assertExists()
            .performClick()

        assertTrue(clicked)
    }

    //Add button click
    @Test
    fun addButton_click_triggersCallback() {

        var added = false

        composeTestRule.setContent {
            RestaurantDetailsContent(
                state = fakeState(),
                foodItems = fakeFoodItems(),
                cartItems = emptyList(),
                isCollapsed = false,
                listState = LazyListState(),
                onFilterClick = {},
                onItemClick = {},
                onAddClick = { _, _ -> added = true }
            )
        }

        composeTestRule
            .onNodeWithTag("add_button", useUnmergedTree = true)
            .assertExists()
            .performClick()

        assertTrue(added)
    }

    // Filter click
    @Test
    fun filter_click_triggersCallback() {

        var clicked = false

        composeTestRule.setContent {
            RestaurantDetailsContent(
                state = fakeState(),
                foodItems = fakeFoodItems(),
                cartItems = emptyList(),
                isCollapsed = false,
                listState = LazyListState(),
                onFilterClick = { clicked = true },
                onItemClick = {},
                onAddClick = { _, _ -> }
            )
        }

        composeTestRule
            .onNodeWithTag("filter_Veg", useUnmergedTree = true) // ✅ depends on label
            .performClick()

        assertTrue(clicked)
    }

    // Quantity UI update
    @Test
    fun quantity_showsCorrectValue() {

        composeTestRule.setContent {
            RestaurantDetailsContent(
                state = fakeState(),
                foodItems = fakeFoodItems(),
                cartItems = fakeCartItems(),
                isCollapsed = false,
                listState = LazyListState(),
                onFilterClick = {},
                onItemClick = {},
                onAddClick = { _, _ -> }
            )
        }

        composeTestRule
            .onNodeWithText("Qty: 2")
            .assertExists()
    }
}