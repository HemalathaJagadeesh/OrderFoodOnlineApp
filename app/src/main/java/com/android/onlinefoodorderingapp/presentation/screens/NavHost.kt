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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import com.android.onlinefoodorderingapp.domain.model.OrderType
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
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
import com.android.onlinefoodorderingapp.presentation.viewmodel.CartViewModel
import com.android.onlinefoodorderingapp.presentation.screens.cart.CartBottomBar
import com.android.onlinefoodorderingapp.presentation.screens.cart.CartScreen
import com.android.onlinefoodorderingapp.presentation.screens.cart.CartScreen1
import com.android.onlinefoodorderingapp.presentation.screens.cart.CartTopBar
import com.android.onlinefoodorderingapp.presentation.viewmodel.CartViewModel1
import com.android.onlinefoodorderingapp.presentation.viewmodel.CartViewModel2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String,
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var selectedType by remember {
        mutableStateOf(OrderType.DELIVERY)
    }

    val foodItem = navBackStackEntry
        ?.savedStateHandle
        ?.get<FoodItem>("foodItem")


    // ✅ Only create VM when Restaurant Details is active
    val restaurantDetailsViewModel: RestaurantDetailViewModel? =
        if (currentRoute == Routes.RESTAURANT_DETAILS) {
            val restaurantEntry = remember(navBackStackEntry) {
                navController.getBackStackEntry(Routes.RESTAURANT_DETAILS)
            }
            hiltViewModel(restaurantEntry)
        } else null

    val state = restaurantDetailsViewModel?.state?.collectAsState()?.value

    var currentFoodItem by remember { mutableStateOf<FoodItem?>(null) }


    val cartViewModel: CartViewModel2 = hiltViewModel()

    Scaffold(topBar = {
        when (currentRoute) {
            Routes.HOME -> {
                //MainTopBar(scrollBehavior)
                // HomeTopBar(collapseFraction = collapseFraction)
            }

            Routes.RESTAURANT_DETAILS -> {


                val cartCount = restaurantDetailsViewModel
                    ?.cartCount
                    ?.collectAsState()


                cartCount?.value?.let { RestaurantDetailsTopBar(navController) }
            }

            Routes.FOOD_DETAILS_SCREEN -> {

             /*   val cartCount = cartViewModel
                    ?.cartItems
                    ?.collectAsState()
                    ?.value
                    ?.sumOf { it.quantity } ?: 0*/


                RestaurantDetailsTopBar(navController)
            }
            Routes.CART_SCREEN ->{

               /* val cartCount = cartViewModel
                    ?.cartItems
                    ?.collectAsState()
                    ?.value
                    ?.sumOf { it.quantity } ?: 0*/

                CartTopBar(navController)
            }
        }
    }, bottomBar = {
        when (currentRoute) {
            Routes.HOME -> {
                MainBottomBar(
                    selectedType = selectedType, onTypeChange = { selectedType = it })

            }

            Routes.FOOD_DETAILS_SCREEN -> {


                currentFoodItem?.let { item ->

                    FoodCustomizationBottomBar(
                        onAddToCart = { qty ->
                            cartViewModel.addToCart(item, qty)
                        }
                    )

                }

            }

            Routes.RESTAURANT_DETAILS -> {
                // RestaurantDetailsBottomBar()
                if (currentRoute?.startsWith(Routes.RESTAURANT_DETAILS) == true) {
                    restaurantDetailsViewModel?.let { vm ->
                        state?.let { state ->
                            println("State: ${state.isMenuSheetOpen}")
                            RestaurantDetailsBottomBar(
                                searchText = state.menuSearchState.searchText,
                                onSearchChange = restaurantDetailsViewModel::onSearchChange,
                                onMenuClick = restaurantDetailsViewModel::onMenuClick,
                                modifier = Modifier
                            )
                        }
                    }
                }
            }
            Routes.CART_SCREEN -> {

                /*val totalPrice: Double =
                    cartViewModel?.totalPrice?.collectAsState()?.value ?: 0.0*/
                val totalPrice = 10.0
                CartBottomBar(total = totalPrice,
                    onCheckoutClick = {})
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
                    RestaurantDetailsScreen(restaurantId,navController,cartViewModel)
                }

                composable(Routes.FOOD_DETAILS_SCREEN) { navBackStackEntry ->

                    val foodid = navBackStackEntry.arguments?.getString("foodId")
                    //FoodDetailsScreen(foodId = foodid)
                    FoodDetailsScreen1(foodId = foodid, navController = navController,cartViewModel,

                        onFoodLoaded = { item ->
                            currentFoodItem = item   // ✅ update shared state
                        }
                    )
                }


                composable(Routes.CART_SCREEN) {
                    CartScreen(navController,cartViewModel)
                }


            }
        }

    }

    if (state?.isMenuSheetOpen == true && restaurantDetailsViewModel != null) {
        ModalBottomSheet(
            onDismissRequest = restaurantDetailsViewModel::onMenuDismiss
        ) {
            MenuContent(
                categories = state.categories, onClick = {
                        category ->
                    restaurantDetailsViewModel.onCategorySelected(category) // ✅ APPLY FILTER
                    restaurantDetailsViewModel.onMenuDismiss()

                })
        }
    }

}