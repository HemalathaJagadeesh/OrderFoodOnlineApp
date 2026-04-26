package com.android.onlinefoodorderingapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurants")
data class RestaurantEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String,
    val rating: Float,
    val deliveryTime: String,
    val isVeg: Boolean,

    /*val id: Int,
    val name: String,
    val url: String,
    val location: String,
    val cuisines: String,
    val featured_image: String,
    val has_online_delivery: String,
    val is_delivering_now: String,*/
)
