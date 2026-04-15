package com.android.hiltdependencytesting.data.di

import com.android.hiltdependencytesting.data.remote.api.ZomatoApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://developers.zomato.com/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer YOUR_API_KEY")
                    .addHeader("Accept", "application/json")
                    .build()

                chain.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun ProvideZomatoApiService(retrofit: Retrofit) : ZomatoApiService{
        return retrofit.create(ZomatoApiService::class.java)
    }
    /*@Provides
     @Singleton
     fun provideAuthInterceptor(): AuthInterceptor {
         return AuthInterceptor(apiKey = BuildConfig.ZOMATO_API_KEY)
     }*/
}