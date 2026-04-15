package com.android.hiltdependencytesting.domain.usecase

import com.android.hiltdependencytesting.domain.model.Category
import com.android.hiltdependencytesting.domain.repository.CategoryRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(): List<Category> {
        return categoryRepository.getCategories()
    }
}