package com.android.hiltdependencytesting.domain.repository

import androidx.paging.PagingData
import com.android.hiltdependencytesting.domain.model.Restaurant
import kotlinx.coroutines.flow.Flow

interface PagedRestaurantRepository {
     fun getPagedRestaurants(
        query:String,
        isVegMode:Boolean,
        category:Int,
        location:String
    ): Flow<PagingData<Restaurant>>
}