package com.android.onlinefoodorderingapp.presentation.viewmodel

import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import com.android.onlinefoodorderingapp.domain.model.foodcustomization.Option
import com.android.onlinefoodorderingapp.domain.model.foodcustomization.OptionGroup
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import com.android.onlinefoodorderingapp.presentation.screens.foodcustomization.OptionItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

// ----------------------------
// STATE
// ----------------------------

data class FoodDetailState(
    val foodItem: FoodItem? = null,
    val optionGroups: List<OptionGroup> = emptyList(),
    val selectedOptions: Map<String, OptionItem> = emptyMap(), // groupTitle -> selected option
    val quantity: Int = 1,
    val totalPrice: Int = 0,
    val isLoading: Boolean = false
)

// ----------------------------
// VIEWMODEL
// ----------------------------
@HiltViewModel
class FoodDetailViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(FoodDetailState())
    val state: StateFlow<FoodDetailState> = _state.asStateFlow()

    // ----------------------------
    // LOAD DATA
    // ----------------------------

    fun loadFood(foodId: String) {
        viewModelScope.launch {

            _state.update { it.copy(isLoading = true) }

            // 🔹 Replace with repo/API later
            val food = getDummyFood(foodId)
            val groups = getDummyOptions()

            _state.update {
                it.copy(
                    foodItem = food,
                    optionGroups = groups,
                    isLoading = false
                )
            }

            calculateTotal()
        }
    }

    // ----------------------------
    // OPTION SELECT
    // ----------------------------

    fun onOptionSelected(groupTitle: String, option: OptionItem) {
        _state.update { current ->
            current.copy(
                selectedOptions = current.selectedOptions + (groupTitle to option)
            )
        }

        calculateTotal()
    }

    // ----------------------------
    // QUANTITY
    // ----------------------------

    fun increaseQty() {
        _state.update { it.copy(quantity = it.quantity + 1) }
        calculateTotal()
    }

    fun decreaseQty() {
        _state.update {
            if (it.quantity > 1) it.copy(quantity = it.quantity - 1)
            else it
        }
        calculateTotal()
    }

    // ----------------------------
    // TOTAL PRICE
    // ----------------------------

    private fun calculateTotal() {
        val current = _state.value

        val basePrice = current.foodItem?.price ?: 0
       // val optionsPrice = current.selectedOptions.values.sumOf { it.price }

        //val total = (basePrice + optionsPrice) * current.quantity
        val total = 2222
        _state.update { it.copy(totalPrice = total) }
    }

    // ----------------------------
    // ADD TO CART
    // ----------------------------

    fun addToCart() {
        val current = _state.value

        val item = current.foodItem ?: return

        val cartItem = CartItem(
            foodItem = item,
            quantity = current.quantity,
            selectedOptions = current.selectedOptions.values.toList(),
            totalPrice = current.totalPrice
        )

        // 🔹 Replace with repository later
        println("Added to cart: $cartItem")
    }

    // ----------------------------
    // DUMMY DATA
    // ----------------------------

    private fun getDummyFood(id: String): FoodItem {
        return FoodItem(
            id = id,
            name = "Veg Burger",
            price = "80",
            description = "A delicious veg burger made with fresh vegetables and a soft bun. Perfect for a quick meal or snack.",
            image = "https://source.unsplash.com/featured/?burger",

        )
    }
    /*rating = 4.2f,
                ratingCount = 120,
                isVeg = true*/
    private fun getDummyOptions(): List<OptionGroup> {
        return listOf(
            OptionGroup(
                title = "Choose Size",
                isSingleChoice = true,
                options  = listOf(
                    Option("1", "Crunchy shell"),
                    Option("2", "Soft shell")
                )
            ),
            OptionGroup(
                title = "Extras",
                isSingleChoice = false,
                options = listOf(
                    Option("1", "Crunchy shell"),
                    Option("2", "Soft shell")
                )
            )
        )
    }
}

// ----------------------------
// CART MODEL
// ----------------------------

data class CartItem(
    val foodItem: FoodItem,
    val quantity: Int,
    val selectedOptions: List<OptionItem>,
    val totalPrice: Int
)