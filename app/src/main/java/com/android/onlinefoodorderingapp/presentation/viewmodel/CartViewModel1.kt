package com.android.onlinefoodorderingapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.onlinefoodorderingapp.data.local.entity.CartEntity
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import com.android.onlinefoodorderingapp.domain.repository.cart.CartRepository
import com.android.onlinefoodorderingapp.domain.usecase.cart.AddToCartUseCase
import com.android.onlinefoodorderingapp.domain.usecase.cart.DecreaseQuantityUseCase
import com.android.onlinefoodorderingapp.domain.usecase.cart.GetCartItemsUseCase
import com.android.onlinefoodorderingapp.domain.usecase.cart.IncreaseQuantityUseCase
import com.android.onlinefoodorderingapp.presentation.screens.cart.CartUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel1 @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val increaseQuantityUseCase: IncreaseQuantityUseCase,
    private val decreaseQuantityUseCase: DecreaseQuantityUseCase,
    private val addToCartUseCase: AddToCartUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())

    val uiState = _uiState.asStateFlow()
    val cartItems = getCartItemsUseCase()
        .onEach {  Log.d("CartFlow", "Emitted: $it") }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyList()
        )

    init {
        Log.i("TAG", "init: ")
        observeCartItems()
    }

    private fun observeCartItems() {
/*
        viewModelScope.launch {

            getCartItemsUseCase()
                .collect { items ->

                    _uiState.update {
                        Log.i("getCartItemsUseCase", "observeCartItems:$items ")
                        it.copy(
                            cartItems = items,
                            totalPrice = items.sumOf { item ->
                                item.price * item.quantity
                            }
                        )
                    }
                }
        }*/
    }

    fun increaseQuantity(item: String) {
        viewModelScope.launch {
          increaseQuantityUseCase(item)
        }
    }

    fun decreaseQuantity(item: String) {

        viewModelScope.launch {

            decreaseQuantityUseCase(item)
        }
    }

    fun addToCart(food: FoodItem) {
        viewModelScope.launch {
          //  addToCartUseCase(food)
        }
    }
}