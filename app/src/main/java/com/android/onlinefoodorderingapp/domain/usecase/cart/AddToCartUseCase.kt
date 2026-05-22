package com.android.onlinefoodorderingapp.domain.usecase.cart

import com.android.onlinefoodorderingapp.data.local.entity.CartEntity
import com.android.onlinefoodorderingapp.data.mapper.toFoodItem
import com.android.onlinefoodorderingapp.domain.model.CartItem
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import com.android.onlinefoodorderingapp.domain.repository.cart.CartRepository
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val repository: CartRepository
) {

    suspend operator fun invoke(food: FoodItem, quantity: Int) {
repository.addToCart(food,quantity)
    }

}