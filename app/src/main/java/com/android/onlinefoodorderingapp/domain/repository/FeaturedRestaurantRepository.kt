package com.android.onlinefoodorderingapp.domain.repository

import com.android.onlinefoodorderingapp.domain.model.Restaurant

interface FeaturedRestaurantRepository {
   suspend fun getFeaturedRestaurants(): List<Restaurant>
}