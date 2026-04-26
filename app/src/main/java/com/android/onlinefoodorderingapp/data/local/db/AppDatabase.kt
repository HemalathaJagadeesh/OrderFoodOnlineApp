package com.android.onlinefoodorderingapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.onlinefoodorderingapp.data.local.dao.RestaurantDao
import com.android.onlinefoodorderingapp.data.local.dao.UserDao
import com.android.onlinefoodorderingapp.data.local.entity.CategoryEntity
import com.android.onlinefoodorderingapp.data.local.entity.RestaurantEntity
import com.android.onlinefoodorderingapp.data.local.entity.UserEntity

@Database(entities = [RestaurantEntity::class, UserEntity::class, CategoryEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao
    abstract fun UserDao() : UserDao
}