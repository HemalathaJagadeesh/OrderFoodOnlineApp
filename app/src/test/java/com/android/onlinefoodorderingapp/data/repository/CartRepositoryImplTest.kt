package com.android.onlinefoodorderingapp.data.repository


import com.android.onlinefoodorderingapp.data.local.dao.CartDao
import com.android.onlinefoodorderingapp.data.mapper.toCartEntity
import com.android.onlinefoodorderingapp.data.repository.cart.CartRepositoryImpl
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest

class CartRepositoryImplTest : BehaviorSpec({

    lateinit var dao: CartDao
    lateinit var repository: CartRepositoryImpl

    fun testFood(
        id: String = "1",
        name: String = "Pizza",
        price: Double = 100.0
    ) = FoodItem(
        foodId = id,
        name = name,
        price = price,
        image = "",
        description = "",
        isSpicy = false,
        isVeg = true,
        category = "Fast Food"
    )

    fun testEntity(id: String = "1", quantity: Int = 1) =
        testFood(id).toCartEntity().copy(quantity = quantity)

    beforeTest {
        dao = mockk()
        repository = CartRepositoryImpl(dao)
    }

    given("CartRepositoryImpl") {

        // getCartItems
        `when`("getCartItems is called") {
            then("should map entities to domain") {
                runTest {

                    val entities = listOf(testEntity("1", 2))
                    every { dao.getCartItems() } returns flowOf(entities)

                    val result = repository.getCartItems().toList()

                    result.flatten().first().quantity shouldBe 2

                    verify(exactly = 1) { dao.getCartItems() }
                }
            }
        }

        // getTotalItems
        `when`("getTotalItems is called") {
            then("should return sum of quantities") {
                runTest {

                    val entities = listOf(
                        testEntity("1", 2),
                        testEntity("2", 3)
                    )

                    every { dao.getCartItems() } returns flowOf(entities)

                    val result = repository.getTotalItems().toList()

                    result shouldBe listOf(5)
                }
            }
        }

        // getTotalPrice
        `when`("getTotalPrice is called") {
            then("should map null to 0.0") {
                runTest {

                    every { dao.getTotalPrice() } returns flowOf(null)

                    val result = repository.getTotalPrice().toList()

                    result shouldBe listOf(0.0)
                }
            }
        }

        // addToCart (existing item)
        `when`("addToCart when item already exists") {
            then("should update quantity") {
                runTest {

                    val food = testFood("1")
                    val existing = testEntity("1", 2)

                    coEvery { dao.getItemById("1") } returns existing
                    coEvery { dao.updateQuantity("1", 5) } returns 1

                    repository.addToCart(food, 3)

                    coVerify(exactly = 1) {
                        dao.updateQuantity("1", 5)
                    }
                }
            }
        }

        // addToCart (new item)
        `when`("addToCart when item does not exist") {
            then("should insert new item") {
                runTest {

                    val food = testFood("1")

                    coEvery { dao.getItemById("1") } returns null
                    coEvery { dao.insertCartItem(any()) } just Runs

                    repository.addToCart(food, 2)

                    coVerify(exactly = 1) {
                        dao.insertCartItem(match { it.quantity == 2 })
                    }
                }
            }
        }

        //increaseQuantity
        `when`("increaseQuantity is called") {
            then("should call dao") {
                runTest {

                    coEvery { dao.increaseQuantity("1") } returns 1

                    repository.increaseQuantity("1")

                    coVerify(exactly = 1) {
                        dao.increaseQuantity("1")
                    }
                }
            }
        }

        // decreaseQuantity (>1)
        `when`("decreaseQuantity when quantity > 1") {
            then("should decrease quantity") {
                runTest {

                    val entity = testEntity("1", 2)

                    coEvery { dao.getItemById("1") } returns entity
                    coEvery { dao.decreaseQuantity("1") } returns 1

                    repository.decreaseQuantity("1")

                    coVerify(exactly = 1) {
                        dao.decreaseQuantity("1")
                    }
                }
            }
        }

        //  decreaseQuantity (=1)
        `when`("decreaseQuantity when quantity is 1") {
            then("should delete item") {
                runTest {

                    val entity = testEntity("1", 1)

                    coEvery { dao.getItemById("1") } returns entity
                    coEvery { dao.deleteById("1") } returns 0

                    repository.decreaseQuantity("1")

                    coVerify(exactly = 1) {
                        dao.deleteById("1")
                    }
                }
            }
        }

        //removeItem
        `when`("removeItem is called") {
            then("should delete item") {
                runTest {

                    coEvery { dao.deleteById("1") } returns 1

                    repository.removeItem("1")

                    coVerify(exactly = 1) {
                        dao.deleteById("1")
                    }
                }
            }
        }

        // clearCart
        `when`("clearCart is called") {
            then("should clear all items") {
                runTest {

                    coEvery { dao.clearCart() } just Runs

                    repository.clearCart()

                    coVerify(exactly = 1) {
                        dao.clearCart()
                    }
                }
            }
        }

        //getCartItemById
        `when`("getCartItemById returns item") {
            then("should return mapped item") {
                runTest {

                    val entity = testEntity("1", 2)

                    coEvery { dao.getItemById("1") } returns entity

                    val result = repository.getCartItemById("1")

                    result?.quantity shouldBe 2
                }
            }
        }

        // getCartItemById null
        `when`("getCartItemById returns null") {
            then("should return null") {
                runTest {

                    coEvery { dao.getItemById("1") } returns null

                    val result = repository.getCartItemById("1")

                    result shouldBe null
                }
            }
        }
    }
})
