package com.android.onlinefoodorderingapp.presentation.viewmodel


import app.cash.turbine.test
import com.android.onlinefoodorderingapp.data.local.SessionManager
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*

class StartupViewModelTest : BehaviorSpec({

    val dispatcher = StandardTestDispatcher()

    lateinit var viewModel: StartupViewModel
    val sessionManager = mockk<SessionManager>()

    beforeTest {
        Dispatchers.setMain(dispatcher)
    }

    afterTest {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    given("isLoggedIn flow") {

        `when`("user is logged in") {

            then("should emit true") {

                runTest {
                    every { sessionManager.isLoggedIn } returns flowOf(true)

                    viewModel = StartupViewModel(sessionManager)

                    viewModel.isLoggedIn.test {
                        awaitItem() shouldBe null   // ✅ initial value
                        awaitItem() shouldBe true   // ✅ actual emission
                    }
                }
            }
        }

        `when`("user is not logged in") {

            then("should emit false") {

                runTest {
                    every { sessionManager.isLoggedIn } returns flowOf(false)

                    viewModel = StartupViewModel(sessionManager)

                    viewModel.isLoggedIn.test {
                        awaitItem() shouldBe null
                        awaitItem() shouldBe false
                    }
                }
            }
        }
    }
})
