@file:OptIn(ExperimentalMaterial3Api::class)

package com.android.onlinefoodorderingapp.presentation.screens

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import com.android.onlinefoodorderingapp.domain.model.OrderType
import com.android.onlinefoodorderingapp.presentation.screens.auth.AuthContainer
import com.android.onlinefoodorderingapp.presentation.screens.foodcustomization.FoodCustomizationBottomBar
import com.android.onlinefoodorderingapp.presentation.screens.restaurantdetails.RestaurantDetailsBottomBar
import com.android.onlinefoodorderingapp.presentation.screens.restaurantdetails.MenuContent
import com.android.onlinefoodorderingapp.presentation.screens.restaurantdetails.RestaurantDetailsScreen
import com.android.onlinefoodorderingapp.presentation.screens.restaurantdetails.RestaurantDetailsTopBar
import com.android.onlinefoodorderingapp.presentation.util.Routes
import com.android.onlinefoodorderingapp.presentation.viewmodel.RestaurantDetailViewModel
import com.android.onlinefoodorderingapp.presentation.screens.foodcustomization.FoodDetailsScreen1
import com.android.onlinefoodorderingapp.presentation.screens.home.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String,
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var selectedType by remember { mutableStateOf(OrderType.DELIVERY) }


    val viewModel: RestaurantDetailViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    Scaffold(topBar = {
        when (currentRoute) {
            Routes.HOME -> {
                //MainTopBar(scrollBehavior)
                // HomeTopBar(collapseFraction = collapseFraction)
            }

            Routes.RESTAURANT_DETAILS -> {
                RestaurantDetailsTopBar(navController)
            }

            Routes.FOOD_DETAILS_SCREEN -> {
                RestaurantDetailsTopBar(navController)
            }
        }
    }, bottomBar = {
        when (currentRoute) {
            Routes.HOME -> {
                MainBottomBar(
                    selectedType = selectedType, onTypeChange = { selectedType = it })

            }

            Routes.FOOD_DETAILS_SCREEN -> {
                FoodCustomizationBottomBar()
            }

            Routes.RESTAURANT_DETAILS -> {
                // RestaurantDetailsBottomBar()
                if (currentRoute?.startsWith(Routes.RESTAURANT_DETAILS) == true) {

                    println("State: ${state.isMenuSheetOpen}")
                    RestaurantDetailsBottomBar(
                        searchText = state.searchText,
                        onSearchChange = viewModel::onSearchChange,
                        onMenuClick = viewModel::onMenuClick,
                        modifier = Modifier
                    )
                }
            }

            else -> {}
        }
    }

    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(padding)
        ) {
            navigation(
                startDestination = Routes.PHONE_INPUT, route = Routes.AUTH_GRAPH
            ) {
                composable(Routes.PHONE_INPUT) {
                    AuthContainer(navController)
                }
            }
            //MAIN APP FLOW
            navigation(
                startDestination = Routes.HOME, route = Routes.MAIN_GRAPH
            ) {

                composable(Routes.HOME) {
                    HomeScreen(navController)
                }

                composable(Routes.RESTAURANT_DETAILS) { navBackStackEntry ->
                    val restaurantId = navBackStackEntry.arguments?.getString("restaurantId")
                    RestaurantDetailsScreen(restaurantId, navController)
                }

                composable(Routes.FOOD_DETAILS_SCREEN) { navBackStackEntry ->
                    val foodid = navBackStackEntry.arguments?.getString("foodId")
                    Log.d("NAV_DEBUG", "Current route: $currentRoute")
                    println("FoodId: $foodid")
                    //FoodDetailsScreen(foodId = foodid)
                    FoodDetailsScreen1(foodId = foodid, navController = navController)
                }

            }
        }

    }

    if (state?.isMenuSheetOpen == true && viewModel != null) {
        ModalBottomSheet(
            onDismissRequest = viewModel::onMenuDismiss
        ) {
            MenuContent(
                categories = state.categories, onClick = {
                    viewModel.onMenuDismiss()
                })
        }
    }

}