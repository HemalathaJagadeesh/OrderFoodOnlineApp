package com.android.hiltdependencytesting.domain.repository

import com.android.hiltdependencytesting.domain.model.Category

interface CategoryRepository {
    suspend fun getCategories(): List<Category>
}