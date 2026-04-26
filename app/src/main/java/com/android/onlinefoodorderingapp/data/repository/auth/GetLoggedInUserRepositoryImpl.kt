package com.android.onlinefoodorderingapp.data.repository.auth

import com.android.onlinefoodorderingapp.data.local.dao.UserDao
import com.android.onlinefoodorderingapp.domain.model.User
import com.android.onlinefoodorderingapp.domain.repository.auth.LoggedInUserRepository

class GetLoggedInUserRepositoryImpl(
    private val userDao: UserDao
): LoggedInUserRepository {

    override suspend fun getLoggedInUser(phone: String): User? {
       return userDao.getUser()?.let {
           User(
               id = 0,
               name = it.name,
               phone = it.phone,
           )
       } }

    }
