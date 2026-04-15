package com.android.hiltdependencytesting.domain.model

data class Restaurant(
    val id: Int,
    val name: String,
    val url: String,
    val location: String,
    val cuisines: String,
    val featured_image: String,
    val has_online_delivery: String,
    val is_delivering_now: String,
    val deliveryTime: String
)
