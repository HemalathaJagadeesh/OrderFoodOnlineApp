package com.android.onlinefoodorderingapp.presentation.viewmodel

import com.android.onlinefoodorderingapp.data.local.DummyData
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class RestaurantDetailViewModelTest
    : BehaviorSpec({

    val dispatcher = UnconfinedTestDispatcher()

    lateinit var viewModel: RestaurantDetailViewModel

   /* beforeTest {
        Dispatchers.setMain(dispatcher)
        viewModel = RestaurantDetailViewModel()
    }*/

    afterTest {
        Dispatchers.resetMain()
    }

    Given("RestaurantDetailViewModel initialization") {

        Then("it should initialize categories correctly") {
            runTest(dispatcher) {
                val state = viewModel.state.first()

                state.categories.shouldContainExactly(
                    "Pizza",
                    "Burger",
                    "Pasta",
                    "Salads",
                    "Desserts"
                )
            }
        }

       /* Then("it should load dummy food list on init") {
            runTest(dispatcher) {
                val state = viewModel.state.first()

                state.foodItem shouldBe DummyData.foodItem
            }
        }*/
    }

    Given("search input change") {

        When("onSearchChange is called") {
            Then("searchText should update") {
                runTest(dispatcher) {
                    viewModel.onSearchChange("Burger")

                    viewModel.state.first().searchText shouldBe "Burger"
                }
            }
        }
    }

    Given("menu sheet interactions") {

        Then("openMenuSheet should open the menu") {
            runTest(dispatcher) {
                viewModel.openMenuSheet()

                viewModel.state.first().isMenuSheetOpen.shouldBeTrue()
            }
        }

        Then("closeMenuSheet should close the menu") {
            runTest(dispatcher) {
                viewModel.openMenuSheet()
                viewModel.closeMenuSheet()

                viewModel.state.first().isMenuSheetOpen.shouldBeFalse()
            }
        }

        Then("onMenuClick should open the menu") {
            runTest(dispatcher) {
                viewModel.onMenuClick()

                viewModel.state.first().isMenuSheetOpen.shouldBeTrue()
            }
        }

        Then("onMenuDismiss should close the menu") {
            runTest(dispatcher) {
                viewModel.openMenuSheet()
                viewModel.onMenuDismiss()

                viewModel.state.first().isMenuSheetOpen.shouldBeFalse()
            }
        }
    }

    Given("food item selection") {

        Then("selectedFoodItem should be updated") {
            runTest(dispatcher) {
                val foodItem: FoodItem = DummyData.foodItem.first()

                viewModel.onFoodItemClick(foodItem)

                viewModel.state.first().selectedFoodItem shouldBe foodItem
            }
        }
    }
/*
    Given("add item click") {

        Then("onAddItemClick should not crash") {
            runTest(dispatcher) {
                viewModel.onAddItemClick()
                true shouldBe true
            }
        }
    }*/
})

