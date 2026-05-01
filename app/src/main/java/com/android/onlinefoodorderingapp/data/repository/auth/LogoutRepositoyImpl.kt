package com.android.onlinefoodorderingapp.data.repository.auth

import com.android.onlinefoodorderingapp.data.local.dao.UserDao
import com.android.onlinefoodorderingapp.domain.repository.auth.LogoutRepository

class LogoutRepositoyImpl(private val dao: UserDao):LogoutRepository{
    override suspend fun logout() {
      // sessionManager.clearSession()
        dao.deleteUser()
    }
}