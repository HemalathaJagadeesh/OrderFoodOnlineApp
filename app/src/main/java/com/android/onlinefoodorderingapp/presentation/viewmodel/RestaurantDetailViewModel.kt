package com.android.onlinefoodorderingapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.android.onlinefoodorderingapp.R
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import com.android.onlinefoodorderingapp.presentation.util.RestaurantDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RestaurantDetailViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(RestaurantDetailUiState(categories = listOf("Pizza", "Burger", "Pasta", "Salads", "Desserts")))
    val state = _state.asStateFlow()

    init {
        dummyFoodList()
    }

    fun onSearchChange(searchText: String){
        _state.update { it.copy(searchText = searchText) }
    }

    fun openMenuSheet(){
        _state.update { it.copy(isMenuSheetOpen = true) }
    }

    fun closeMenuSheet(){
        _state.update { it.copy(isMenuSheetOpen = false) }
    }

    fun onMenuClick(){
        println("Viewmodel recieved Click")
        _state.update { it.copy(isMenuSheetOpen = true) }
    }

    fun onMenuDismiss(){
        _state.update { it.copy(isMenuSheetOpen = false) }

    }

    fun onFoodItemClick(item: FoodItem){
        println("Food Item Clicked: ${item.name}")
        _state.update { it.copy(selectedFoodItem = item) }

    }

    fun onAddItemClick(){
println("Add Item Clicked")
    }




    fun dummyFoodList() {
        _state.value = state.value.copy(
            foodItem = listOf(
                FoodItem(
                    name = "Spicy Chicken Crunch",
                    description = "Crispy chicken tossed in signature spicy sauce",
                    price = "269",
                    image = "https://images.unsplash.com/photo-1600891964599-f61ba0e24092",
                    id = "1"
                ),
                FoodItem(
                    name = "Ultimate Cheesy Nachos",
                    description = "Loaded nachos with cheese, jalapenos & salsa",
                    price = "229",
                    image = "https://images.unsplash.com/photo-1600891964599-f61ba0e24092",
                    id = "2"
                ),
                FoodItem(
                    name = "Spicy Chicken Crunch",
                    description = "Crispy chicken tossed in signature spicy sauce",
                    price = "269",
                    image = "https://images.unsplash.com/photo-1600891964599-f61ba0e24092",
                    id = "3"
                ),
                FoodItem(
                    name = "Ultimate Cheesy Nachos",
                    description = "Loaded nachos with cheese, jalapenos & salsa",
                    price = "229",
                    image = "https://images.unsplash.com/photo-1600891964599-f61ba0e24092",
                    id = "4"
                )
            )
        )
    }

}