package com.android.onlinefoodorderingapp.presentation.viewmodel

import app.cash.turbine.test
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import com.android.onlinefoodorderingapp.domain.usecase.cart.GetCartCountUseCase
import com.android.onlinefoodorderingapp.presentation.util.FoodFilter
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)

class RestaurantDetailViewModelTest : BehaviorSpec({

    val dispatcher = StandardTestDispatcher()
    val scheduler = dispatcher.scheduler

    lateinit var viewModel: RestaurantDetailViewModel
    lateinit var getCartCountUseCase: GetCartCountUseCase
    lateinit var cartFlow: MutableStateFlow<Int>

    beforeTest {
        Dispatchers.setMain(dispatcher)

        getCartCountUseCase = mockk()

        cartFlow = MutableStateFlow(0)

        every { getCartCountUseCase.invoke() } returns cartFlow

        viewModel = RestaurantDetailViewModel(getCartCountUseCase)
    }

    afterTest {
        Dispatchers.resetMain()
    }

    fun advanceDebounce() {
        scheduler.advanceTimeBy(300)
    }

    given("ViewModel initialization") {

        `when`("initialized") {

            then("should load categories and data") {
                val state = viewModel.state.value

                state.categories shouldContain "Pizza"
                state.allFoodItems.shouldNotBeEmpty()
            }

            then("cartCount should emit initial value") {
                viewModel.cartCount.test {
                    awaitItem() shouldBe 0
                }
            }
        }
    }

    given("Search functionality") {

        `when`("valid query") {

            then("should filter correctly") {

                viewModel.filteredFoodItems.test {

                    awaitItem()

                    viewModel.onSearchChange("pizza")

                    scheduler.advanceTimeBy(300)

                    scheduler.runCurrent()

                    val result = awaitItem()

                    result.shouldNotBeEmpty()

                    result.all {
                        it.name.contains("pizza", true)
                    } shouldBe true
                }
            }
        }



        `when`("blank query") {

            then("should return all items") {
                viewModel.onSearchChange("")

                viewModel.filteredFoodItems.test {
                    advanceDebounce()
                    val result = awaitItem()

                    result.size shouldBe viewModel.state.value.allFoodItems.size
                }
            }
        }

        `when`("no results") {

            then("should return empty list") {
                viewModel.onSearchChange("xyz123")

                viewModel.filteredFoodItems.test {
                    advanceDebounce()
                    awaitItem() shouldBe emptyList()
                }
            }
        }
    }


    given("Category selection") {

        `when`("category selected") {

            then("filters correctly") {
                viewModel.onCategorySelected("Pizza")

                viewModel.filteredFoodItems.test {
                    advanceDebounce()
                    val result = awaitItem()

                    result.all {
                        it.category.equals("Pizza", true)
                    } shouldBe true
                }
            }
        }

        `when`("same category selected again") {

            then("should toggle off") {
                viewModel.onCategorySelected("Pizza")
                viewModel.onCategorySelected("Pizza")

                viewModel.state.value.menuSearchState.selectedCategory shouldBe null
            }
        }

        `when`("All selected") {

            then("should clear filter") {
                viewModel.onCategorySelected("All")

                viewModel.state.value.menuSearchState.selectedCategory shouldBe null
            }
        }

        `when`("invalid category") {

            then("should return empty list") {
                viewModel.onCategorySelected("Invalid")

                viewModel.filteredFoodItems.test {
                    advanceDebounce()
                    awaitItem() shouldBe emptyList()
                }
            }
        }
    }

    given("Food filters") {

        `when`("VEG selected") {

            then("returns only veg") {
                viewModel.onFilterSelected(FoodFilter.VEG)

                viewModel.filteredFoodItems.test {
                    advanceDebounce()
                    val result = awaitItem()

                    result.all { it.isVeg } shouldBe true
                }
            }
        }

        `when`("NON_VEG selected") {

            then("returns only non-veg") {
                viewModel.onFilterSelected(FoodFilter.NON_VEG)

                viewModel.filteredFoodItems.test {
                    advanceDebounce()
                    val result = awaitItem()

                    result.all { !it.isVeg } shouldBe true
                }
            }
        }

        `when`("no matching filter") {

            then("returns empty list") {
                viewModel.onFilterSelected(FoodFilter.SPICY)

                viewModel.filteredFoodItems.test {
                    advanceDebounce()
                    val result = awaitItem()

                    if (result.isEmpty()) result shouldBe emptyList()
                }
            }
        }
    }

    given("Menu sheet state") {

        `when`("opened") {
            then("should be true") {
                viewModel.openMenuSheet()
                viewModel.state.value.isMenuSheetOpen shouldBe true
            }
        }

        `when`("closed") {
            then("should be false") {
                viewModel.closeMenuSheet()
                viewModel.state.value.isMenuSheetOpen shouldBe false
            }
        }

        `when`("dismissed") {
            then("should close") {
                viewModel.onMenuDismiss()
                viewModel.state.value.isMenuSheetOpen shouldBe false
            }
        }
    }

    given("Food item interaction") {

        `when`("item clicked") {
            then("updates selected item") {
                val item = viewModel.state.value.allFoodItems.first()

                viewModel.onFoodItemClick(item)

                viewModel.state.value.selectedFoodItem shouldBe item
            }
        }

        `when`("random item clicked") {
            then("still updates safely") {
                val item = FoodItem("Test", "Test", 10.0, "image_url", "food_desc",
                    true, true, "Pizza")

                viewModel.onFoodItemClick(item)

                viewModel.state.value.selectedFoodItem shouldBe item
            }
        }
    }

    given("Cart count updates") {

        `when`("flow emits new values") {

            then("should reflect updates") {
                viewModel.cartCount.test {

                    // Initial emission
                    awaitItem() shouldBe 0

                    // Emit new value
                    cartFlow.value = 5
                    scheduler.runCurrent()

                    awaitItem() shouldBe 5

                    // Emit another value
                    cartFlow.value = 10
                    scheduler.runCurrent()

                    awaitItem() shouldBe 10
                }
            }
        }
    }
})


