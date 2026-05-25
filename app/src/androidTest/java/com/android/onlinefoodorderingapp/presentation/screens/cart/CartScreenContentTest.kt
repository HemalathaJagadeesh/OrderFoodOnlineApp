package com.android.onlinefoodorderingapp

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.android.onlinefoodorderingapp.domain.model.CartItem
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import com.android.onlinefoodorderingapp.presentation.screens.cart.CartScreenContent
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class CartScreenContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // ✅ Updated fake data with all required fields
    private fun getFakeCartItems(): List<CartItem> {
        return listOf(
            CartItem(
                foodItem = FoodItem(
                    foodId = "1",
                    name = "Pizza",
                    price = 200.0,
                    image = "pizza.png",
                    description = "Delicious cheese pizza",
                    isSpicy = false,
                    isVeg = true,
                    category = "Fast Food"
                ),
                quantity = 2
            ),
            CartItem(
                foodItem = FoodItem(
                    foodId = "2",
                    name = "Burger",
                    price = 100.0,
                    image = "burger.png",
                    description = "Tasty burger",
                    isSpicy = true,
                    isVeg = false,
                    category = "Fast Food"
                ),
                quantity = 1
            )
        )
    }

    // ✅ 1. Empty Cart Test
    @Test
    fun cartScreen_whenEmpty_showsEmptyMessage() {
        composeTestRule.setContent {
            CartScreenContent(
                cartItems = emptyList(),
                onAdd = {},
                onRemove = {},
                onDelete = {}
            )
        }

        composeTestRule
            .onNodeWithTag("empty_cart")
            .assertIsDisplayed()
    }

    // ✅ 2. List Rendering Test
    @Test
    fun cartScreen_whenItemsPresent_showsList() {
        val items = getFakeCartItems()

        composeTestRule.setContent {
            CartScreenContent(
                cartItems = items,
                onAdd = {},
                onRemove = {},
                onDelete = {}
            )
        }

        composeTestRule
            .onNodeWithTag("cart_list")
            .assertIsDisplayed()
    }

    // ✅ 3. Items Exist Test
    @Test
    fun cartScreen_showsAllCartItems() {
        val items = getFakeCartItems()

        composeTestRule.setContent {
            CartScreenContent(
                cartItems = items,
                onAdd = {},
                onRemove = {},
                onDelete = {}
            )
        }

        composeTestRule.onNodeWithTag("cart_item_1").assertExists()
        composeTestRule.onNodeWithTag("cart_item_2").assertExists()
    }

    // ✅ 4. Add Button Click
    @Test
    fun addButton_click_triggersCallback() {
        val items = getFakeCartItems()
        var clicked = false

        composeTestRule.setContent {
            CartScreenContent(
                cartItems = items,
                onAdd = { id ->
                    if (id == "1") clicked = true
                },
                onRemove = {},
                onDelete = {}
            )
        }

        composeTestRule
            .onNodeWithTag("add_1")
            .performClick()

        assertTrue(clicked)
    }

    // ✅ 5. Remove Button Click
    @Test
    fun removeButton_click_triggersCallback() {
        val items = getFakeCartItems()
        var clicked = false

        composeTestRule.setContent {
            CartScreenContent(
                cartItems = items,
                onAdd = {},
                onRemove = { id ->
                    if (id == "1") clicked = true
                },
                onDelete = {}
            )
        }

        composeTestRule
            .onNodeWithTag("remove_1")
            .performClick()

        assertTrue(clicked)
    }

    // ✅ 6. Delete Button Click
    @Test
    fun deleteButton_click_triggersCallback() {
        val items = getFakeCartItems()
        var clicked = false

        composeTestRule.setContent {
            CartScreenContent(
                cartItems = items,
                onAdd = {},
                onRemove = {},
                onDelete = { id ->
                    if (id == "1") clicked = true
                }
            )
        }

        composeTestRule
            .onNodeWithTag("delete_1")
            .performClick()

        assertTrue(clicked)
    }
}