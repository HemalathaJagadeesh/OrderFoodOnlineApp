package com.android.onlinefoodorderingapp.presentation.util

import com.android.onlinefoodorderingapp.domain.model.CartItem
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import com.android.onlinefoodorderingapp.presentation.screens.restaurantdetails.MenuSearchUiState

data class RestaurantDetailUiState(
    val searchText: String = "",
    val isMenuSheetOpen: Boolean = false,
    val categories: List<String> = emptyList(),
    val selectedFoodItem: FoodItem? = null,
   // val foodItem: List<FoodItem> = emptyList(),
    val isBottomSheetVisible: Boolean = false,
    val selectedFilter: FoodFilter = FoodFilter.ALL,
    val allFoodItems: List<FoodItem> = emptyList(),

    val menuSearchState: MenuSearchUiState = MenuSearchUiState(),
    val cartItems: Map<String, CartItem> = emptyMap()

    )
