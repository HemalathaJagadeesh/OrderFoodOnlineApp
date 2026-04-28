package com.android.onlinefoodorderingapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.onlinefoodorderingapp.data.local.DummyData
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import com.android.onlinefoodorderingapp.presentation.util.FoodFilter
import com.android.onlinefoodorderingapp.presentation.util.RestaurantDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantDetailViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(
        RestaurantDetailUiState(
            categories = listOf(
                "Pizza",
                "Burger",
                "Pasta",
                "Salads",
                "Desserts"
            )
        )
    )
    val state = _state.asStateFlow()

    init {
        dummyFoodList()
    }

    fun onSearchChange(searchText: String) {
        _state.update { it.copy(searchText = searchText) }
    }

    fun openMenuSheet() {
        _state.update { it.copy(isMenuSheetOpen = true) }
    }

    fun closeMenuSheet() {
        _state.update { it.copy(isMenuSheetOpen = false) }
    }

    fun onMenuClick() {
        println("Viewmodel recieved Click")
        _state.update { it.copy(isMenuSheetOpen = true) }
    }

    fun onMenuDismiss() {
        _state.update { it.copy(isMenuSheetOpen = false) }

    }

    fun onFoodItemClick(item: FoodItem) {
        println("Food Item Clicked: ${item.name}")
        _state.update { it.copy(selectedFoodItem = item) }

    }

    fun onAddItemClick() {
        println("Add Item Clicked")
    }

    private fun dummyFoodList() {
        _state.value = state.value.copy(
            allFoodItems = DummyData.foodItem,
            foodItem = DummyData.foodItem
        )
    }

    fun loadData(restaurantId: String) {
        viewModelScope.launch {

        }

    }

    fun onFilterSelected(filter: FoodFilter) {
        _state.update { current ->
            val filteredItems = when (filter) {
                FoodFilter.ALL -> current.allFoodItems
                FoodFilter.VEG -> current.allFoodItems.filter { it.isVeg }
                FoodFilter.NON_VEG -> current.allFoodItems.filter { !it.isVeg }
                FoodFilter.SPICY -> current.allFoodItems.filter { it.isSpicy }
            }

            current.copy(
                selectedFilter = filter,
                foodItem = filteredItems
            )
        }
    }

}