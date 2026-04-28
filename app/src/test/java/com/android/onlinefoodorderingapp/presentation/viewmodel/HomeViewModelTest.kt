package com.android.onlinefoodorderingapp.presentation.viewmodel

import app.cash.turbine.test
import com.android.onlinefoodorderingapp.data.local.DummyData.dummyRestaurants
import com.android.onlinefoodorderingapp.domain.usecase.*
import com.android.onlinefoodorderingapp.domain.usecase.auth.LogoutUsecase
import com.android.onlinefoodorderingapp.presentation.screens.home.ProfileAction
import com.android.onlinefoodorderingapp.presentation.util.HomeUiEvent
import com.android.onlinefoodorderingapp.presentation.util.HomeUiState
import com.android.onlinefoodorderingapp.presentation.util.UiEffect
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain


@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest : FunSpec({

    // ✅ Dispatcher that works in your setup
    val dispatcher = UnconfinedTestDispatcher()

    // ✅ Mocks
    val getPagedRestaurants = mockk<GetPagedRestaurantUseCase>(relaxed = true)
    val getCategories = mockk<GetCategoriesUseCase>()
    val getFeaturedRestaurants = mockk<GetFeaturedRestaurantsUseCase>()
    val logoutUsecase = mockk<LogoutUsecase>(relaxed = true)

    lateinit var viewModel: HomeViewModel

    beforeTest {
        Dispatchers.setMain(dispatcher)

        coEvery { getCategories() } returns emptyList()
        coEvery { getFeaturedRestaurants() } returns emptyList()

        viewModel = HomeViewModel(
            getCategories = getCategories,
            getFeaturedRestaurants = getFeaturedRestaurants,
            logoutUseCase = logoutUsecase
        )
    }

    afterTest {
        Dispatchers.resetMain()
    }

    test("initial uiState should be Success with default values") {
        viewModel.uiState.test {
            val state = awaitItem() as HomeUiState.Success

            state.isVegMode shouldBe false
            state.searchQuery shouldBe ""
            state.location shouldBe "Ludhiana Bus Stop"
        }
    }


    test("veg toggle emits updated uiState") {
        viewModel.uiState.test {
            val initial = awaitItem() as HomeUiState.Success
            initial.isVegMode shouldBe false

            viewModel.onVegToggleChanged(true)

            val updated = awaitItem() as HomeUiState.Success
            updated.isVegMode shouldBe true
        }
    }

    test("search change updates uiState") {
        viewModel.uiState.test {
            awaitItem() // initial Success

            viewModel.onSearchChange("Pizza")

            val updated = awaitItem() as HomeUiState.Success
            updated.searchQuery shouldBe "Pizza"
        }
    }


    test("restaurant click should emit navigation effect") {
        val restaurant = dummyRestaurants.first()

        viewModel.effect.test {
            viewModel.onEvent(
                HomeUiEvent.OnTopRestaurantsClick(restaurant)
            )

            awaitItem() shouldBe UiEffect.NavigateToRestaurantDetails(restaurant)
        }
    }

    test("logout should call usecase and emit NavigateToLogin effect") {
        viewModel.effect.test {
            viewModel.onEvent(
                HomeUiEvent.OnProfileMenuClick(ProfileAction.Logout)
            )

            awaitItem() shouldBe UiEffect.NavigateToLogin
        }

        coVerify { logoutUsecase() }
    }
})