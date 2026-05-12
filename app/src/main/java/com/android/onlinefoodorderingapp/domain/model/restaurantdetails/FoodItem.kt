package com.android.onlinefoodorderingapp.domain.model.restaurantdetails

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FoodItem(
    val foodId: String,
    val name: String,
    val price: Double,
    val image: String,
    val description: String,
    val isSpicy: Boolean,
    val isVeg: Boolean,
    val category: String
) : Parcelable
