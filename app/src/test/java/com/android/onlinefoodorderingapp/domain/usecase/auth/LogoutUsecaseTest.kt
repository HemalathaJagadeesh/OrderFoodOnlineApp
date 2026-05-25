package com.android.onlinefoodorderingapp.domain.usecase.auth

import com.android.onlinefoodorderingapp.domain.repository.auth.LogoutRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class LogoutUsecaseTest
    : BehaviorSpec({

    lateinit var repo: LogoutRepository
    lateinit var useCase: LogoutUsecase

    beforeTest {
        repo = mockk()
        useCase = LogoutUsecase(repo)
    }

    given("LogoutUsecase") {

        // Successful logout
        `when`("repository logout succeeds") {

            then("it should call repository logout") {
                runTest {

                    coEvery { repo.logout() } returns Unit

                    useCase()

                    coVerify(exactly = 1) {
                        repo.logout()
                    }
                }
            }
        }

        //  Repository throws exception
        `when`("repository throws exception") {

            val exception = RuntimeException("Logout failed")

            then("it should propagate exception") {
                runTest {

                    coEvery { repo.logout() } throws exception

                    try {
                        useCase()
                    } catch (e: Exception) {
                        e shouldBe exception
                    }

                    coVerify(exactly = 1) {
                        repo.logout()
                    }
                }
            }
        }

        //  Multiple invocations
        `when`("usecase is called multiple times") {

            then("repository logout should be called each time") {
                runTest {

                    coEvery { repo.logout() } returns Unit

                    useCase()
                    useCase()

                    coVerify(exactly = 2) {
                        repo.logout()
                    }
                }
            }
        }
    }
})
