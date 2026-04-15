package com.android.hiltdependencytesting.data.mapper

import com.android.hiltdependencytesting.data.local.entity.CategoryEntity
import com.android.hiltdependencytesting.data.local.entity.RestaurantEntity
import com.android.hiltdependencytesting.data.remote.dto.category.CategoryDto
import com.android.hiltdependencytesting.data.remote.dto.restaurant.RestaurantDto
import com.android.hiltdependencytesting.domain.model.Category
import com.android.hiltdependencytesting.domain.model.Restaurant
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
            featured_image = featured_image,
            has_online_delivery = "",
            is_delivering_now = "",
            deliveryTime = deliveryTime
        )
    }

    fun RestaurantDto.toRestaurant(): Restaurant {
        return Restaurant(
            id = id,
            name = name,
            url = url,
            location = "",
            cuisines = cuisines,
            featured_image = featured_image,
            has_online_delivery = "",
            is_delivering_now = "",
            deliveryTime = deliveryTime
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
        featured_image = "",
        has_online_delivery = "",
        is_delivering_now = "",
        deliveryTime = deliveryTime

    )
}
fun RestaurantEntity.toDomain() : Restaurant{
    return Restaurant(
        id = id,
        name = name,
        url = imageUrl,
        location = "",
        cuisines = "",
        featured_image = "",
        has_online_delivery = "",
        is_delivering_now = "",
        deliveryTime = deliveryTime
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


