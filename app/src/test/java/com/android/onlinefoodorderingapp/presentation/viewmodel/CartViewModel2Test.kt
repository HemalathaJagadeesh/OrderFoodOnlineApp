package com.android.onlinefoodorderingapp.presentation.viewmodel

import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest

class CartViewModel2Test : BehaviorSpec({

    lateinit var viewmodel: CartViewModel2

    beforeTest {
        viewmodel = CartViewModel2()
    }

    fun dummyFoodItem(id: String) = FoodItem(
        foodId = id,
        name = "Item $id",
        description = "desc",
        price = 10.0,
        image = "img",
        isSpicy = false,
        isVeg = true,
        category = "Test"
    )

    //ADD NEW ITEM
    given("add item to cart") {
        `when`("new item is added") {
            then("should add item correctly") {
                val item = dummyFoodItem("1")

                viewmodel.addToCart(item, 1)

                val result = viewmodel.cartItems.value

                result.size shouldBe 1
                result[0].quantity shouldBe 1
            }
        }
    }


    given("add existing item") {
        `when`("same item is added again") {
            then("should increase quantity") {
                val item = dummyFoodItem("1")

                viewmodel.addToCart(item, 1)
                viewmodel.addToCart(item, 2)

                val result = viewmodel.cartItems.value

                result.size shouldBe 1
                result[0].quantity shouldBe 3
            }
        }
    }

    //ZERO QUANTITY (NEGATIVE CASE)
    given("add item with zero quantity") {
        `when`("quantity is 0") {
            then("should not add item") {
                val item = dummyFoodItem("1")

                viewmodel.addToCart(item, 0)

                viewmodel.cartItems.value shouldBe emptyList()
            }
        }
    }

    //
    given("add item with negative quantity") {
        `when`("quantity is negative") {
            then("should ignore request") {
                val item = dummyFoodItem("1")

                viewmodel.addToCart(item, -1)

                viewmodel.cartItems.value shouldBe emptyList()
            }
        }
    }


    given("increase quantity") {
        `when`("item exists") {
            then("should increment quantity") {
                val item = dummyFoodItem("1")

                viewmodel.addToCart(item, 1)
                viewmodel.increaseQuantity("1")

                viewmodel.cartItems.value[0].quantity shouldBe 2
            }
        }
    }

    //INCREASE NON-EXISTING
    given("increase non-existing item") {
        `when`("item not present") {
            then("should not crash or modify cart") {
                viewmodel.increaseQuantity("1")

                viewmodel.cartItems.value shouldBe emptyList()
            }
        }
    }

    //DECREASE QUANTITY
    given("decrease quantity") {
        `when`("item quantity > 1") {
            then("should decrement quantity") {
                val item = dummyFoodItem("1")

                viewmodel.addToCart(item, 2)
                viewmodel.decreaseQuantity("1")

                viewmodel.cartItems.value[0].quantity shouldBe 1
            }
        }
    }

    //DECREASE TO ZERO → REMOVE
    given("decrease to zero") {
        `when`("quantity becomes zero") {
            then("should remove item") {
                val item = dummyFoodItem("1")

                viewmodel.addToCart(item, 1)
                viewmodel.decreaseQuantity("1")

                viewmodel.cartItems.value shouldBe emptyList()
            }
        }
    }

    // DECREASE NON-EXISTING
    given("decrease non-existing item") {
        `when`("item not in cart") {
            then("should not crash") {
                viewmodel.decreaseQuantity("1")

                viewmodel.cartItems.value shouldBe emptyList()
            }
        }
    }

    //REMOVE ITEM
    given("remove item") {
        `when`("item exists") {
            then("should remove it") {
                val item = dummyFoodItem("1")

                viewmodel.addToCart(item, 1)
                viewmodel.removeItem("1")

                viewmodel.cartItems.value shouldBe emptyList()
            }
        }
    }

    // REMOVE NON-EXISTING
    given("remove non-existing item") {
        `when`("item not present") {
            then("cart remains unchanged") {
                viewmodel.removeItem("1")

                viewmodel.cartItems.value shouldBe emptyList()
            }
        }
    }

    // MULTIPLE ITEMS
    given("multiple items added") {
        `when`("different items are added") {
            then("all should exist in cart") {
                val item1 = dummyFoodItem("1")
                val item2 = dummyFoodItem("2")

                viewmodel.addToCart(item1, 1)
                viewmodel.addToCart(item2, 2)

                viewmodel.cartItems.value.size shouldBe 2
            }
        }
    }

    given("cart count") {
        `when`("items are added") {
            then("should calculate total quantity") {
                runTest {
                    val item = dummyFoodItem("1")

                    val flow = viewmodel.cartCount

                    viewmodel.addToCart(item, 2)
                    viewmodel.addToCart(item, 3)

                    val count = flow.first()

                    count shouldBe 5
                }
            }
        }
    }


    given("total price") {
        `when`("items are added") {

            then("should calculate total quantity") {
                runTest {
                    val item = dummyFoodItem("1")

                    viewmodel.addToCart(item, 2)
                    viewmodel.addToCart(item, 3)

                    val count = viewmodel.cartCount.first()

                    count shouldBe 5
                }
            }

        }
    }

    given("prevent negative quantity") {
        `when`("decreasing beyond zero") {
            then("should not go negative") {
                val item = dummyFoodItem("1")

                viewmodel.addToCart(item, 1)
                viewmodel.decreaseQuantity("1")
                viewmodel.decreaseQuantity("1")

                viewmodel.cartItems.value shouldBe emptyList()
            }
        }
    }
})
