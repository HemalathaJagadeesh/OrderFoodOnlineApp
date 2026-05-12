package com.android.onlinefoodorderingapp.di

import android.content.Context
import androidx.room.Room
import com.android.onlinefoodorderingapp.data.local.dao.CartDao
import com.android.onlinefoodorderingapp.data.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideRestaurantDao(database: AppDatabase) = database.restaurantDao()

    @Provides
    fun provideUserDao(database: AppDatabase) = database.UserDao()


    @Provides
    fun provideCartDao(db: AppDatabase): CartDao = db.cartDao()

}