package com.android.onlinefoodorderingapp.presentation.util

import com.android.onlinefoodorderingapp.domain.model.Category
import com.android.onlinefoodorderingapp.domain.model.ExploreItem
import com.android.onlinefoodorderingapp.domain.model.Restaurant

sealed class HomeUiState {
    data object Loading : HomeUiState()

    data class Success(
        val location: String = "",
        val searchQuery: String = "",
        val categories: List<Category> ,
        val featuredRestaurants: List<Restaurant> = emptyList(),
        val isVegMode: Boolean = false,
        val selectedTab: Int = 0,
        val isLoading: Boolean = false,
        val exploreItems: List<ExploreItem> ,
        val restaurants: List<Restaurant>
    ) : HomeUiState()

    data class Error(val message: String) : HomeUiState()

}
