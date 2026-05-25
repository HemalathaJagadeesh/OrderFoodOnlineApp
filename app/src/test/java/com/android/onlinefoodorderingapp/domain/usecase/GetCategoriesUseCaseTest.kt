package com.android.onlinefoodorderingapp.domain.usecase


import com.android.onlinefoodorderingapp.domain.model.Category
import com.android.onlinefoodorderingapp.domain.repository.CategoryRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class GetCategoriesUseCaseTest : BehaviorSpec({

    lateinit var repo: CategoryRepository
    lateinit var useCase: GetCategoriesUseCase

    beforeTest {
        repo = mockk()
        useCase = GetCategoriesUseCase(repo)
    }

    given("GetCategoriesUseCase") {


        fun testCategory(id: Int = 1, name: String = "Fast Food", imageUrl: String = "image_url") =
            Category(id = id, name = name, imageUrl = imageUrl)

        // Non-empty list
        `when`("repository returns categories") {

            val categories = listOf(
                testCategory(1, "Pizza","image_url"),
                testCategory(2, "Burger", imageUrl = "image_url")
            )

            then("it should return the same list") {
                runTest {

                    coEvery {
                        repo.getCategories()
                    } returns categories

                    val result = useCase()

                    result shouldBe categories

                    coVerify(exactly = 1) {
                        repo.getCategories()
                    }
                }
            }
        }

        // Empty list
        `when`("repository returns empty list") {

            then("it should return empty list") {
                runTest {

                    coEvery {
                        repo.getCategories()
                    } returns emptyList()

                    val result = useCase()

                    result shouldBe emptyList()

                    coVerify(exactly = 1) {
                        repo.getCategories()
                    }
                }
            }
        }

        //  Exception case
        `when`("repository throws exception") {

            val exception = RuntimeException("Network error")

            then("it should propagate exception") {
                runTest {

                    coEvery {
                        repo.getCategories()
                    } throws exception

                    try {
                        useCase()
                    } catch (e: Exception) {
                        e shouldBe exception
                    }

                    coVerify(exactly = 1) {
                        repo.getCategories()
                    }
                }
            }
        }

        // Delegation verification
        `when`("usecase is invoked") {

            then("it should call repository") {
                runTest {

                    coEvery {
                        repo.getCategories()
                    } returns emptyList()

                    useCase()

                    coVerify(exactly = 1) {
                        repo.getCategories()
                    }
                }
            }
        }

        //Multiple calls
        `when`("usecase is called multiple times") {

            val categories = listOf(testCategory())

            then("repository should be called each time") {
                runTest {

                    coEvery {
                        repo.getCategories()
                    } returns categories

                    useCase()
                    useCase()

                    coVerify(exactly = 2) {
                        repo.getCategories()
                    }
                }
            }
        }
    }
})
