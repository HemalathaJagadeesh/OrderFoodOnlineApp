package com.android.onlinefoodorderingapp.data.repository

import com.android.onlinefoodorderingapp.data.local.dao.RestaurantDao
import com.android.onlinefoodorderingapp.data.mapper.toRestaurant
import com.android.onlinefoodorderingapp.data.remote.api.ZomatoApiService
import com.android.onlinefoodorderingapp.domain.model.Restaurant
import com.android.onlinefoodorderingapp.domain.repository.FeaturedRestaurantRepository
import javax.inject.Inject
import com.android.onlinefoodorderingapp.data.mapper.toEntity

class FeaturedRestaurantRepositoryImpl @Inject constructor(
    private val api: ZomatoApiService,
    private val restaurantDao: RestaurantDao
) :
    FeaturedRestaurantRepository {
    override suspend fun getFeaturedRestaurants(): List<Restaurant> {
        val localFeaturedRestaurants = restaurantDao.getFeaturedRestaurants()
        if (localFeaturedRestaurants.isNotEmpty()) {
            return restaurantDao.getFeaturedRestaurants().map { it.toRestaurant() }
        }
        val apiResponse = api.getFeaturedRestaurants()
        restaurantDao.insertRestaurants(apiResponse.restaurants.map { it.restaurant.toEntity() })

        return api.getFeaturedRestaurants().restaurants.map { it.restaurant.toRestaurant() }
    }

}