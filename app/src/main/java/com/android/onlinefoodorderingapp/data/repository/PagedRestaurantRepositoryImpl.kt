package com.android.onlinefoodorderingapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.onlinefoodorderingapp.data.remote.api.ZomatoApiService
import com.android.onlinefoodorderingapp.domain.repository.PagedRestaurantRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import androidx.paging.Pager
import com.android.onlinefoodorderingapp.data.local.db.AppDatabase
import com.android.onlinefoodorderingapp.data.remote.mediator.RestaurantRemoteMediator
import com.android.onlinefoodorderingapp.domain.model.Restaurant
import kotlinx.coroutines.flow.map
import androidx.paging.map
import com.android.onlinefoodorderingapp.data.mapper.toDomain


class PagedRestaurantRepositoryImpl @Inject constructor(
    private val api: ZomatoApiService,
    private val db: AppDatabase
) : PagedRestaurantRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedRestaurants(
        query: String,
        isVegMode: Boolean,
        category: Int,
        location: String
    ): Flow<PagingData<Restaurant>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            remoteMediator = RestaurantRemoteMediator(api,db),
            pagingSourceFactory = {
                db.restaurantDao()
                    .getPagedRestaurants()

            }
        ).flow
            .map { pagingData ->
                pagingData.map { entity -> entity.toDomain() }
            }
    }

}

/*

    /* override suspend fun getFeaturedRestaurants(
        query: String,
        isVegMode: Boolean,
        category: String?,
        location: String
    ): Flow<PagingData<Restaurant>> = Pager(
    config = PagingConfig(pageSize = 20, enablePlaceholders = false), pagingSourceFactory = {
        RestaurantPagingSource(
            api = api,
            query = query,
            isVegMode = isVegMode,
            selectedTab = 1,
            location = location
        )
    }).flow*/*/