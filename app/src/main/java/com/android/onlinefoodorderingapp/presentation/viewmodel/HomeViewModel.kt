package com.android.onlinefoodorderingapp.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.onlinefoodorderingapp.data.local.DummyData.dummyCategories
import com.android.onlinefoodorderingapp.data.local.DummyData.dummyExploreItems
import com.android.onlinefoodorderingapp.data.local.DummyData.dummyRestaurants

import com.android.onlinefoodorderingapp.domain.model.Category
import com.android.onlinefoodorderingapp.domain.model.FilterParams
import com.android.onlinefoodorderingapp.domain.model.Restaurant
import com.android.onlinefoodorderingapp.domain.usecase.GetCategoriesUseCase
import com.android.onlinefoodorderingapp.domain.usecase.GetFeaturedRestaurantsUseCase
import com.android.onlinefoodorderingapp.domain.usecase.auth.LogoutUsecase
import com.android.onlinefoodorderingapp.presentation.screens.home.ProfileAction
import com.android.onlinefoodorderingapp.presentation.util.AppConstants
import com.android.onlinefoodorderingapp.presentation.util.HomeUiEvent
import com.android.onlinefoodorderingapp.presentation.util.HomeUiState
import com.android.onlinefoodorderingapp.presentation.util.UiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCategories: GetCategoriesUseCase,
    private val getFeaturedRestaurants: GetFeaturedRestaurantsUseCase,
    private val logoutUseCase: LogoutUsecase
) : ViewModel() {

    //  SEARCH
    private val searchQuery = MutableStateFlow(AppConstants.EMPTY_STRING)

    //  LOCATION
    private val location = MutableStateFlow("Ludhiana Bus Stop")

    //  FILTERS
    private val isVegMode = MutableStateFlow(false)
    val isVegModeState = isVegMode.asStateFlow()
    private val selectedTab = MutableStateFlow(0)

    private val _selectedRestaurant = MutableStateFlow<Restaurant?>(null)
    val selectedRestaurant = _selectedRestaurant.asStateFlow()

    //  UI EFFECTS
    private val _effect = MutableSharedFlow<UiEffect>()
    val effect = _effect.asSharedFlow()

    //Profile icon actions
    /* private val _profileActionEvent = MutableSharedFlow<UiEffect>()
     val profileActionEvent = _profileActionEvent.asSharedFlow()*/


    //  STATIC DATA
    private val categoriesFlow =

        flow {
            emit(getCategories()) // suspend call is legal here
        }.catch { emit(emptyList()) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = emptyList()
            )

    /*  private val featuredFlow = flow {
          emit(getFeaturedRestaurants())
      }*/

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.OnTopRestaurantsClick -> {
                handleOnTopRestaurantsClick(event.restaurant)
            }

            is HomeUiEvent.OnProfileMenuClick -> {
                handleProfileMenuAction(event.action)
            }
        }
    }

    private fun handleOnTopRestaurantsClick(restaurant: Restaurant) {
        viewModelScope.launch {
            _selectedRestaurant.value = restaurant
            _effect.emit(UiEffect.NavigateToRestaurantDetails(restaurant))
        }
    }

    private val featuredFlow: StateFlow<List<Restaurant>> = flow {
        emit(getFeaturedRestaurants())
    }.catch { emit(emptyList()) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )


    /*private val filterFlow =
        combine(location, searchQuery, isVegMode, selectedTab) { loc, query, veg, tab ->
            tab?.let { FilterParams(query, veg, it, loc) }
        }*/

    private val filterFlow =
        combine(location, searchQuery, isVegMode, selectedTab) { loc, query, veg, tab ->
            FilterParams(query, veg, tab, loc)
        }


    //UI STATE (Single source of truth)
    //private val _uiState = MutableStateFlow(HomeUiState())
    private val _uiState: StateFlow<HomeUiState> =
        combine(
            filterFlow,
            categoriesFlow,
            featuredFlow
        ) { filter, categories, featured ->

            val filteredRestaurants = dummyRestaurants
                .filter { !filter.isVegMode || it.isVeg }
                .filter {
                    filter.query.isBlank() ||
                            it.name.contains(filter.query, ignoreCase = true)
                }

            HomeUiState.Success(
                location = filter.location,
                searchQuery = filter.query,
                isVegMode = filter.isVegMode,
                selectedTab = filter.selectedTab,
                categories = dummyCategories,
                exploreItems = dummyExploreItems,
                restaurants = filteredRestaurants
            ) as HomeUiState
        }
            .catch { e ->
                emit(
                    HomeUiState.Error(
                        e.message ?: AppConstants.SOMETHING_WENT_WRONG
                    )
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HomeUiState.Loading
            )

    val uiState: StateFlow<HomeUiState> = _uiState


    // PAGINATION
    /*val pagedRestaurants: Flow<PagingData<Restaurant>> =
        combine(searchQuery, isVegMode, selectedTab, location) { q, veg, tab, loc ->
            tab?.let { FilterParams(q, veg, it, loc) }
        }.debounce(300).flatMapLatest { params ->
            params?.let {
                getPagedRestaurants(
                    query = it.query,
                    isVegMode = params.isVegMode,
                    category = params.selectedTab,
                    location = params.location
                )
            } ?: flow { emit(PagingData.empty()) }
        }.cachedIn(viewModelScope)
*/
    // SEARCH
    fun onSearchChange(query: String) {
        searchQuery.value = query
    }

    fun onSearchSubmit() {}

    // LOCATION
    fun updateLocation(newLocation: String) {
        location.value = newLocation
    }

    // FILTERS
    fun onVegToggleChanged(isVeg: Boolean) {
        isVegMode.value = isVeg

    }

    fun selectTab(tab: Category) {
        selectedTab.value = tab.id
    }

    private fun handleProfileMenuAction(action: ProfileAction) {


        when (action) {
            ProfileAction.OpenProfile -> {
                // _effect.tryEmit(UiEffect.NavigateToProfile)
            }

            ProfileAction.OpenSettings -> {
                //_effect.tryEmit(UiEffect.NavigateToSettings)
            }

            ProfileAction.Logout -> {
                viewModelScope.launch {
                    logoutUseCase()
                    _effect.emit(UiEffect.NavigateToLogin)
                }
            }
        }
    }

    fun resetHomeState() {
        searchQuery.value = ""
        isVegMode.value = false
        selectedTab.value = AppConstants.ALL_TAB  // or 0 if that’s default
    }


}

