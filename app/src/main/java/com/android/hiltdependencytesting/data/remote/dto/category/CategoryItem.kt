package com.android.hiltdependencytesting.data.remote.dto.category

import com.android.hiltdependencytesting.domain.model.Category
import com.google.gson.annotations.SerializedName

data class CategoryItem(
    @SerializedName ("categories") val category: Category
)
