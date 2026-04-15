package com.android.hiltdependencytesting.data.di

import com.android.hiltdependencytesting.data.local.db.AppDatabase
import com.android.hiltdependencytesting.data.remote.api.ZomatoApiService
import com.android.hiltdependencytesting.data.repository.CategoryRepositoryImpl
import com.android.hiltdependencytesting.data.repository.FeaturedRestaurantRepositoryImpl
import com.android.hiltdependencytesting.data.repository.PagedRestaurantRepositoryImpl
import com.android.hiltdependencytesting.domain.repository.CategoryRepository
import com.android.hiltdependencytesting.domain.repository.FeaturedRestaurantRepository
import com.android.hiltdependencytesting.domain.repository.PagedRestaurantRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {



   /* @Binds
    abstract fun bindFeaturedRestaurantRepository(featuredRestaurantRepositoryImpl: FeaturedRestaurantRepositoryImpl):
            FeaturedRestaurantRepository*/

    @Binds
    @Singleton
    abstract fun bindPagedRestaurantRepository(pagedRestaurantRepository: PagedRestaurantRepositoryImpl):
            PagedRestaurantRepository

}