package com.android.hiltdependencytesting.domain.usecase

import androidx.paging.PagingData
import com.android.hiltdependencytesting.domain.model.Restaurant
import com.android.hiltdependencytesting.domain.repository.PagedRestaurantRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagedRestaurantUseCase @Inject constructor(
    private val repository: PagedRestaurantRepository
) {
    suspend operator fun invoke(
        query: String,
        isVegMode: Boolean,
        category: Int,
        location: String
    ): Flow<PagingData<Restaurant>> {
        return repository.getPagedRestaurants(query, isVegMode, category, location)

    }
}