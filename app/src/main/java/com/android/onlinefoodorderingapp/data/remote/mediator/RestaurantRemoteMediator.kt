package com.android.onlinefoodorderingapp.data.remote.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.android.onlinefoodorderingapp.data.local.db.AppDatabase
import com.android.onlinefoodorderingapp.data.local.entity.RestaurantEntity
import com.android.onlinefoodorderingapp.data.mapper.toEntity
import com.android.onlinefoodorderingapp.data.remote.api.ZomatoApiService

@OptIn(ExperimentalPagingApi::class)
class RestaurantRemoteMediator(
    private val api: ZomatoApiService,
    private val db: AppDatabase
) : RemoteMediator<Int, RestaurantEntity>() {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RestaurantEntity>
    ): MediatorResult {

        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.APPEND -> (state.pages.size) + 1
                LoadType.PREPEND -> return MediatorResult.Success(true)
            }

            val response = api.getRestaurants(start = 1,
                count = state.pages.size,
                query = "",
                isVeg = false,)

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.restaurantDao().clearRestaurants()
                }
                val restaurants = response.restaurants.map { it.restaurant.toEntity() }

                db.restaurantDao().insertRestaurants(restaurants)
            }

            MediatorResult.Success(
                endOfPaginationReached = response.restaurants.isEmpty()
            )

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}