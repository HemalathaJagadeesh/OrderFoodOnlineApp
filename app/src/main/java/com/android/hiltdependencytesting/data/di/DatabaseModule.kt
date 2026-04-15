package com.android.hiltdependencytesting.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.android.hiltdependencytesting.data.local.db.AppDatabase
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
        ).addCallback(object : RoomDatabase.Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                db.execSQL("INSERT INTO restaurants (id, name, imageUrl, rating, deliveryTime, isVeg) VALUES (1, 'Pizza Place', 'https://example.com/pizza.jpg', 4.5, '30 mins', 0)")
                db.execSQL("ISERT INTO Category (id, name, imageUrl) VALUES (1, 'Pizza', 'https://example.com/pizza.jpg")
                db.execSQL("INSERT INTO restaurants (id, name, imageUrl, rating, deliveryTime, isVeg) VALUES")
            }
        })
            .fallbackToDestructiveMigration().build()
    }

        @Provides
        fun provideRestaurantDao(database: AppDatabase) = database.restaurantDao()
}