package com.android.onlinefoodorderingapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.onlinefoodorderingapp.data.local.DummyData
import com.android.onlinefoodorderingapp.domain.model.CartItem
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import com.android.onlinefoodorderingapp.domain.usecase.cart.GetCartCountUseCase
import com.android.onlinefoodorderingapp.presentation.util.FoodFilter
import com.android.onlinefoodorderingapp.presentation.util.RestaurantDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantDetailViewModel @Inject constructor(
    private val getCartCountUseCase: GetCartCountUseCase
) : ViewModel() {

    val cartCount = getCartCountUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)
    private val _state = MutableStateFlow(
        RestaurantDetailUiState(
            categories = listOf(
                "All",
                "Pizza",
                "Burger",
                "Pasta",
                "Salads",
                "Desserts"
            )
        )
    )
    val state = _state.asStateFlow()


    private val searchTextFlow = state
        .map { it.menuSearchState.searchText }
        .debounce(300) // ✅ debounce delay
        .distinctUntilChanged()

    /**
     * DERIVED FILTERED LIST
     * UI MUST consume this
     */
    val filteredFoodItems = combine(
        state,           // for filters + category + data
        searchTextFlow   // debounced search text
    ) { current, debouncedSearch ->

        val selectedCategory = current.menuSearchState.selectedCategory

        current.allFoodItems
            .filter {
                debouncedSearch.isBlank() ||
                        it.name.contains(debouncedSearch, ignoreCase = true)
            }
            .filter {
                selectedCategory == null ||
                        it.category.equals(selectedCategory, ignoreCase = true)
            }
            .filter {
                when (current.selectedFilter) {
                    FoodFilter.ALL -> true
                    FoodFilter.VEG -> it.isVeg
                    FoodFilter.NON_VEG -> !it.isVeg
                    FoodFilter.SPICY -> it.isSpicy
                }
            }

    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        emptyList()
    )



    init {
        loadInitialData()
    }

    fun onSearchChange(searchText: String) {

        _state.update {
            it.copy(
                menuSearchState = it.menuSearchState.copy(
                    searchText = searchText
                )
            )
        }

    }
    fun onCategorySelected(category: String) {
        _state.update { current ->

            val newCategory =
                if (category.equals("All", ignoreCase = true)) {
                    null
                } else if (current.menuSearchState.selectedCategory == category) {
                    null
                } else {
                    category
                }

            current.copy(
                menuSearchState = current.menuSearchState.copy(
                    selectedCategory = newCategory
                )
            )
        }
    }

    fun openMenuSheet() {
        _state.update { it.copy(isMenuSheetOpen = true) }
    }

    fun closeMenuSheet() {
        _state.update { it.copy(isMenuSheetOpen = false) }
    }

    fun onMenuClick() {
        println("Viewmodel recieved Click")
        _state.update { it.copy(isMenuSheetOpen = true) }
    }

    fun onMenuDismiss() {
        _state.update { it.copy(isMenuSheetOpen = false) }

    }

    fun onFoodItemClick(item: FoodItem) {
        println("Food Item Clicked: ${item.name}")
        _state.update { it.copy(selectedFoodItem = item) }

    }

    private fun loadInitialData() {
        _state.update {
            it.copy(allFoodItems = DummyData.foodItem)
        }
    }


    fun loadData(restaurantId: String) {

        viewModelScope.launch {
            val items = DummyData.foodItem
            _state.update {
                it.copy(allFoodItems = items)
            }
        }

    }

    fun onFilterSelected(filter: FoodFilter) {

        _state.update { it.copy(selectedFilter = filter) }
    }

}