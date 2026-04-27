package com.android.onlinefoodorderingapp.presentation.util

import com.android.onlinefoodorderingapp.domain.model.Restaurant
import com.android.onlinefoodorderingapp.presentation.screens.home.ProfileAction

sealed class HomeUiEvent {
    data class OnTopRestaurantsClick(val restaurant: Restaurant) : HomeUiEvent()
    data class OnProfileMenuClick(val action: ProfileAction) : HomeUiEvent()
}