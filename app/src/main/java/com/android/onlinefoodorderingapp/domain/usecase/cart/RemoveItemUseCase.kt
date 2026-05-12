package com.android.onlinefoodorderingapp.domain.usecase.cart

import com.android.onlinefoodorderingapp.domain.repository.cart.CartRepository
import javax.inject.Inject

class RemoveItemUseCase
@Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(foodId: String) {
        repository.removeItem(foodId)
    }
}
