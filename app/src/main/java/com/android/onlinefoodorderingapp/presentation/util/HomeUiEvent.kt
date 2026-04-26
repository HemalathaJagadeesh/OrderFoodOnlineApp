package com.android.onlinefoodorderingapp.presentation.util

import com.android.onlinefoodorderingapp.domain.model.Restaurant

sealed class HomeUiEvent {
    data class OnTopRestaurantsClick(val restaurant: Restaurant) : HomeUiEvent()
}