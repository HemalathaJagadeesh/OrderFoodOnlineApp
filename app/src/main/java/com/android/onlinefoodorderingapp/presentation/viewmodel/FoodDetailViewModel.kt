package com.android.onlinefoodorderingapp.presentation.viewmodel

import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import com.android.onlinefoodorderingapp.data.local.DummyData
import com.android.onlinefoodorderingapp.domain.model.foodcustomization.Option
import com.android.onlinefoodorderingapp.domain.model.foodcustomization.OptionGroup
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import com.android.onlinefoodorderingapp.domain.model.CartItem
import com.android.onlinefoodorderingapp.domain.model.OptionItem
import com.android.onlinefoodorderingapp.presentation.util.FoodDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodDetailViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(FoodDetailState())
    val state: StateFlow<FoodDetailState> = _state.asStateFlow()


    fun loadFood(foodId: String) {
        viewModelScope.launch {

            _state.update { it.copy(isLoading = true) }

            //Replace with repo/API later
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

    fun onOptionSelected(groupTitle: String, option: OptionItem) {
        _state.update { current ->
            current.copy(
                selectedOptions = current.selectedOptions + (groupTitle to option)
            )
        }

        calculateTotal()
    }


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


    private fun calculateTotal() {
        val current = _state.value

        val basePrice = current.foodItem?.price ?: 0
       // val optionsPrice = current.selectedOptions.values.sumOf { it.price }

        //val total = (basePrice + optionsPrice) * current.quantity
        val total = 0
        _state.update { it.copy(totalPrice = total) }
    }

    //Add to cart
    fun addToCart() {
        val current = _state.value

        val item = current.foodItem ?: return

        val cartItem = CartItem(
            foodItem = item,
            quantity = current.quantity,
            selectedOptions = current.selectedOptions.values.toList(),
            totalPrice = current.totalPrice
        )


        println("Added to cart: $cartItem")
    }


    private fun getDummyFood(id: String): FoodItem? {
        return DummyData.foodItem.find { it.id == id }
        /*return FoodItem(
            id = id,
            name = "Veg Burger",
            price = "80",
            description = "A delicious veg burger made with fresh vegetables and a soft bun. Perfect for a quick meal or snack.",
            image = "https://source.unsplash.com/featured/?burger",
            isVeg = true

        )*/

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
