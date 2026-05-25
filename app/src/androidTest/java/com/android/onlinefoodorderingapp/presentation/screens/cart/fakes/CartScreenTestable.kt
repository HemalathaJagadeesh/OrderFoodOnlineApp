package com.android.onlinefoodorderingapp.presentation.screens.cart.fakes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavController
import com.android.onlinefoodorderingapp.presentation.screens.cart.CartItemRow


@Composable
fun CartScreenTestable(
    navController: NavController,
    cartViewModel: FakeCartViewModel
) {
    val cartItems by cartViewModel.cartItems.collectAsState()

    if (cartItems.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .testTag("empty_cart"),
            contentAlignment = Alignment.Center
        ) {
            Text("Cart is empty")
        }
    } else {
        LazyColumn(modifier = Modifier.testTag("cart_list")) {
            items(cartItems, key = { it.foodItem.foodId }) { item ->

                CartItemRow(
                    item = item,
                    onAdd = {
                        cartViewModel.increaseQuantity(item.foodItem.foodId)
                    },
                    onRemove = {
                        cartViewModel.decreaseQuantity(item.foodItem.foodId)
                    },
                    onDelete = {
                        cartViewModel.removeItem(item.foodItem.foodId)
                    }
                )
            }
        }
    }
}
