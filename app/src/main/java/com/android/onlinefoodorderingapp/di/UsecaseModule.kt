package com.android.onlinefoodorderingapp.di

import com.android.onlinefoodorderingapp.data.repository.auth.GetLoggedInUserRepositoryImpl
import com.android.onlinefoodorderingapp.domain.repository.auth.LoggedInUserRepository
import com.android.onlinefoodorderingapp.domain.repository.auth.LogoutRepository
import com.android.onlinefoodorderingapp.domain.repository.auth.SendOtpRepository
import com.android.onlinefoodorderingapp.domain.repository.auth.VerifyOtpRepository
import com.android.onlinefoodorderingapp.domain.usecase.auth.GetLoggedInUserUsecase
import com.android.onlinefoodorderingapp.domain.usecase.auth.LogoutUsecase
import com.android.onlinefoodorderingapp.domain.usecase.auth.SendOtpUseCase
import com.android.onlinefoodorderingapp.domain.usecase.auth.VerifyOtpUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UsecaseModule {

    @Provides
    fun provideSendOtpUsecase(repo: SendOtpRepository) = SendOtpUseCase(repo)

    @Provides
    fun provideVerifyOtpUsecase(repo: VerifyOtpRepository) = VerifyOtpUsecase(repo)

    @Provides
    fun provideGetLoggedInUserUsecase(repo: LoggedInUserRepository)= GetLoggedInUserUsecase(repo)

    @Provides
    fun provideLogoutUsecase(repo: LogoutRepository) = LogoutUsecase(repo)
}