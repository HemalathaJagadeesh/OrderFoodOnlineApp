package com.android.onlinefoodorderingapp.data.repository


import com.android.onlinefoodorderingapp.data.local.DummyData
import com.android.onlinefoodorderingapp.data.local.dao.RestaurantDao
import com.android.onlinefoodorderingapp.data.remote.api.ZomatoApiService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.collections.shouldNotBeEmpty
import kotlinx.coroutines.runBlocking
import io.mockk.mockk

class CategoryRepositoryImplTest : BehaviorSpec({

    lateinit var repository: CategoryRepositoryImpl

    beforeTest {
        // Mocks are not really used, but required for constructor
        val api = mockk<ZomatoApiService>(relaxed = true)
        val dao = mockk<RestaurantDao>(relaxed = true)

        repository = CategoryRepositoryImpl(api, dao)
    }

    given("repository always returns dummy data") {

        `when`("getCategories is called") {

            then("it should return dummy categories") {

                val result = runBlocking {
                    repository.getCategories()
                }

                result shouldBe DummyData.categories
            }

            then("it should not return empty list") {

                val result = runBlocking {
                    repository.getCategories()
                }

                result.shouldNotBeEmpty()
            }
        }
    }

    given("multiple calls to repository") {

        `when`("getCategories is called multiple times") {

            then("it should return same data every time") {

                val result1 = runBlocking {
                    repository.getCategories()
                }

                val result2 = runBlocking {
                    repository.getCategories()
                }

                result1 shouldBe result2
            }
        }
    }

    given("data consistency") {

        `when`("result is fetched") {

            then("it should match dummy data size") {

                val result = runBlocking {
                    repository.getCategories()
                }

                result.size shouldBe DummyData.categories.size
            }
        }
    }
})