package com.android.onlinefoodorderingapp.data.repository.auth

import com.android.onlinefoodorderingapp.data.local.dao.UserDao
import com.android.onlinefoodorderingapp.data.local.entity.UserEntity
import com.android.onlinefoodorderingapp.domain.model.User
import com.android.onlinefoodorderingapp.domain.repository.auth.VerifyOtpRepository
import com.android.onlinefoodorderingapp.presentation.util.OtpManager
import javax.inject.Inject

class VerifyOtpRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val otpManager: OtpManager
): VerifyOtpRepository {
    override suspend fun verifyOtp(phone: String, otp: String): Result<User> {
        return try {
            val isValid = otpManager.verify(phone, otp)

            if (!isValid) {
                return Result.failure(Exception("Invalid OTP"))
            }
            // Check existing user (single-user assumption)
            val existingUser = userDao.getUser()

            val userEntity = existingUser ?: UserEntity(
                phone = phone,
                name = "" // or null if you prefer
            )

            userDao.insertUser(userEntity)

            Result.success(
                User(
                    id = 0,
                    phone = userEntity.phone,
                    name = userEntity.name
                )
            )

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}