package com.android.onlinefoodorderingapp.presentation.util

import com.android.onlinefoodorderingapp.domain.model.OptionItem
import com.android.onlinefoodorderingapp.domain.model.foodcustomization.OptionGroup
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem

data class FoodDetailState(
    val foodItem: FoodItem? = null,
    val optionGroups: List<OptionGroup> = emptyList(),
    val selectedOptions: Map<String, OptionItem> = emptyMap(), // groupTitle -> selected option
    val quantity: Int = 1,
    val totalPrice: Int = 0,
    val isLoading: Boolean = false
)