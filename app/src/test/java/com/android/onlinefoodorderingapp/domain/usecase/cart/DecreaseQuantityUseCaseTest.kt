package com.android.onlinefoodorderingapp.domain.usecase.cart

import com.android.onlinefoodorderingapp.domain.repository.cart.CartRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class DecreaseQuantityUseCaseTest
    : BehaviorSpec({

    lateinit var repo: CartRepository
    lateinit var useCase: DecreaseQuantityUseCase

    beforeTest {
        repo = mockk()
        useCase = DecreaseQuantityUseCase(repo)
    }

    given("DecreaseQuantityUseCase") {

        //  Successful decrease
        `when`("repository decreases quantity successfully") {

            val itemId = "item_123"

            then("it should call repository with correct itemId") {
                runTest {

                    coEvery {
                        repo.decreaseQuantity(itemId)
                    } returns Unit

                    useCase(itemId)

                    coVerify(exactly = 1) {
                        repo.decreaseQuantity(itemId)
                    }
                }
            }
        }

        // Parameter verification
        `when`("valid itemId is passed") {

            val itemId = "item_456"

            then("it should pass correct itemId to repository") {
                runTest {

                    coEvery {
                        repo.decreaseQuantity(any())
                    } returns Unit

                    useCase(itemId)

                    coVerify(exactly = 1) {
                        repo.decreaseQuantity(itemId)
                    }
                }
            }
        }

        // Multiple invocations
        `when`("usecase is called multiple times") {

            val itemId = "item_789"

            then("repository should be called each time") {
                runTest {

                    coEvery {
                        repo.decreaseQuantity(itemId)
                    } returns Unit

                    useCase(itemId)
                    useCase(itemId)

                    coVerify(exactly = 2) {
                        repo.decreaseQuantity(itemId)
                    }
                }
            }
        }

        //  Exception case
        `when`("repository throws exception") {

            val itemId = "item_999"
            val exception = RuntimeException("Decrease failed")

            then("it should propagate exception") {
                runTest {

                    coEvery {
                        repo.decreaseQuantity(itemId)
                    } throws exception

                    try {
                        useCase(itemId)
                    } catch (e: Exception) {
                        e shouldBe exception
                    }

                    coVerify(exactly = 1) {
                        repo.decreaseQuantity(itemId)
                    }
                }
            }
        }

        //  Edge case: empty itemId
        `when`("empty itemId is passed") {

            val itemId = ""

            then("it should still delegate to repository as-is") {
                runTest {

                    coEvery {
                        repo.decreaseQuantity(itemId)
                    } returns Unit

                    useCase(itemId)

                    coVerify(exactly = 1) {
                        repo.decreaseQuantity(itemId)
                    }
                }
            }
        }
    }
})
