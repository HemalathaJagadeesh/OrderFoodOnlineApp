package com.android.onlinefoodorderingapp.domain.usecase.cart

import com.android.onlinefoodorderingapp.domain.model.CartItem
import com.android.onlinefoodorderingapp.domain.repository.cart.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartItemsUseCase @Inject constructor(
    private val repository: CartRepository
) {

    operator fun invoke(): Flow<List<CartItem>> {
        return repository.getCartItems()
    }

}
