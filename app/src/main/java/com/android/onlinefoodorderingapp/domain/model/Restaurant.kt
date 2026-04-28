package com.android.onlinefoodorderingapp.domain.model

data class Restaurant(
    val id: Int,
    val name: String,
    val url: String,
    val location: String,
    val cuisines: String,
    val featuredImage: String,
    val hasOnlineDelivery: String,
    val isDeliveringNow: String,
    val deliveryTime: String,
    val isVeg: Boolean
)
