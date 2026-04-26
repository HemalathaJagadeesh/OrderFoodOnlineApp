package com.android.onlinefoodorderingapp.data.remote.dto.category

import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("category_id") val categoryId: Int,
    @SerializedName("category_name") val categoryName: String,
    @SerializedName("category_image_url") val categoryImageUrl: String
)
