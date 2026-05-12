package com.android.onlinefoodorderingapp.domain.model

import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem

data class CartItem(
    val foodItem: FoodItem,
    val quantity: Int
)
