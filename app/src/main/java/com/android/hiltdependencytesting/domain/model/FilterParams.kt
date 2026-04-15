package com.android.hiltdependencytesting.domain.model

data class FilterParams(
    val query: String = "",
    val isVegMode: Boolean = false,
    val selectedTab: Int = 0,
    val location: String = "",
    val sortBy: String? = null,
    val minPrice: Int? = null,
    val maxPrice: Int? = null
)