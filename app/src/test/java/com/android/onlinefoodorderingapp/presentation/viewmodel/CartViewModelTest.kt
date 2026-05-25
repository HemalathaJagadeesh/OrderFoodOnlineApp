package com.android.onlinefoodorderingapp.presentation.viewmodel


import app.cash.turbine.test
import com.android.onlinefoodorderingapp.domain.model.CartItem
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import com.android.onlinefoodorderingapp.domain.usecase.cart.AddToCartUseCase
import com.android.onlinefoodorderingapp.domain.usecase.cart.DecreaseQuantityUseCase
import com.android.onlinefoodorderingapp.domain.usecase.cart.GetCartCountUseCase
import com.android.onlinefoodorderingapp.domain.usecase.cart.GetCartItemByIdUseCase
import com.android.onlinefoodorderingapp.domain.usecase.cart.GetCartItemsUseCase
import com.android.onlinefoodorderingapp.domain.usecase.cart.GetTotalPriceUseCase
import com.android.onlinefoodorderingapp.domain.usecase.cart.IncreaseQuantityUseCase
import com.android.onlinefoodorderingapp.domain.usecase.cart.RemoveItemUseCase
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest : BehaviorSpec({

    val dispatcher = StandardTestDispatcher()

    lateinit var viewModel: CartViewModel

    val addToCartUseCase = mockk<AddToCartUseCase>()
    val getCartItemsUseCase = mockk<GetCartItemsUseCase>()
    val removeItemUseCase = mockk<RemoveItemUseCase>()
    val getTotalPriceUseCase = mockk<GetTotalPriceUseCase>()
    val getCartCountUseCase = mockk<GetCartCountUseCase>()
    val increaseQuantityUseCase = mockk<IncreaseQuantityUseCase>()
    val decreaseQuantityUseCase = mockk<DecreaseQuantityUseCase>()
    val getCartItemByIdUseCase = mockk<GetCartItemByIdUseCase>()

    beforeTest {
        Dispatchers.setMain(dispatcher)

        // default flows
        every { getCartItemsUseCase() } returns flowOf(emptyList())
        every { getTotalPriceUseCase() } returns flowOf(0.0)
        every { getCartCountUseCase() } returns flowOf(0)

        // suspend mocks
        coEvery { addToCartUseCase(any(), any()) } just Runs
        coEvery { removeItemUseCase(any()) } just Runs
        coEvery { increaseQuantityUseCase(any()) } just Runs
        coEvery { decreaseQuantityUseCase(any()) } just Runs
        coEvery { getCartItemByIdUseCase(any()) } returns null
    }

    afterTest {
        Dispatchers.resetMain()
        clearAllMocks()
    }


    given("cartCount flow") {
        `when`("use case emits value") {
            then("should emit correct count") {

                runTest {
                    every { getCartCountUseCase() } returns flowOf(4)

                    viewModel = CartViewModel(
                        addToCartUseCase,
                        getCartItemsUseCase,
                        removeItemUseCase,
                        getTotalPriceUseCase,
                        getCartCountUseCase,
                        increaseQuantityUseCase,
                        decreaseQuantityUseCase,
                        getCartItemByIdUseCase
                    )

                    viewModel.cartCount.test {
                        skipItems(1) // initial = 0
                        awaitItem() shouldBe 4
                    }
                }
            }
        }
    }

    given("totalPrice flow") {
        `when`("use case emits value") {
            then("should emit correct price") {

                runTest {
                    every { getTotalPriceUseCase() } returns flowOf(200.0)

                    viewModel = CartViewModel(
                        addToCartUseCase,
                        getCartItemsUseCase,
                        removeItemUseCase,
                        getTotalPriceUseCase,
                        getCartCountUseCase,
                        increaseQuantityUseCase,
                        decreaseQuantityUseCase,
                        getCartItemByIdUseCase
                    )

                    viewModel.totalPrice.test {
                        skipItems(1)
                        awaitItem() shouldBe 200.0
                    }
                }
            }
        }
    }


    given("addToCart") {

        `when`("called") {

            then("should call addToCartUseCase") {

                runTest {
                    val food = mockk<FoodItem>()

                    coEvery { addToCartUseCase(any(), any()) } just Runs

                    viewModel = CartViewModel(
                        addToCartUseCase,
                        getCartItemsUseCase,
                        removeItemUseCase,
                        getTotalPriceUseCase,
                        getCartCountUseCase,
                        increaseQuantityUseCase,
                        decreaseQuantityUseCase,
                        getCartItemByIdUseCase
                    )

                    viewModel.addToCart(food, 2)

                    // ✅ THIS is the missing piece
                    runCurrent()

                    coVerify(exactly = 1) {
                        addToCartUseCase(any(), eq(2))
                    }
                }
            }
        }
    }

    given("removeItem") {

        `when`("called") {

            then("should call removeItemUseCase") {

                runTest {
                    val id = "1"

                    coEvery { removeItemUseCase(any()) } just Runs

                    viewModel = CartViewModel(
                        addToCartUseCase,
                        getCartItemsUseCase,
                        removeItemUseCase,
                        getTotalPriceUseCase,
                        getCartCountUseCase,
                        increaseQuantityUseCase,
                        decreaseQuantityUseCase,
                        getCartItemByIdUseCase
                    )

                    viewModel.removeItem(id)

                    runCurrent()

                    coVerify(exactly = 1) {
                        removeItemUseCase(eq(id))
                    }
                }
            }
        }
    }

    given("increaseQuantity") {

        `when`("called") {

            then("should call getCartItemById and increaseQuantityUseCase") {

                runTest {
                    val id = "1"
                    val item = mockk<CartItem>()

                    coEvery { getCartItemByIdUseCase(id) } returns item
                    coEvery { increaseQuantityUseCase(any()) } just Runs

                    viewModel = CartViewModel(
                        addToCartUseCase,
                        getCartItemsUseCase,
                        removeItemUseCase,
                        getTotalPriceUseCase,
                        getCartCountUseCase,
                        increaseQuantityUseCase,
                        decreaseQuantityUseCase,
                        getCartItemByIdUseCase
                    )

                    viewModel.increaseQuantity(id)


                    runCurrent()


                    coVerify(exactly = 1) {
                        getCartItemByIdUseCase(eq(id))
                    }

                    coVerify(exactly = 1) {
                        increaseQuantityUseCase(eq(id))
                    }
                }
            }
        }
    }


    given("decreaseQuantity") {

        `when`("called") {

            then("should call decreaseQuantityUseCase") {

                runTest {
                    val id = "1"

                    coEvery { decreaseQuantityUseCase(any()) } just Runs

                    viewModel = CartViewModel(
                        addToCartUseCase,
                        getCartItemsUseCase,
                        removeItemUseCase,
                        getTotalPriceUseCase,
                        getCartCountUseCase,
                        increaseQuantityUseCase,
                        decreaseQuantityUseCase,
                        getCartItemByIdUseCase
                    )

                    viewModel.decreaseQuantity(id)

                    
                    runCurrent()

                    coVerify(exactly = 1) {
                        decreaseQuantityUseCase(eq(id))
                    }
                }
            }
        }
    }
})

