package com.android.hiltdependencytesting.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.hiltdependencytesting.data.local.dao.RestaurantDao
import com.android.hiltdependencytesting.data.local.entity.CategoryEntity
import com.android.hiltdependencytesting.data.local.entity.RestaurantEntity

@Database(entities = [RestaurantEntity::class, CategoryEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao
}