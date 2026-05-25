package com.android.onlinefoodorderingapp.domain.usecase.auth

import com.android.onlinefoodorderingapp.domain.model.auth.AuthError
import com.android.onlinefoodorderingapp.domain.model.auth.AuthResult
import com.android.onlinefoodorderingapp.domain.repository.auth.AuthRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.mockk
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.test.runTest

class VerifyFirebaseOtpUsecaseTest : BehaviorSpec({

    lateinit var repo: AuthRepository
    lateinit var useCase: VerifyFirebaseOtpUsecase

    beforeTest {
        repo = mockk()
        useCase = VerifyFirebaseOtpUsecase(repo)
    }

    given("VerifyFirebaseOtpUsecase") {

        `when`("repository returns success") {

            val verificationId = "verif_123"
            val code = "123456"
            val userId = "user_abc"

            then("it should return success") {
                runTest {

                    coEvery {
                        repo.verifyOtp(verificationId, code)
                    } returns AuthResult.Success(userId)

                    val result = useCase(verificationId, code)

                    result shouldBe AuthResult.Success(userId)

                    coVerify(exactly = 1) {
                        repo.verifyOtp(verificationId, code)
                    }
                }
            }
        }

        //  Failure case
        `when`("repository returns failure") {

            val verificationId = "verif_123"
            val code = "123456"
            val failure = AuthResult.Failure(AuthError.InvalidOtp)

            then("it should return failure") {
                runTest {

                    coEvery {
                        repo.verifyOtp(verificationId, code)
                    } returns failure

                    val result = useCase(verificationId, code)

                    result shouldBe failure

                    coVerify(exactly = 1) {
                        repo.verifyOtp(verificationId, code)
                    }
                }
            }
        }

        // Exception case
        `when`("repository throws exception") {

            val verificationId = "verif_123"
            val code = "123456"
            val exception = RuntimeException("Verification failed")

            then("it should propagate exception") {
                runTest {

                    coEvery {
                        repo.verifyOtp(verificationId, code)
                    } throws exception

                    try {
                        useCase(verificationId, code)
                    } catch (e: Exception) {
                        e shouldBe exception
                    }

                    coVerify(exactly = 1) {
                        repo.verifyOtp(verificationId, code)
                    }
                }
            }
        }

        //  Parameter verification
        `when`("valid input is passed") {

            val verificationId = "verif_999"
            val code = "654321"

            then("correct parameters should be passed") {
                runTest {

                    coEvery {
                        repo.verifyOtp(any(), any())
                    } returns AuthResult.Success("dummy_user")

                    useCase(verificationId, code)

                    coVerify(exactly = 1) {
                        repo.verifyOtp(
                            verificationId = verificationId,
                            code = code
                        )
                    }
                }
            }
        }

        //  Multiple calls
        `when`("usecase is called multiple times") {

            val verificationId = "verif_123"
            val code = "123456"

            then("repository should be invoked each time") {
                runTest {

                    coEvery {
                        repo.verifyOtp(verificationId, code)
                    } returns AuthResult.Success("user_multi")

                    useCase(verificationId, code)
                    useCase(verificationId, code)

                    coVerify(exactly = 2) {
                        repo.verifyOtp(verificationId, code)
                    }
                }
            }
        }

        //  Edge case
        `when`("empty verificationId or code is passed") {

            val verificationId = ""
            val code = ""

            then("it should delegate to repository as-is") {
                runTest {

                    coEvery {
                        repo.verifyOtp(verificationId, code)
                    } returns AuthResult.Failure(AuthError.InvalidOtp)

                    val result = useCase(verificationId, code)

                    result shouldBe AuthResult.Failure(AuthError.InvalidOtp)

                    coVerify(exactly = 1) {
                        repo.verifyOtp(verificationId, code)
                    }
                }
            }
        }
    }
})
