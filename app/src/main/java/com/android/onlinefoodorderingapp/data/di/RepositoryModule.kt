package com.android.onlinefoodorderingapp.data.di

import com.android.onlinefoodorderingapp.data.repository.PagedRestaurantRepositoryImpl
import com.android.onlinefoodorderingapp.domain.repository.PagedRestaurantRepository
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