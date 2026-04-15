package com.android.hiltdependencytesting.data.repository

import com.android.hiltdependencytesting.data.local.dao.RestaurantDao
import com.android.hiltdependencytesting.data.mapper.toCategory
import com.android.hiltdependencytesting.data.mapper.toEntity
import com.android.hiltdependencytesting.data.remote.api.ZomatoApiService
import com.android.hiltdependencytesting.data.remote.dto.category.CategoryDto
import com.android.hiltdependencytesting.domain.model.Category
import com.android.hiltdependencytesting.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val api: ZomatoApiService,
    private val restaurantDao: RestaurantDao
) : CategoryRepository {
    override suspend fun getCategories(): List<Category> {
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

