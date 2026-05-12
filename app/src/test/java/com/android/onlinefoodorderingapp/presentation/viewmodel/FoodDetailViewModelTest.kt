package com.android.onlinefoodorderingapp.presentation.viewmodel

import app.cash.turbine.test
import com.android.onlinefoodorderingapp.data.local.DummyData
import com.android.onlinefoodorderingapp.domain.model.OptionItem
import com.android.onlinefoodorderingapp.domain.usecase.cart.GetCartCountUseCase
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.maps.shouldContainKey
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain


@OptIn(ExperimentalCoroutinesApi::class)
class FoodDetailViewModelTest : BehaviorSpec({
    val cartCountUseCase = mockk<GetCartCountUseCase>()

    lateinit var viewModel: FoodDetailViewModel

    val dispatcher = UnconfinedTestDispatcher()


    beforeTest {
        Dispatchers.setMain(dispatcher)
        every { cartCountUseCase.invoke() } returns flowOf(3)
        viewModel = FoodDetailViewModel(cartCountUseCase)
    }


    afterTest {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    // =========================================================
    // ✅ CART COUNT FLOW
    // =========================================================
    Given("cartCount") {

        Then("should emit values from use case") {

            viewModel.cartCount.test {
                awaitItem() shouldBe 3
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    // =========================================================
    // ✅ LOAD FOOD SUCCESS
    // =========================================================
    Given("loadFood success") {

        When("valid foodId is provided") {

            Then("should update state with food and options") {

                viewModel.state.test {

                    viewModel.loadFood("1")

                    val initial = awaitItem()
                    initial.isLoading shouldBe false

                    val latest = expectMostRecentItem()

                    latest.isLoading shouldBe false
                    latest.optionGroups.isNotEmpty() shouldBe true

                    cancelAndIgnoreRemainingEvents()
                }
            }
        }
    }

    // =========================================================
    // ✅ LOAD FOOD - INVALID ID (NEGATIVE)
    // =========================================================
    Given("loadFood with invalid id") {

        When("food is not found") {

            Then("foodItem should be null") {

                viewModel.loadFood("invalid_id")

                viewModel.state.value.foodItem shouldBe null
                viewModel.state.value.isLoading shouldBe false
            }
        }
    }

    // =========================================================
    // ✅ OPTION SELECTION
    // =========================================================
    Given("option selected") {

        val option = OptionItem("1", "Test")

        When("user selects option") {

            Then("selectedOptions should update") {

                viewModel.onOptionSelected("Size", option)

                viewModel.state.value.selectedOptions["Size"] shouldBe option
            }
        }
    }

    // =========================================================
    // ✅ OPTION OVERRIDE (NEGATIVE)
    // =========================================================
    Given("same option group selected again") {

        When("user selects another option in same group") {

            Then("previous option should be replaced") {

                runTest {

                    viewModel.state.test {

                        // ✅ Trigger loading
                        viewModel.loadFood("1")

                        // ✅ Wait for state to update (IMPORTANT)
                        awaitItem() // initial state
                        val loadedState = expectMostRecentItem()

                        // ✅ Get real key from state (no hardcoding)
                        val key = loadedState.optionGroups.first().title

                        // ✅ Use correct type
                        val option1 = OptionItem("1", "A")
                        val option2 = OptionItem("2", "B")

                        // ✅ Perform actions AFTER state is ready
                        viewModel.onOptionSelected(key, option1)
                        viewModel.onOptionSelected(key, option2)

                        // ✅ Read latest state
                        val selectedOptions = viewModel.state.value.selectedOptions

                        // Debug (optional)
                        println("Map = $selectedOptions")

                        // ✅ Assertions
                        selectedOptions.containsKey(key) shouldBe true

                        val selected = selectedOptions[key]

                        selected shouldNotBe null
                        selected shouldBe option2

                        cancelAndIgnoreRemainingEvents()
                    }
                }
            }
        }
    }

    // =========================================================
    // ✅ INCREASE QUANTITY
    // =========================================================
    Given("increase quantity") {

        When("called multiple times") {

            Then("quantity should increase correctly") {

                runTest {

                    viewModel.increaseQty()
                    viewModel.increaseQty()

                    val state = viewModel.state.value

                    println("Quantity = ${state.quantity}") // debug

                    state.quantity shouldBe 3
                }
            }
        }
    }

    // =========================================================
    // ✅ DECREASE QUANTITY
    // =========================================================
    Given("decrease quantity") {

        When("quantity > 1") {

            viewModel.increaseQty() // 2
            viewModel.decreaseQty()

            Then("should decrease") {
                viewModel.state.value.quantity shouldBe 1
            }
        }
    }

    // =========================================================
    // ✅ DECREASE BELOW 1 (NEGATIVE)
    // =========================================================
    Given("decrease quantity below 1") {

        When("quantity is already 1") {

            viewModel.decreaseQty()

            Then("should NOT go below 1") {
                viewModel.state.value.quantity shouldBe 1
            }
        }
    }

    // =========================================================
    // ✅ CALCULATE TOTAL (CURRENT IMPLEMENTATION)
    // =========================================================
    Given("calculateTotal") {

        When("called after updates") {

            viewModel.increaseQty()

            Then("total should be 0 as per current logic") {
                viewModel.state.value.totalPrice shouldBe 0
            }
        }
    }

    // =========================================================
    // ✅ ADD TO CART SUCCESS
    // =========================================================
    Given("addToCart with valid food") {

        When("food is loaded") {

            viewModel.loadFood("1")

            Then("should execute without crash") {
                viewModel.addToCart()
                // No assertion (just verifying no crash)
                true shouldBe true
            }
        }
    }

    // =========================================================
    // ✅ ADD TO CART WITHOUT FOOD (NEGATIVE)
    // =========================================================
    Given("addToCart without food") {

        When("foodItem is null") {

            Then("should safely return") {

                viewModel.addToCart()

                viewModel.state.value.foodItem shouldBe null
            }
        }
    }

    // =========================================================
    // ✅ MULTIPLE ACTIONS CONSISTENCY
    // =========================================================
    Given("multiple state updates") {

        When("user performs multiple actions") {

            Then("state should remain consistent") {

                runTest {

                    viewModel.state.test {

                        // ✅ Load data (async)
                        viewModel.loadFood("1")

                        awaitItem() // initial
                        val loadedState = expectMostRecentItem()

                        // ✅ Get correct key dynamically
                        val key = loadedState.optionGroups
                            .firstOrNull { it.title == "Extras" }?.title
                            ?: loadedState.optionGroups.first().title

                        val option = OptionItem("1", "Option")

                        // ✅ Perform actions AFTER state ready
                        viewModel.increaseQty()   // 1 → 2
                        viewModel.onOptionSelected(key, option)

                        val state = viewModel.state.value

                        println("State = $state") // debug

                        // ✅ Assertions
                        state.quantity shouldBe 2
                        state.selectedOptions[key] shouldBe option

                        cancelAndIgnoreRemainingEvents()
                    }
                }
            }
        }
    }

})

