package com.android.hiltdependencytesting.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.hiltdependencytesting.data.local.entity.CategoryEntity
import com.android.hiltdependencytesting.data.local.entity.RestaurantEntity

@Dao
interface RestaurantDao {

    @Query("SELECT * FROM restaurants")
    fun getPagedRestaurants(): PagingSource<Int, RestaurantEntity>

    @Query("SELECT * FROM restaurants ORDER BY id ASC ")
    suspend fun getFeaturedRestaurants(): List<RestaurantEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurants(restaurants: List<RestaurantEntity>)

    @Query("DELETE FROM restaurants")
    suspend fun clearRestaurants()

    @Query("SELECT * FROM category")
    suspend fun getCategories(): List<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<CategoryEntity>)
}