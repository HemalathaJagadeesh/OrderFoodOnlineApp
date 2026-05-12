package com.android.onlinefoodorderingapp.domain.usecase.cart

import com.android.onlinefoodorderingapp.data.local.entity.CartEntity
import com.android.onlinefoodorderingapp.domain.repository.cart.CartRepository
import javax.inject.Inject

class DecreaseQuantityUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(itemId: String) {
        repository.decreaseQuantity(itemId)
    }

  /*  suspend operator fun invoke(itemId: CartEntity) {
        repository.decreaseQuantity(itemId)
    }*/
}