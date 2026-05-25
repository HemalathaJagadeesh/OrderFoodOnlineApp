package com.android.onlinefoodorderingapp.domain.usecase.cart


import com.android.onlinefoodorderingapp.domain.repository.cart.CartRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class IncreaseQuantityUseCaseTest : BehaviorSpec({

    lateinit var repo: CartRepository
    lateinit var useCase: IncreaseQuantityUseCase

    beforeTest {
        repo = mockk()
        useCase = IncreaseQuantityUseCase(repo)
    }

    given("IncreaseQuantityUseCase") {

        // Successful increase
        `when`("repository increases quantity successfully") {

            val itemId = "item_123"

            then("it should call repository with correct itemId") {
                runTest {

                    coEvery {
                        repo.increaseQuantity(itemId)
                    } returns Unit

                    useCase(itemId)

                    coVerify(exactly = 1) {
                        repo.increaseQuantity(itemId)
                    }
                }
            }
        }

        //Parameter verification
        `when`("valid itemId is passed") {

            val itemId = "item_456"

            then("it should pass correct itemId to repository") {
                runTest {

                    coEvery {
                        repo.increaseQuantity(any())
                    } returns Unit

                    useCase(itemId)

                    coVerify(exactly = 1) {
                        repo.increaseQuantity(itemId)
                    }
                }
            }
        }

        //  Multiple invocations
        `when`("usecase is called multiple times") {

            val itemId = "item_multi"

            then("repository should be called each time") {
                runTest {

                    coEvery {
                        repo.increaseQuantity(itemId)
                    } returns Unit

                    useCase(itemId)
                    useCase(itemId)

                    coVerify(exactly = 2) {
                        repo.increaseQuantity(itemId)
                    }
                }
            }
        }

        // Exception case
        `when`("repository throws exception") {

            val itemId = "item_999"
            val exception = RuntimeException("Increase failed")

            then("it should propagate exception") {
                runTest {

                    coEvery {
                        repo.increaseQuantity(itemId)
                    } throws exception

                    try {
                        useCase(itemId)
                    } catch (e: Exception) {
                        e shouldBe exception
                    }

                    coVerify(exactly = 1) {
                        repo.increaseQuantity(itemId)
                    }
                }
            }
        }

        // Edge case: empty itemId
        `when`("empty itemId is passed") {

            val itemId = ""

            then("it should delegate to repository as-is") {
                runTest {

                    coEvery {
                        repo.increaseQuantity(itemId)
                    } returns Unit

                    useCase(itemId)

                    coVerify(exactly = 1) {
                        repo.increaseQuantity(itemId)
                    }
                }
            }
        }
    }
})
