package com.android.onlinefoodorderingapp.di

import com.android.onlinefoodorderingapp.data.local.dao.UserDao
import com.android.onlinefoodorderingapp.data.local.db.AppDatabase
import com.android.onlinefoodorderingapp.data.remote.api.ZomatoApiService
import com.android.onlinefoodorderingapp.data.repository.CategoryRepositoryImpl
import com.android.onlinefoodorderingapp.data.repository.FeaturedRestaurantRepositoryImpl
import com.android.onlinefoodorderingapp.data.repository.auth.GetLoggedInUserRepositoryImpl
import com.android.onlinefoodorderingapp.data.repository.auth.LogoutRepositoyImpl
import com.android.onlinefoodorderingapp.data.repository.auth.SendOtpRepositoryImpl
import com.android.onlinefoodorderingapp.data.repository.auth.VerifyOtpRepositoryImpl
import com.android.onlinefoodorderingapp.domain.repository.CategoryRepository
import com.android.onlinefoodorderingapp.domain.repository.FeaturedRestaurantRepository
import com.android.onlinefoodorderingapp.domain.repository.auth.LoggedInUserRepository
import com.android.onlinefoodorderingapp.domain.repository.auth.LogoutRepository
import com.android.onlinefoodorderingapp.domain.repository.auth.SendOtpRepository
import com.android.onlinefoodorderingapp.domain.repository.auth.VerifyOtpRepository
import com.android.onlinefoodorderingapp.presentation.util.OtpManager
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

    @Provides
    @Singleton
    fun provideSendOtpRepository(otpManager: OtpManager): SendOtpRepository = SendOtpRepositoryImpl(otpManager)

    @Provides
    @Singleton
    fun provideVerifyOtpRepository(userDao: UserDao, otpManager: OtpManager): VerifyOtpRepository =
        VerifyOtpRepositoryImpl(userDao, otpManager)

    @Provides
    @Singleton
    fun provideGetLoggedInUserRepository(userDao: UserDao): LoggedInUserRepository =
        GetLoggedInUserRepositoryImpl(userDao)

    @Provides
    @Singleton
    fun provideLogoutRepository(dao: UserDao): LogoutRepository =
        LogoutRepositoyImpl(dao)

}