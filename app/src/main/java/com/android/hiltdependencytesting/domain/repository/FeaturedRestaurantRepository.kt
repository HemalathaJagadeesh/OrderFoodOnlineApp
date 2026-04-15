package com.android.hiltdependencytesting.domain.repository

import com.android.hiltdependencytesting.domain.model.Restaurant

interface FeaturedRestaurantRepository {
   suspend fun getFeaturedRestaurants(): List<Restaurant>
}