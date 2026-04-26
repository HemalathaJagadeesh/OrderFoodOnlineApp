package com.android.onlinefoodorderingapp.domain.usecase.auth

import com.android.onlinefoodorderingapp.domain.repository.auth.LogoutRepository
import javax.inject.Inject

class LogoutUsecase @Inject constructor(private val logoutRepository: LogoutRepository) {
    suspend operator fun invoke() {
        logoutRepository.logout()
    }
}