package com.android.onlinefoodorderingapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.android.onlinefoodorderingapp.domain.model.CartItem
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CartViewModel2 @Inject constructor() : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems = _cartItems.asStateFlow()

    val cartCount = _cartItems
        .map { list -> list.sumOf { it.quantity } }

    fun addToCart(item: FoodItem, quantity: Int) {

        _cartItems.update { current ->

            val existing = current.find { it.foodItem.foodId == item.foodId }

            if (existing == null) {
                current + CartItem(item, quantity)
            } else {
                current.map {
                    if (it.foodItem.foodId == item.foodId)
                        it.copy(quantity = it.quantity + quantity)
                    else it
                }
            }
        }

    }

    fun increaseQuantity(id: String) {
        _cartItems.update { list ->
            list.map {
                if (it.foodItem.foodId == id)
                    it.copy(quantity = it.quantity + 1)
                else it
            }
        }
    }

    fun decreaseQuantity(id: String) {
        _cartItems.update { list ->
            list.map {
                if (it.foodItem.foodId == id)
                    it.copy(quantity = it.quantity - 1)
                else it
            }.filter { it.quantity > 0 }
        }
    }

    fun removeItem(id: String) {
        _cartItems.update { list ->
            list.filter { it.foodItem.foodId != id }
        }
    }
}