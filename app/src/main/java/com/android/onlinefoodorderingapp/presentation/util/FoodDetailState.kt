package com.android.onlinefoodorderingapp.presentation.util

import com.android.onlinefoodorderingapp.domain.model.foodcustomization.OptionGroup
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem

data class FoodDetailState(
    val foodItem: FoodItem? = null,
    val optionGroups: List<OptionGroup> = emptyList()
)