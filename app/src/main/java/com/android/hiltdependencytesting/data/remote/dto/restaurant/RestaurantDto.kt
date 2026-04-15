package com.android.hiltdependencytesting.data.remote.dto.restaurant

data class RestaurantDto(
    val id: Int,
    val name: String,
    val url: String,
    val location: String,
    val cuisines: String,
    val featured_image: String,
    val has_online_delivery: String,
    val is_delivering_now: String,
    val rating: Float,
    val deliveryTime: String,
    val isVeg: Boolean
)