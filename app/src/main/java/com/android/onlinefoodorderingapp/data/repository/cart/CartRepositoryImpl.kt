package com.android.onlinefoodorderingapp.data.repository.cart

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
     *
     * - If item exists → increase quantity
     * - Else → insert new item
     */

    override suspend fun addToCart(item: FoodItem, quantity: Int) {

        val id = item.foodId

        val existingItem = cartDao.getItemById(id)

        if (existingItem != null) {
            val newQuantity = existingItem.quantity + quantity
            cartDao.updateQuantity(id, newQuantity)
        } else {
            cartDao.insertCartItem(
                item.toCartEntity().copy(quantity = quantity)
            )
        }

    }


    /**
     * Increase quantity directly
     */
    override suspend fun increaseQuantity(foodId: String) {
        //val before = cartDao.getItemById(itemId)
        cartDao.increaseQuantity(foodId)
        //val after = cartDao.getItemById(itemId)
    }

    /**
     * - If qty > 1 → decrease
     * - If qty == 1 → remove item
     */
    override suspend fun decreaseQuantity(foodId: String) {
        val item = cartDao.getItemById(foodId)

        if (item != null) {
            if (item.quantity > 1) {
                cartDao.decreaseQuantity(foodId)
            } else {
                cartDao.deleteById(foodId)
            }
        }
    }

    /**
     * Remove item completely
     */
    override suspend fun removeItem(foodId: String) {
        cartDao.deleteById(foodId)
    }

    /**
     *  Clear entire cart
     */
    override suspend fun clearCart() {
        cartDao.clearCart()
    }

    override suspend fun getCartItemById(foodId: String): CartItem? {
        val item = cartDao.getItemById(foodId)
        return item?.toCartItem()
    }

}
