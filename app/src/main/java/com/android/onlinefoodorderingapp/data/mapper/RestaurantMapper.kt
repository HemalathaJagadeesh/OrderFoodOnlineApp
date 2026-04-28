package com.android.onlinefoodorderingapp.data.mapper

import com.android.onlinefoodorderingapp.data.local.entity.CategoryEntity
import com.android.onlinefoodorderingapp.data.local.entity.RestaurantEntity
import com.android.onlinefoodorderingapp.data.remote.dto.category.CategoryDto
import com.android.onlinefoodorderingapp.data.remote.dto.restaurant.RestaurantDto
import com.android.onlinefoodorderingapp.domain.model.Category
import com.android.onlinefoodorderingapp.domain.model.Restaurant
    fun RestaurantDto.toEntity(): RestaurantEntity {
        return RestaurantEntity(
            id = id,
            name = name,
            imageUrl = url,
            rating = rating,
            deliveryTime = deliveryTime,
            isVeg = isVeg
        )
    }

    fun RestaurantDto.toDomain(): Restaurant {
        return Restaurant(
            id = id,
            name = name,
            url = url,
            location = "",
            cuisines = cuisines,
            featuredImage = featured_image,
            hasOnlineDelivery = "",
            isDeliveringNow = "",
            deliveryTime = deliveryTime,
            isVeg = isVeg
        )
    }

    fun RestaurantDto.toRestaurant(): Restaurant {
        return Restaurant(
            id = id,
            name = name,
            url = url,
            location = "",
            cuisines = cuisines,
            featuredImage = featured_image,
            hasOnlineDelivery = "",
            isDeliveringNow = "",
            deliveryTime = deliveryTime,
            isVeg = isVeg
            // map other fields here
        )
    }

fun RestaurantEntity.toRestaurant(): Restaurant{
    return Restaurant(
        id = id,
        name = name,
        url = imageUrl,
        location = "",
        cuisines = "",
        featuredImage = "",
        hasOnlineDelivery = "",
        isDeliveringNow = "",
        deliveryTime = deliveryTime,
        isVeg = isVeg

    )
}
fun RestaurantEntity.toDomain() : Restaurant{
    return Restaurant(
        id = id,
        name = name,
        url = imageUrl,
        location = "",
        cuisines = "",
        featuredImage = "",
        hasOnlineDelivery = "",
        isDeliveringNow = "",
        deliveryTime = deliveryTime,
        isVeg = isVeg
    )

}

fun CategoryEntity.toCategory(): Category{
    return Category(
        id = id,
        name = name,
        imageUrl = imageUrl
    )
}

fun CategoryDto.toCategory(): Category{
    return Category(
        id = categoryId,
        name = categoryName,
        imageUrl = categoryImageUrl
    )

}

fun CategoryDto.toEntity(): CategoryEntity{
    return CategoryEntity(
        id = categoryId,
        name = categoryName,
        imageUrl = categoryImageUrl
    )
}


