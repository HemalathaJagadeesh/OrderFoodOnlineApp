package com.android.onlinefoodorderingapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.android.onlinefoodorderingapp.data.mapper.toRestaurant
import com.android.onlinefoodorderingapp.data.remote.api.ZomatoApiService
import com.android.onlinefoodorderingapp.domain.model.Restaurant

class RestaurantPagingSource(
    private val api: ZomatoApiService,
    private val query: String,
    private val isVegMode: Boolean,
    private val selectedTab: Int,
    private val location: String
) : PagingSource<Int, Restaurant>() {
    override fun getRefreshKey(state: PagingState<Int, Restaurant>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1) ?: state.closestPageToPosition(
                position
            )?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Restaurant> {
        return try {
            val page = params.key ?: 1
            val response = api.getRestaurants(
                start = page, count = params.loadSize, isVeg = isVegMode, query = "selectedTab"
            )

            val restaurants = response.restaurants.map {
                it.restaurant.toRestaurant()
            }

            LoadResult.Page(
                data = restaurants,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.restaurants.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}