package com.android.onlinefoodorderingapp.presentation.viewmodel

import com.android.onlinefoodorderingapp.data.local.DummyData
import com.android.onlinefoodorderingapp.domain.model.OptionItem
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.maps.shouldContainKey
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class FoodDetailViewModelTest
    : FunSpec({
    val dispatcher = UnconfinedTestDispatcher()

    lateinit var viewModel: FoodDetailViewModel

    beforeTest {
        Dispatchers.setMain(dispatcher)
        viewModel = FoodDetailViewModel()
    }

    afterTest {
        Dispatchers.resetMain()
    }

    test("loadFood should update food item and option groups") {
        viewModel.loadFood("1")

        dispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value

        state.foodItem shouldBe DummyData.foodItem.first { it.id == "1" }
        state.optionGroups.isNotEmpty() shouldBe true
        state.isLoading shouldBe false
    }

    test("increaseQty should increment quantity") {
        val initialQty = viewModel.state.value.quantity

        viewModel.increaseQty()
        dispatcher.scheduler.advanceUntilIdle()

        viewModel.state.value.quantity shouldBe initialQty + 1
    }

    test("decreaseQty should decrement quantity when greater than one") {
        viewModel.increaseQty()
        dispatcher.scheduler.advanceUntilIdle()

        viewModel.decreaseQty()
        dispatcher.scheduler.advanceUntilIdle()

        viewModel.state.value.quantity shouldBe 1
    }

    test("decreaseQty should not go below one") {
        viewModel.decreaseQty()
        dispatcher.scheduler.advanceUntilIdle()

        viewModel.state.value.quantity shouldBe 1
    }

    test("onOptionSelected should update selectedOptions map") {
        val option = OptionItem(
            id = "1",
            name = "Crunchy shell"
        )

        viewModel.onOptionSelected("Extras", option)
        dispatcher.scheduler.advanceUntilIdle()

        val selectedOptions = viewModel.state.value.selectedOptions

        selectedOptions shouldContainKey "Extras"
        selectedOptions["Extras"] shouldBe option
    }

    test("calculateTotal should update total price") {
        viewModel.loadFood("1")
        dispatcher.scheduler.advanceUntilIdle()

        // Hardcoded value in ViewModel
        viewModel.state.value.totalPrice shouldBe 2222
    }

    test("addToCart should not crash when food exists") {
        viewModel.loadFood("1")
        dispatcher.scheduler.advanceUntilIdle()

        viewModel.addToCart() // validation = no exception
    }

    test("addToCart should safely return when food is null") {
        viewModel.addToCart()
    }
})
