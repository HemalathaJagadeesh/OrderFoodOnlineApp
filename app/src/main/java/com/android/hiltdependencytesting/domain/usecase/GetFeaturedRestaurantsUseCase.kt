package com.android.hiltdependencytesting.domain.usecase

import com.android.hiltdependencytesting.domain.model.Restaurant
import com.android.hiltdependencytesting.domain.repository.FeaturedRestaurantRepository
import javax.inject.Inject

class GetFeaturedRestaurantsUseCase @Inject constructor(
    private val featuredRestaurantRepository: FeaturedRestaurantRepository) {
    suspend operator fun invoke(): List<Restaurant> {
        return featuredRestaurantRepository.getFeaturedRestaurants()
    }
}