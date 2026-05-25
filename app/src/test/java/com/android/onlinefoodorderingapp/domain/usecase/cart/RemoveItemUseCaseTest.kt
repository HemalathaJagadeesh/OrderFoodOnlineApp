package com.android.onlinefoodorderingapp.domain.usecase.cart


import com.android.onlinefoodorderingapp.domain.repository.cart.CartRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class RemoveItemUseCaseTest : BehaviorSpec({

    lateinit var repo: CartRepository
    lateinit var useCase: RemoveItemUseCase

    beforeTest {
        repo = mockk()
        useCase = RemoveItemUseCase(repo)
    }

    given("RemoveItemUseCase") {

        // Successful remove
        `when`("repository removes item successfully") {

            val foodId = "item_123"

            then("it should call repository with correct foodId") {
                runTest {

                    coEvery {
                        repo.removeItem(foodId)
                    } returns Unit

                    useCase(foodId)

                    coVerify(exactly = 1) {
                        repo.removeItem(foodId)
                    }
                }
            }
        }

        //  Parameter verification
        `when`("valid foodId is passed") {

            val foodId = "item_456"

            then("it should pass correct foodId to repository") {
                runTest {

                    coEvery {
                        repo.removeItem(any())
                    } returns Unit

                    useCase(foodId)

                    coVerify(exactly = 1) {
                        repo.removeItem(foodId)
                    }
                }
            }
        }

        // Multiple invocations
        `when`("usecase is called multiple times") {

            val foodId = "item_multi"

            then("repository should be called each time") {
                runTest {

                    coEvery {
                        repo.removeItem(foodId)
                    } returns Unit

                    useCase(foodId)
                    useCase(foodId)

                    coVerify(exactly = 2) {
                        repo.removeItem(foodId)
                    }
                }
            }
        }

        // Exception case
        `when`("repository throws exception") {

            val foodId = "item_999"
            val exception = RuntimeException("Remove failed")

            then("it should propagate exception") {
                runTest {

                    coEvery {
                        repo.removeItem(foodId)
                    } throws exception

                    try {
                        useCase(foodId)
                    } catch (e: Exception) {
                        e shouldBe exception
                    }

                    coVerify(exactly = 1) {
                        repo.removeItem(foodId)
                    }
                }
            }
        }

        // Edge case: empty id
        `when`("empty foodId is passed") {

            val foodId = ""

            then("it should delegate to repository as-is") {
                runTest {

                    coEvery {
                        repo.removeItem(foodId)
                    } returns Unit

                    useCase(foodId)

                    coVerify(exactly = 1) {
                        repo.removeItem(foodId)
                    }
                }
            }
        }
    }
})
