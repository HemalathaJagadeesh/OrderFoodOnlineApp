package com.android.onlinefoodorderingapp.presentation.screens.cart


import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.android.onlinefoodorderingapp.domain.model.CartItem
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import com.android.onlinefoodorderingapp.presentation.screens.cart.fakes.CartScreenTestable
import com.android.onlinefoodorderingapp.presentation.screens.cart.fakes.FakeCartViewModel
import org.junit.Rule
import org.junit.Test

class CartScreenTest {

    @get:Rule
    val rule = createComposeRule()

    private fun setContent(vm: FakeCartViewModel) {
        rule.setContent {
            CartScreenTestable(
                navController = rememberNavController(),
                cartViewModel = vm
            )
        }
    }

    //  Empty cart
    @Test
    fun emptyCart_showsMessage() {

        val vm = FakeCartViewModel()

        setContent(vm)

        rule.onNodeWithTag("empty_cart")
            .assertIsDisplayed()
    }

    // Items appear
    @Test
    fun cartItems_areDisplayed() {

        val vm = FakeCartViewModel()

        vm.cartItems.value = listOf(
            testCartItem("1"),
            testCartItem("2")
        )

        setContent(vm)

        rule.onNodeWithTag("cart_list").assertExists()
        rule.onNodeWithTag("cart_item_1").assertExists()
        rule.onNodeWithTag("cart_item_2").assertExists()
    }

    // Add action
    @Test
    fun add_click_triggersAction() {

        val vm = FakeCartViewModel()

        vm.cartItems.value = listOf(testCartItem("1"))

        var calledId: String? = null
        vm.onAdd = { calledId = it }

        setContent(vm)

        rule.onNodeWithTag("add_1")
            .performClick()

        assert(calledId == "1")
    }

    // Remove action
    @Test
    fun remove_click_triggersAction() {

        val vm = FakeCartViewModel()

        vm.cartItems.value = listOf(testCartItem("1"))

        var called = false
        vm.onRemove = { called = true }

        setContent(vm)

        rule.onNodeWithTag("remove_1")
            .performClick()

        assert(called)
    }

    //  Delete action
    @Test
    fun delete_click_triggersAction() {

        val vm = FakeCartViewModel()

        vm.cartItems.value = listOf(testCartItem("1"))

        var called = false
        vm.onDelete = { called = true }

        setContent(vm)

        rule.onNodeWithTag("delete_1")
            .performClick()

        assert(called)
    }

    // Quantity shown
    @Test
    fun quantity_isDisplayed() {

        val vm = FakeCartViewModel()

        vm.cartItems.value = listOf(
            testCartItem("1", quantity = 3)
        )

        setContent(vm)

        rule.onNodeWithText("3")
            .assertExists()
    }


    fun testCartItem(
        id: String,
        quantity: Int = 1
    ) = CartItem(
        foodItem = FoodItem(
            foodId = id,
            name = "Pizza",
            price = 100.0,
            image = "",
            description = "",
            isSpicy = false,
            isVeg = true,
            category = "Fast Food"
        ),
        quantity = quantity
    )

}


