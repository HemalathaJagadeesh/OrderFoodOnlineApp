package com.android.onlinefoodorderingapp

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OnlineFoodOrderingApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("APP_DEBUG", "Application started")
    }
}