package com.android.onlinefoodorderingapp.data.repository.cart

import android.util.Log
import com.android.onlinefoodorderingapp.data.local.dao.CartDao
import com.android.onlinefoodorderingapp.data.mapper.toCartEntity
import com.android.onlinefoodorderingapp.data.mapper.toCartItem
import com.android.onlinefoodorderingapp.domain.model.CartItem
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import com.android.onlinefoodorderingapp.domain.repository.cart.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl
@Inject constructor(
    private val cartDao: CartDao
) : CartRepository {

    override fun getCartItems(): Flow<List<CartItem>> {

        return cartDao.getCartItems()
            .map { cartItems ->
                cartItems.map { it.toCartItem() }
            }

    }



    override fun getTotalItems(): Flow<Int> {
        return cartDao.getCartItems()
            .map { cartItems ->
                cartItems.sumOf { it.quantity }
            }
    }


    override fun getTotalPrice(): Flow<Double> {
        return cartDao.getTotalPrice()
            .map { it ?: 0.0 }
    }

    /**
     * ✅ Smart Add:
     * - If item exists → increase quantity
     * - Else → insert new item
     */

    override suspend fun addToCart(item: FoodItem) {
        val id = item.foodId

        cartDao.getItemById(id)?.let {
            cartDao.increaseQuantity(id)
        } ?: run {
            cartDao.insertCartItem(item.toCartEntity())  // ✅ safe
        }
    }


    /**
     * ✅ Increase quantity directly
     */
    override suspend fun increaseQuantity(itemId: String) {

        Log.d("CartDebug", "INCREASE CALLED")

        val before = cartDao.getItemById(itemId)
        Log.d("CartDebug", "Before: ${before?.quantity}")

        cartDao.increaseQuantity(itemId)

        val after = cartDao.getItemById(itemId)
        Log.d("CartDebug", "After: ${after?.quantity}")

    }

    /**
     * ✅ Smart decrease:
     * - If qty > 1 → decrease
     * - If qty == 1 → remove item
     */
    override suspend fun decreaseQuantity(itemId: String) {
        val item = cartDao.getItemById(itemId)

        if (item != null) {
            if (item.quantity > 1) {
                cartDao.decreaseQuantity(itemId)
            } else {
                cartDao.deleteById(itemId)
            }
        }
    }

    /**
     * ✅ Remove item completely
     */
    override suspend fun removeItem(itemId: String) {
        cartDao.deleteById(itemId)
    }

    /**
     * ✅ Clear entire cart
     */
    override suspend fun clearCart() {
        cartDao.clearCart()
    }

    override suspend fun getCartItemById(foodId: String) {
        cartDao.getItemById(foodId)
    }

}
