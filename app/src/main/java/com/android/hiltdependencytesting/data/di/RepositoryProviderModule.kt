package com.android.hiltdependencytesting.data.di

import com.android.hiltdependencytesting.data.local.db.AppDatabase
import com.android.hiltdependencytesting.data.remote.api.ZomatoApiService
import com.android.hiltdependencytesting.data.repository.CategoryRepositoryImpl
import com.android.hiltdependencytesting.data.repository.FeaturedRestaurantRepositoryImpl
import com.android.hiltdependencytesting.domain.repository.CategoryRepository
import com.android.hiltdependencytesting.domain.repository.FeaturedRestaurantRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryProviderModule {

    @Provides
    @Singleton
    fun provideCategoryRepository(apiService: ZomatoApiService, database: AppDatabase): CategoryRepository {
        return CategoryRepositoryImpl(apiService,database.restaurantDao())
    }
    @Provides
    @Singleton
     fun provideFeaturedRestaurantRepository(apiService: ZomatoApiService, database: AppDatabase):
            FeaturedRestaurantRepository{
         return FeaturedRestaurantRepositoryImpl(apiService,database.restaurantDao())
     }
}