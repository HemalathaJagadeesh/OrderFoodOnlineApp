package com.android.onlinefoodorderingapp.domain.usecase.cart

import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import com.android.onlinefoodorderingapp.domain.repository.cart.CartRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class AddToCartUseCaseTest:
BehaviorSpec({

    lateinit var repo: CartRepository
    lateinit var useCase: AddToCartUseCase

    beforeTest {
        repo = mockk()
        useCase = AddToCartUseCase(repo)
    }


    fun testFoodItem(
        foodId: String = "1",
        name: String = "Pizza",
        price: Double = 199.0,
        image: String = "image",
        description: String = "desc",
        isSpicy: Boolean = false,
        isVeg: Boolean = true,
        category: String = "Fast Food"
    ) = FoodItem(
        foodId,
        name,
        price,
        image,
        description,
        isSpicy,
        isVeg,
        category
    )


    given("AddToCartUseCase") {

        //  Successful add to cart
        `when`("repository addToCart succeeds") {

            val food = testFoodItem()
            val quantity = 2

            then("it should call repository with correct parameters") {
                runTest {

                    coEvery {
                        repo.addToCart(food, quantity)
                    } returns Unit

                    useCase(food, quantity)

                    coVerify(exactly = 1) {
                        repo.addToCart(food, quantity)
                    }
                }
            }
        }

        // Parameter validation (interaction check)
        `when`("valid inputs are passed") {

            val food = testFoodItem()
            val quantity = 3

            then("it should pass correct parameters to repository") {
                runTest {

                    coEvery {
                        repo.addToCart(any(), any())
                    } returns Unit

                    useCase(food, quantity)

                    coVerify(exactly = 1) {
                        repo.addToCart(food, quantity)
                    }
                }
            }
        }

        //  Multiple invocations
        `when`("usecase is called multiple times") {

            val food = testFoodItem()
            val quantity = 1

            then("repository should be called each time") {
                runTest {

                    coEvery {
                        repo.addToCart(food, quantity)
                    } returns Unit

                    useCase(food, quantity)
                    useCase(food, quantity)

                    coVerify(exactly = 2) {
                        repo.addToCart(food, quantity)
                    }
                }
            }
        }

        // Exception case
        `when`("repository throws exception") {

            val food = testFoodItem()
            val quantity = 1
            val exception = RuntimeException("Add to cart failed")

            then("it should propagate exception") {
                runTest {

                    coEvery {
                        repo.addToCart(food, quantity)
                    } throws exception

                    try {
                        useCase(food, quantity)
                    } catch (e: Exception) {
                        e shouldBe exception
                    }

                    coVerify(exactly = 1) {
                        repo.addToCart(food, quantity)
                    }
                }
            }
        }

        // Edge case: zero quantity
        `when`("quantity is zero") {

            val food = testFoodItem()
            val quantity = 0

            then("it should still delegate to repository as-is") {
                runTest {

                    coEvery {
                        repo.addToCart(food, quantity)
                    } returns Unit

                    useCase(food, quantity)

                    coVerify(exactly = 1) {
                        repo.addToCart(food, quantity)
                    }
                }
            }
        }
    }
})
