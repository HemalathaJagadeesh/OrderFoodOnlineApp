package com.android.onlinefoodorderingapp.presentation.util

import com.android.onlinefoodorderingapp.domain.model.Restaurant

sealed class UiEffect {
    data class NavigateToRestaurantDetails(val restaurant: Restaurant): UiEffect()
    //data object NavigateToLogin: UiEffect()
}


