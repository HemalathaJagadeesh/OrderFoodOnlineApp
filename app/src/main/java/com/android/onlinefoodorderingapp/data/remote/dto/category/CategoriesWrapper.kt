package com.android.onlinefoodorderingapp.data.remote.dto.category

import com.google.gson.annotations.SerializedName

data class CategoriesWrapper(
    @SerializedName("categories") val categories: List<CategoryItem>)
