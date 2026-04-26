package com.android.onlinefoodorderingapp.domain.usecase

import com.android.onlinefoodorderingapp.domain.model.Restaurant
import com.android.onlinefoodorderingapp.domain.repository.FeaturedRestaurantRepository
import javax.inject.Inject

class GetFeaturedRestaurantsUseCase @Inject constructor(
    private val featuredRestaurantRepository: FeaturedRestaurantRepository) {
    suspend operator fun invoke(): List<Restaurant> {
        return featuredRestaurantRepository.getFeaturedRestaurants()
    }
}