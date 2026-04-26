package com.android.onlinefoodorderingapp.domain.repository

import com.android.onlinefoodorderingapp.domain.model.Category

interface CategoryRepository {
    suspend fun getCategories(): List<Category>
}