package com.android.hiltdependencytesting.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Category")
data class CategoryEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String
)
