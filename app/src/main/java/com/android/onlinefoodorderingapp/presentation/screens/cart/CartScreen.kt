package com.android.onlinefoodorderingapp.presentation.screens.cart

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import com.android.onlinefoodorderingapp.presentation.theme.spacing
import com.android.onlinefoodorderingapp.presentation.viewmodel.CartViewModel
import com.android.onlinefoodorderingapp.R
import com.android.onlinefoodorderingapp.data.local.entity.CartEntity
import com.android.onlinefoodorderingapp.domain.model.CartItem
import com.android.onlinefoodorderingapp.presentation.viewmodel.CartViewModel2

@Composable
fun CartScreen( navController: NavController,cartViewModel: CartViewModel2) {

    //val cartViewModel: CartViewModel2 = hiltViewModel()
    val cartItems by cartViewModel.cartItems.collectAsState()
   // val cartCount by cartViewModel.cartCount.collectAsState()

    LaunchedEffect(cartItems) {
        Log.d("TAG", "UI received update")

        cartItems.forEach {
            Log.d("TAG", "${it.foodItem.name} → ${it.quantity}")
        }
    }

    if (cartItems.isEmpty()) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.small),
            contentAlignment = Alignment.Center
        ) {
            Text(stringResource(R.string.car_is_empty))
        }

    } else {


       // val cartItems by cartViewModel.cartItems.collectAsState()

        LazyColumn {

            items(cartItems, key = { it.foodItem.foodId }) { item ->
                val quantity = item.quantity
                Log.i("TAG", "CartScreen: ${item.foodItem.name}, qty=$quantity")
                CartItemRow(
                    item = item,
                    onAdd = { cartViewModel.increaseQuantity(item.foodItem.foodId) },
                    onRemove = { cartViewModel.decreaseQuantity(item.foodItem.foodId) },
                    onDelete = { cartViewModel.removeItem(item.foodItem.foodId) }   // ✅
                )

            }
        }

    }

}
@Composable
fun CartItemRow(
    item: CartItem,
    onAdd: () -> Unit,
    onRemove: () -> Unit,
    onDelete: () -> Unit   // ✅ NEW
) {

    Log.d("DEBUG_ROW", "ROW RECOMPOSE -> ${item.foodItem.name}, qty=${item.quantity}")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.foodItem.name)
            Text(text = "₹${item.foodItem.price}", color = Color.Gray)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {

            // ✅ Decrease
            IconButton(onClick = onRemove) {
                Text("-")
            }

            Text(
                text = item.quantity.toString(),
                fontWeight = FontWeight.Bold
            )

            // ✅ Increase
            IconButton(onClick = onAdd) {
                Text("+")
            }

            // ✅ DELETE BUTTON
            IconButton(onClick = onDelete) {
                Text("🗑")   // or use Icon() if you have icons
            }
        }
    }
}