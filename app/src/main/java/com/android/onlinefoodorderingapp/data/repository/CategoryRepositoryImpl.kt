package com.android.onlinefoodorderingapp.data.repository

import com.android.onlinefoodorderingapp.config.AppConfig
import com.android.onlinefoodorderingapp.data.local.DummyData
import com.android.onlinefoodorderingapp.data.local.dao.RestaurantDao
import com.android.onlinefoodorderingapp.data.mapper.toCategory
import com.android.onlinefoodorderingapp.data.mapper.toEntity
import com.android.onlinefoodorderingapp.data.remote.api.ZomatoApiService
import com.android.onlinefoodorderingapp.domain.model.Category
import com.android.onlinefoodorderingapp.domain.repository.CategoryRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val api: ZomatoApiService,
    private val restaurantDao: RestaurantDao
) : CategoryRepository {
    override suspend fun getCategories(): List<Category> {
        return if (AppConfig.USE_FAKE_DATA) {
            delay(500)
            DummyData.categories
        } else {
            //First, get data from local database
            val localCategories = restaurantDao.getCategories()
            if (localCategories.isNotEmpty()) {
                return localCategories.map { it.toCategory() }
            }
            val apiResponse = api.getCategories()
            restaurantDao.insertCategories(apiResponse.map { it.toEntity() })
            return apiResponse.map { it.toCategory() }
        }
    }
}

