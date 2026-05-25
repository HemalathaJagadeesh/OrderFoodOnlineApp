package com.android.onlinefoodorderingapp.domain.repository.cart

import com.android.onlinefoodorderingapp.data.local.entity.CartEntity
import com.android.onlinefoodorderingapp.domain.model.CartItem
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    fun getCartItems(): Flow<List<CartItem>>

    fun getTotalItems(): Flow<Int>

    fun getTotalPrice(): Flow<Double>

    suspend fun addToCart(item: FoodItem,quantity: Int)

    suspend fun increaseQuantity(foodId: String)

    suspend fun decreaseQuantity(foodId: String)

    suspend fun removeItem(foodId: String)

    suspend fun clearCart()
    suspend fun getCartItemById(foodId: String): CartItem?

    fun interface GetCartCount {
        operator fun invoke(): Flow<Int>
    }
}

