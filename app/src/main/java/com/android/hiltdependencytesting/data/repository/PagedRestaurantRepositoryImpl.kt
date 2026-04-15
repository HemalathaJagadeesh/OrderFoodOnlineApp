package com.android.hiltdependencytesting.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.hiltdependencytesting.data.remote.api.ZomatoApiService
import com.android.hiltdependencytesting.domain.repository.PagedRestaurantRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import androidx.paging.Pager
import com.android.hiltdependencytesting.data.local.db.AppDatabase
import com.android.hiltdependencytesting.data.paging.RestaurantPagingSource
import com.android.hiltdependencytesting.data.remote.mediator.RestaurantRemoteMediator
import com.android.hiltdependencytesting.domain.model.Restaurant
import kotlinx.coroutines.flow.map
import androidx.paging.map
import com.android.hiltdependencytesting.data.mapper.toDomain
import com.android.hiltdependencytesting.data.mapper.toRestaurant


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