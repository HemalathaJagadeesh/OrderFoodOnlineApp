package com.android.onlinefoodorderingapp.presentation.screens.cart

import com.android.onlinefoodorderingapp.data.local.entity.CartEntity
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem

data class CartUiState(

    val cartItems: List<FoodItem> = emptyList(),

    val totalPrice: Double = 0.0
)