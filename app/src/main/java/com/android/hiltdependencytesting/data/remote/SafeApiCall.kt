package com.android.hiltdependencytesting.data.remote

import com.android.hiltdependencytesting.domain.util.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

suspend fun <T> SafeApiCallFlow(apiCall: suspend() -> T): Flow<ResultState<T>> = flow{
    emit(ResultState.Loading)
     try{
        val response = apiCall()
        emit(ResultState.Success(response))
    }catch (e: HttpException){
        emit(ResultState.Error(e.message ?: "An unexpected error occurred", e.code()))
    }catch (e: IOException){
        emit(ResultState.Error("Network error"))
    }catch (e: Exception){
       emit(ResultState.Error(e.message ?: "Unknown error"))
    }
}