package com.android.onlinefoodorderingapp.data.mapper

import com.android.onlinefoodorderingapp.data.local.entity.CartEntity
import com.android.onlinefoodorderingapp.domain.model.CartItem
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem

fun CartEntity.toFoodItem(): FoodItem{

    return  FoodItem(
            foodId = foodId,
            name = name,
            price = price,
            image = image,
            description = description,
            isSpicy = false,
            isVeg = false,
            category = category
        )
}


fun FoodItem.toCartEntity(initialQuantity: Int = 1): CartEntity {
    return CartEntity(
        foodId = foodId,
        name = name,
        price = price,
        image = image,
        description = description,
        isSpicy = isSpicy,
        isVeg = isVeg,
        category = category,
        quantity = initialQuantity
    )
}


fun CartEntity.toCartItem(): CartItem {
    return CartItem(
        foodItem = FoodItem(
            foodId = foodId,
            name = name,
            price = price,
            image = image,
            description = description,
            isSpicy = isSpicy,
            isVeg = isVeg,
            category = category
        ),
        quantity = quantity
    )
}

fun CartItem.toCartEntity(): CartEntity {
    return CartEntity(
        foodId = foodItem.foodId,
        name = foodItem.name,
        price = foodItem.price,
        image = foodItem.image,
        description = foodItem.description,
        isSpicy = foodItem.isSpicy,
        isVeg = foodItem.isVeg,
        category = foodItem.category,
        quantity = quantity
    )
}

