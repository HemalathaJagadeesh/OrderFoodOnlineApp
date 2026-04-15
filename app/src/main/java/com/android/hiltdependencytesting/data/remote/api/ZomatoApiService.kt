package com.android.hiltdependencytesting.data.remote.api

import com.android.hiltdependencytesting.data.remote.dto.category.CategoryDto
import com.android.hiltdependencytesting.data.remote.dto.restaurant.RestaurantResponse
import com.android.hiltdependencytesting.domain.model.Category
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ZomatoApiService {

    @GET("api/v2.1/categories")
    suspend fun getCategories(): List<CategoryDto>


    @GET("api/v2.1/search")
    suspend fun getRestaurants(
        @Query("start") start: Int,
        @Query("count") count: Int,
        @Query("q") query: String? = null,
        @Query("is_veg") isVeg: Boolean = false
    ): RestaurantResponse


    suspend fun getFeaturedRestaurants(): RestaurantResponse

}