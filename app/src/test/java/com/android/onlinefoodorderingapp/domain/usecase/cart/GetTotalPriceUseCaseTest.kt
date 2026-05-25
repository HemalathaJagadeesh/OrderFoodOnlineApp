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

class GetTotalPriceUseCaseTest : BehaviorSpec({

    lateinit var repo: CartRepository
    lateinit var useCase: GetTotalPriceUseCase

    beforeTest {
        repo = mockk()
        useCase = GetTotalPriceUseCase(repo)
    }

    given("GetTotalPriceUseCase") {

        //  Single emission
        `when`("repository emits single total price") {

            then("it should return that value") {
                runTest {

                    val flow = flow {
                        emit(250.0)
                    }

                    every { repo.getTotalPrice() } returns flow

                    val result = useCase().toList()

                    result shouldBe listOf(250.0)

                    verify(exactly = 1) {
                        repo.getTotalPrice()
                    }
                }
            }
        }

        //  Multiple emissions
        `when`("repository emits multiple total prices") {

            then("it should emit all values") {
                runTest {

                    val flow = flow {
                        emit(100.0)
                        emit(200.0)
                        emit(350.0)
                    }

                    every { repo.getTotalPrice() } returns flow

                    val result = useCase().toList()

                    result shouldBe listOf(100.0, 200.0, 350.0)

                    verify(exactly = 1) {
                        repo.getTotalPrice()
                    }
                }
            }
        }

        //  Empty flow
        `when`("repository emits nothing") {

            then("it should return empty list") {
                runTest {

                    val flow = flow<Double> { }

                    every { repo.getTotalPrice() } returns flow

                    val result = useCase().toList()

                    result shouldBe emptyList()

                    verify {
                        repo.getTotalPrice()
                    }
                }
            }
        }

        // Exception case
        `when`("repository flow throws exception") {

            val exception = RuntimeException("Flow error")

            then("it should propagate exception") {
                runTest {

                    val flow = flow<Double> {
                        throw exception
                    }

                    every { repo.getTotalPrice() } returns flow

                    try {
                        useCase().toList()
                    } catch (e: Exception) {
                        e shouldBe exception
                    }

                    verify {
                        repo.getTotalPrice()
                    }
                }
            }
        }

        // Delegation verification
        `when`("usecase is invoked") {

            then("it should call repository getTotalPrice") {
                runTest {

                    val flow = flow { emit(500.0) }

                    every { repo.getTotalPrice() } returns flow

                    useCase().toList()

                    verify(exactly = 1) {
                        repo.getTotalPrice()
                    }
                }
            }
        }
    }
})
