package com.android.onlinefoodorderingapp.presentation.screens.home


import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.android.onlinefoodorderingapp.domain.model.Category
import com.android.onlinefoodorderingapp.domain.model.Restaurant
import com.android.onlinefoodorderingapp.presentation.util.HomeUiState
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class HomeContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun fakeState() = HomeUiState.Success(
        location = "Bangalore",
        searchQuery = "",
        isVegMode = false,
        categories = listOf(
            Category(1, "Pizza", ""),
            Category(2, "Burger", "")
        ),
        exploreItems = emptyList(),
        restaurants = listOf(
            Restaurant(
                id = 1, name = "Dominos",
                url = "",
                location = "",
                cuisines = "Pizza",
                featuredImage = "",
                hasOnlineDelivery = "true",
                isDeliveringNow = "true",
                deliveryTime = "30",
                isVeg = true
            ),
            Restaurant(
                2, "KFC", "", "Chicken", "25", "", hasOnlineDelivery = "true",
                isDeliveringNow = "true", isVeg = true, deliveryTime = "30"
            )
        )
    )

    //  Screen renders
    @Test
    fun homeScreen_renders() {
        composeTestRule.setContent {
            HomeContent(
                uiState = fakeState(),
                isVeg = false,
                onCategoryClick = {},
                onRestaurantClick = {},
                onProfileMenuAction = {},
                onSearchChange = {},
                onSearchSubmit = {},
                onToggleVeg = {}
            )
        }

        composeTestRule
            .onNodeWithTag("home_screen")
            .assertExists()
    }

    // Search input works
    @Test
    fun searchInput_updatesValue() {

        var enteredText = ""

        composeTestRule.setContent {
            HomeContent(
                uiState = fakeState(),
                isVeg = false,
                onCategoryClick = {},
                onRestaurantClick = {},
                onProfileMenuAction = {},
                onSearchChange = { enteredText = it },
                onSearchSubmit = {},
                onToggleVeg = {}
            )
        }

        composeTestRule
            .onNodeWithTag("search_input", useUnmergedTree = true)
            .performTextInput("Pizza")

        assertTrue(enteredText.contains("Pizza"))
    }

    // Veg toggle click works
    @Test
    fun vegToggle_click_triggersCallback() {

        var toggled = false

        composeTestRule.setContent {
            HomeContent(
                uiState = fakeState(),
                isVeg = false,
                onCategoryClick = {},
                onRestaurantClick = {},
                onProfileMenuAction = {},
                onSearchChange = {},
                onSearchSubmit = {},
                onToggleVeg = { toggled = true }
            )
        }

        composeTestRule
            .onNodeWithTag("veg_toggle", useUnmergedTree = true)
            .performClick()

        assertTrue(toggled)
    }

    // Restaurant click

    fun restaurant_click_triggersCallback() {

        var clicked = false

        composeTestRule.setContent {
            HomeContent(
                uiState = fakeState(),
                isVeg = false,
                onCategoryClick = {},
                onRestaurantClick = { clicked = true },
                onProfileMenuAction = {},
                onSearchChange = {},
                onSearchSubmit = {},
                onToggleVeg = {}
            )
        }

        composeTestRule
            .onNodeWithTag("restaurant_1", useUnmergedTree = true)
            .assertExists()
            .performClick()

        assertTrue(clicked)
    }


    /*//  Profile icon click
    @Test
    fun profile_click_triggersCallback() {


        var clicked = false

        composeTestRule.setContent {
            CollapsingBanner(
                collapseFraction = 0f,
                onProfileMenuAction = {
                    clicked = true
                },
                isVeg = false,
                searchQuery = "",
                onSearchChange = {},
                onSearchSubmit = {},
                onToggleVeg = {}
            )
        }

        // Click profile icon
        composeTestRule
            .onNodeWithTag("profile_icon")
            .assertExists()
            .performClick()

        // WAIT for dropdown to appear
        composeTestRule.waitUntil(
            timeoutMillis = 3000
        ) {
            composeTestRule
                .onAllNodesWithText("Profile")
                .fetchSemanticsNodes().isNotEmpty()
        }

        //  Click menu item
        composeTestRule
            .onNodeWithText("Profile")
            .performClick()

        assertTrue(clicked)
    }*/

}


