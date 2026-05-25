package com.android.onlinefoodorderingapp.domain.usecase.cart

import com.android.onlinefoodorderingapp.domain.repository.cart.CartRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest

class GetCartCountUseCaseTest : BehaviorSpec({

    lateinit var repo: CartRepository
    lateinit var useCase: GetCartCountUseCase

    beforeTest {
        repo = mockk()
        useCase = GetCartCountUseCase(repo)
    }

    given("GetCartCountUseCase") {

        // Single emission
        `when`("repository emits a single cart count") {

            then("it should return that value") {
                runTest {

                    val flow = flow {
                        emit(3)
                    }

                    every { repo.getTotalItems() } returns flow

                    val result = useCase().toList()

                    result shouldBe listOf(3)

                    verify(exactly = 1) {
                        repo.getTotalItems()
                    }
                }
            }
        }

        //  Multiple emissions
        `when`("repository emits multiple values") {

            then("it should emit all values") {
                runTest {

                    val flow = flow {
                        emit(1)
                        emit(2)
                        emit(5)
                    }

                    every { repo.getTotalItems() } returns flow

                    val result = useCase().toList()

                    result shouldBe listOf(1, 2, 5)

                    verify(exactly = 1) {
                        repo.getTotalItems()
                    }
                }
            }
        }

        //  Empty flow
        `when`("repository emits nothing") {

            then("it should return empty list") {
                runTest {

                    val flow = flow<Int> { }

                    every { repo.getTotalItems() } returns flow

                    val result = useCase().toList()

                    result shouldBe emptyList()

                    verify {
                        repo.getTotalItems()
                    }
                }
            }
        }

        //  Exception in flow
        `when`("repository flow throws exception") {

            val exception = RuntimeException("Flow error")

            then("it should propagate exception") {
                runTest {

                    val flow = flow<Int> {
                        throw exception
                    }

                    every { repo.getTotalItems() } returns flow

                    try {
                        useCase().toList()
                    } catch (e: Exception) {
                        e shouldBe exception
                    }

                    verify {
                        repo.getTotalItems()
                    }
                }
            }
        }

        //  Verify delegation
        `when`("usecase is invoked") {

            then("it should delegate to repository") {
                runTest {

                    val flow = flow { emit(10) }

                    every { repo.getTotalItems() } returns flow

                    useCase().toList()

                    verify(exactly = 1) {
                        repo.getTotalItems()
                    }
                }
            }
        }
    }
})
