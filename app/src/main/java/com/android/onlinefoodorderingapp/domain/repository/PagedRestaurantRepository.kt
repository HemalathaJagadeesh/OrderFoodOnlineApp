package com.android.onlinefoodorderingapp.domain.repository

import androidx.paging.PagingData
import com.android.onlinefoodorderingapp.domain.model.Restaurant
import kotlinx.coroutines.flow.Flow

interface PagedRestaurantRepository {
     fun getPagedRestaurants(
        query:String,
        isVegMode:Boolean,
        category:Int,
        location:String
    ): Flow<PagingData<Restaurant>>
}