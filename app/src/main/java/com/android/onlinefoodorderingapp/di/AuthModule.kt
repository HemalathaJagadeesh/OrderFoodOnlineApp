package com.android.onlinefoodorderingapp.di

import android.content.Context
import com.android.onlinefoodorderingapp.data.repository.auth.AuthRepositoryImpl
import com.android.onlinefoodorderingapp.domain.repository.auth.AuthRepository
import com.android.onlinefoodorderingapp.domain.repository.auth.UserRepository
import com.android.onlinefoodorderingapp.domain.usecase.auth.SendFirebaseOtpUsecase
import com.android.onlinefoodorderingapp.domain.usecase.auth.VerifyFirebaseOtpUsecase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun provideAuthRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore,
        @ApplicationContext context: Context
    ): AuthRepository {
        return AuthRepositoryImpl(auth)
    }

    @Provides
    fun provideSendOtpUseCase(repo: AuthRepository) = SendFirebaseOtpUsecase(repo)

    @Provides
    fun provideVerifyOtpUseCase(repo: AuthRepository) = VerifyFirebaseOtpUsecase(repo)
}