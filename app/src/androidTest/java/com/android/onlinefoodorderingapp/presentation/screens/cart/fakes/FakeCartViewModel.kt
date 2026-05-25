package com.android.onlinefoodorderingapp.presentation.screens.cart.fakes

import com.android.onlinefoodorderingapp.domain.model.CartItem
import kotlinx.coroutines.flow.MutableStateFlow


class FakeCartViewModel {

    val cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val totalPrice = MutableStateFlow(0.0)

    var onAdd: ((String) -> Unit)? = null
    var onRemove: ((String) -> Unit)? = null
    var onDelete: ((String) -> Unit)? = null

    fun increaseQuantity(id: String) {
        onAdd?.invoke(id)
    }

    fun decreaseQuantity(id: String) {
        onRemove?.invoke(id)
    }

    fun removeItem(id: String) {
        onDelete?.invoke(id)
    }
}
