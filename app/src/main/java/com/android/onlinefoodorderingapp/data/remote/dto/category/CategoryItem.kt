package com.android.onlinefoodorderingapp.data.remote.dto.category

import com.android.onlinefoodorderingapp.domain.model.Category
import com.google.gson.annotations.SerializedName

data class CategoryItem(
    @SerializedName ("categories") val category: Category
)
