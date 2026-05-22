package com.android.onlinefoodorderingapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.android.onlinefoodorderingapp.data.local.entity.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    //Insert item
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(item: CartEntity)

    //Get all cart items (reactive)
    @Query("SELECT * FROM cart_items")
    fun getCartItems(): Flow<List<CartEntity>>

    //Get single item (used in repository logic)
    @Query("SELECT * FROM cart_items WHERE foodId = :itemId LIMIT 1")
    suspend fun getItemById(itemId: String): CartEntity?

    //Flow version (optional UI use)
    @Query("SELECT * FROM cart_items WHERE foodId = :itemId LIMIT 1")
    fun getItemFlow(itemId: String): Flow<CartEntity?>

    //Update exact quantity
    @Query("UPDATE cart_items SET quantity = :quantity WHERE foodId = :itemId")
    suspend fun updateQuantity(itemId: String, quantity: Int): Int

    //Increase quantity (efficient query)
    @Query("UPDATE cart_items SET quantity = quantity + 1 WHERE foodId = :id")
    suspend fun increaseQuantity(id: String): Int

    //Decrease quantity (only if > 1)
    @Query("UPDATE cart_items SET quantity = quantity - 1 WHERE foodId = :id AND quantity > 1")
    suspend fun decreaseQuantity(id: String):Int

    //Delete by ID
    @Query("DELETE FROM cart_items WHERE foodId = :id")
    suspend fun deleteById(id: String):Int

    // Delete entity
    @Delete
    suspend fun deleteCartItem(item: CartEntity)

    // Clear entire cart
    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

    // Total item count (for badge)
    @Query("SELECT SUM(quantity) FROM cart_items")
    fun getTotalItems(): Flow<Int?>

    //Total price (for checkout)
    @Query("SELECT SUM(quantity * price) FROM cart_items")
    fun getTotalPrice(): Flow<Double?>

}