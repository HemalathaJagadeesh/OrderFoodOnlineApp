package com.android.onlinefoodorderingapp.domain.util

sealed class ResultState<out T> {
    data object Loading : ResultState<Nothing>()
    data class Success<T>(val data: T): ResultState<T>()
    data class Error(val message: String, val errorCode: Int? = null): ResultState<Nothing>()
}