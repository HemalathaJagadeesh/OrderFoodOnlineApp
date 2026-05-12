package com.android.onlinefoodorderingapp.presentation.screens.cart

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.onlinefoodorderingapp.data.local.entity.CartEntity
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import com.android.onlinefoodorderingapp.presentation.viewmodel.CartViewModel1

@Composable
fun CartScreen1(viewModel: CartViewModel1
) {
/*
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val cartItems by viewModel.cartItems.collectAsState()
    LaunchedEffect(cartItems) {
        Log.d("CartUI", "Collected: $cartItems")
    }

    LazyColumn {
        items(items = uiState.cartItems,  key = { it.foodId }) {
                item ->

            CartItemRow(
                item = item,
                onIncrease = {
                    viewModel.increaseQuantity(item.foodId)
                },
                onDecrease = {
                    viewModel.decreaseQuantity(item.foodId)
                }
            )
        }

       *//* items(
            items = uiState.cartItems,
            key = { it.id }
        ) { item ->

            CartItemCard(
                item = item,
                onIncrease = {
                    viewModel.increaseQuantity(item)
                },
                onDecrease = {
                    viewModel.decreaseQuantity(item)
                }
            )*//*

    }
}
@Composable
fun CartItemRow(
    item: CartEntity,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {

    Row {

        IconButton(
            onClick = onDecrease
        ) {
            Text("-")
        }

        Text(
            text = item.quantity.toString()
        )

        IconButton(
            onClick = onIncrease
        ) {
            Text("+")
        }
    }*/
}