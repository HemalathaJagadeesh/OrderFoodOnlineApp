package com.android.onlinefoodorderingapp.data.repository.auth

import com.android.onlinefoodorderingapp.data.local.dao.UserDao
import com.android.onlinefoodorderingapp.domain.repository.auth.LogoutRepository
import com.android.onlinefoodorderingapp.presentation.util.SessionManager

class LogoutRepositoyImpl(private val dao: UserDao):LogoutRepository{
    override suspend fun logout() {
      // sessionManager.clearSession()
        dao.deleteUser()
    }
}