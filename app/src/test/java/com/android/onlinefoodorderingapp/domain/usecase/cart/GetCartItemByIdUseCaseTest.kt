package com.android.onlinefoodorderingapp.domain.usecase.cart

import com.android.onlinefoodorderingapp.domain.model.CartItem
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import com.android.onlinefoodorderingapp.domain.repository.cart.CartRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class GetCartItemByIdUseCaseTest : BehaviorSpec({

    lateinit var repo: CartRepository
    lateinit var useCase: GetCartItemByIdUseCase

    beforeTest {
        repo = mockk()
        useCase = GetCartItemByIdUseCase(repo)
    }


    fun testFoodItem(
        id: String = "food_1",
        name: String = "Pizza"
    ) = FoodItem(
        foodId = id,
        name = name,
        price = 199.0,
        image = "img",
        description = "desc",
        isSpicy = false,
        isVeg = true,
        category = "Fast Food"
    )

    //  Common CartItem
    fun testCartItem(
        itemId: String = "food_1",
        quantity: Int = 2
    ) = CartItem(
        foodItem = testFoodItem(id = itemId),
        quantity = quantity
    )


    given("GetCartItemByIdUseCase") {

        //  Helper functions inside test class
        fun testFoodItem(id: String) = FoodItem(
            foodId = id,
            name = "Pizza",
            price = 199.0,
            image = "",
            description = "",
            isSpicy = false,
            isVeg = true,
            category = "Fast Food"
        )

        fun testCartItem(id: String, quantity: Int = 2) = CartItem(
            foodItem = testFoodItem(id),
            quantity = quantity
        )

        //  Item exists
        `when`("repository returns cart item") {

            val itemId = "item_123"

            then("it should return the item") {
                runTest {

                    val item = testCartItem(itemId)

                    coEvery {
                        repo.getCartItemById(itemId)
                    } returns item

                    val result = useCase(itemId)

                    result shouldBe item

                    coVerify(exactly = 1) {
                        repo.getCartItemById(itemId)
                    }
                }
            }
        }

        //  Item not found
        `when`("repository returns null") {

            val itemId = "item_456"

            then("it should return null") {
                runTest {

                    coEvery {
                        repo.getCartItemById(itemId)
                    } returns null

                    val result = useCase(itemId)

                    result shouldBe null

                    coVerify(exactly = 1) {
                        repo.getCartItemById(itemId)
                    }
                }
            }
        }

        //  Exception case
        `when`("repository throws exception") {

            val itemId = "item_789"
            val exception = RuntimeException("DB error")

            then("it should propagate exception") {
                runTest {

                    coEvery {
                        repo.getCartItemById(itemId)
                    } throws exception

                    try {
                        useCase(itemId)
                    } catch (e: Exception) {
                        e shouldBe exception
                    }

                    coVerify(exactly = 1) {
                        repo.getCartItemById(itemId)
                    }
                }
            }
        }

        //  Parameter verification
        `when`("valid itemId is passed") {

            val itemId = "item_999"

            then("it should call repository with correct itemId") {
                runTest {

                    coEvery {
                        repo.getCartItemById(any())
                    } returns null

                    useCase(itemId)

                    coVerify(exactly = 1) {
                        repo.getCartItemById(itemId)
                    }
                }
            }
        }

        // Multiple calls
        `when`("usecase is called multiple times") {

            val itemId = "item_multi"

            then("repository should be called each time") {
                runTest {

                    val item = testCartItem(itemId)

                    coEvery {
                        repo.getCartItemById(itemId)
                    } returns item

                    useCase(itemId)
                    useCase(itemId)

                    coVerify(exactly = 2) {
                        repo.getCartItemById(itemId)
                    }
                }
            }
        }

        // Edge case
        `when`("empty itemId is passed") {

            val itemId = ""

            then("it should delegate to repository as-is") {
                runTest {

                    coEvery {
                        repo.getCartItemById(itemId)
                    } returns null

                    val result = useCase(itemId)

                    result shouldBe null

                    coVerify(exactly = 1) {
                        repo.getCartItemById(itemId)
                    }
                }
            }
        }
    }
})
