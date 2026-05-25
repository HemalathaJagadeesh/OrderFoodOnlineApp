package com.android.onlinefoodorderingapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.onlinefoodorderingapp.data.mapper.toCartEntity
import com.android.onlinefoodorderingapp.domain.model.CartItem
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import com.android.onlinefoodorderingapp.domain.usecase.cart.AddToCartUseCase
import com.android.onlinefoodorderingapp.domain.usecase.cart.DecreaseQuantityUseCase
import com.android.onlinefoodorderingapp.domain.usecase.cart.GetCartCountUseCase
import com.android.onlinefoodorderingapp.domain.usecase.cart.GetCartItemByIdUseCase
import com.android.onlinefoodorderingapp.domain.usecase.cart.GetCartItemsUseCase
import com.android.onlinefoodorderingapp.domain.usecase.cart.GetTotalPriceUseCase
import com.android.onlinefoodorderingapp.domain.usecase.cart.IncreaseQuantityUseCase
import com.android.onlinefoodorderingapp.domain.usecase.cart.RemoveItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(

    private val addToCartUseCase: AddToCartUseCase,
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val removeItemUseCase: RemoveItemUseCase,
    private val getTotalPriceUseCase: GetTotalPriceUseCase,
    private val getCartCountUseCase: GetCartCountUseCase,
    private val increaseQuantityUseCase: IncreaseQuantityUseCase,
    private val decreaseQuantityUseCase: DecreaseQuantityUseCase,
    private val getCartItemByIdUseCase: GetCartItemByIdUseCase

) : ViewModel() {



    val cartItems = getCartItemsUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    val totalPrice = getTotalPriceUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            0.0
        )

    val cartCount = getCartCountUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            0
        )


    /*init {


        viewModelScope.launch {
            cartItems.collect { list ->
                Log.d("DEBUG_VM", "COLLECTING STARTED")

                list.forEach {
                    Log.d("DEBUG_VM", "${it.foodItem.name} qty=${it.quantity}")
                }
            }
        }

    }*/

    fun removeFromCart(id: String) {
        viewModelScope.launch {
            removeItemUseCase(id)
        }
    }



    fun addToCart(food: FoodItem, quantity: Int) {
        viewModelScope.launch {
            addToCartUseCase(food, quantity)
        }
    }

    fun removeItem(id: String) {
        viewModelScope.launch {
            removeItemUseCase(id)
        }
    }

    fun increaseQuantity(id: String) {
        viewModelScope.launch {

            val item =getCartItemByIdUseCase(id)

            increaseQuantityUseCase(id)
        }
    }

    fun decreaseQuantity(id: String) {
        viewModelScope.launch {
            decreaseQuantityUseCase(id)
        }
    }


}