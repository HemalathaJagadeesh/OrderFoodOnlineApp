package com.android.onlinefoodorderingapp.domain.usecase.auth

import com.android.onlinefoodorderingapp.domain.model.User
import com.android.onlinefoodorderingapp.domain.repository.auth.LoggedInUserRepository
import javax.inject.Inject

class GetLoggedInUserUsecase @Inject constructor(private val loggedInUserRepository: LoggedInUserRepository) {
    suspend operator fun invoke(phone: String): User? {
        return loggedInUserRepository.getLoggedInUser(phone)
    }

}