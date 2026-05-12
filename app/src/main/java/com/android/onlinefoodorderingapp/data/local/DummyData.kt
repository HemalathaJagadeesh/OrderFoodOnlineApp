package com.android.onlinefoodorderingapp.data.local

import com.android.onlinefoodorderingapp.domain.model.Category
import com.android.onlinefoodorderingapp.domain.model.ExploreItem
import com.android.onlinefoodorderingapp.domain.model.Restaurant
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import com.android.onlinefoodorderingapp.presentation.screens.home.ProfileAction
import com.android.onlinefoodorderingapp.presentation.screens.home.components.ProfileMenuItem


object DummyData {

    val categories = listOf(
        Category(
            id = 1,
            name = "Pizza",
            imageUrl = "https://picsum.photos/200?pizza"
        ),
        Category(
            id = 2,
            name = "Burgers",
            imageUrl = "https://picsum.photos/200?burger"
        ),
        Category(
            id = 3,
            name = "Biryani",
            imageUrl = "https://picsum.photos/200?biryani"
        ),
        Category(
            id = 4,
            name = "Desserts",
            imageUrl = "https://picsum.photos/200?dessert"
        ),
        Category(
            id = 5,
            name = "Drinks",
            imageUrl = "https://picsum.photos/200?drinks"
        )
    )

    val foodItem = listOf(
        FoodItem(
            name = "Spicy Chicken Crunch",
            description = "Crispy chicken tossed in signature spicy sauce",
            price = 269.0,
            image = "https://images.unsplash.com/photo-1600891964599-f61ba0e24092",
            foodId = "1",
            isVeg = false,
            isSpicy = false,
            category = "Pizza"
        ),
        FoodItem(
            name = "Ultimate Cheesy Nachos",
            description = "Loaded nachos with cheese, jalapenos & salsa",
            price =229.0,
            image = "https://images.unsplash.com/photo-1600891964599-f61ba0e24092",
            foodId = "2",
            isVeg = true,
            isSpicy = true,
            category = "Salads"
        ),
        FoodItem(
            name = "Spicy Chicken Crunch",
            description = "Crispy chicken tossed in signature spicy sauce",
            price = 267.0,
            image = "https://images.unsplash.com/photo-1600891964599-f61ba0e24092",
            foodId = "3",
            isVeg = true,
            isSpicy = false,
            category = "Pizza"

        ),
        FoodItem(
            name = "Ultimate Cheesy Nachos",
            description = "Loaded nachos with cheese, jalapenos & salsa",
            price = 229.0,
            image = "https://images.unsplash.com/photo-1600891964599-f61ba0e24092",
            foodId = "4",
            isVeg = false,
            isSpicy = true,
            category = "Salads"
        )
    )


    val dummyExploreItems = listOf(
        ExploreItem(
            1,
            "Top Rated",
            "https://img.freepik.com/premium-vector/flash-sale-discount-promotion-banner_603380-265.jpg?w=2000",
            type = ""
        ),
        ExploreItem(
            2,
            "Fast Delivery",
            "https://images.unsplash.com/photo-1526367790999-0150786686a2",
            type = ""
        ),
        ExploreItem(
            3,
            "Great Offers",
            "https://images.unsplash.com/photo-1600891964599-f61ba0e24092",
            type = ""
        ),
        ExploreItem(
            4,
            "Healthy",
            "https://images.unsplash.com/photo-1546069901-ba9599a7e63c",
            type = ""
        ),
        ExploreItem(
            5,
            "Healthy",
            "https://images.unsplash.com/photo-1546069901-ba9599a7e63c",
            type = ""
        ),
        ExploreItem(
            6,
            "Healthy",
            "https://images.unsplash.com/photo-1546069901-ba9599a7e63c",
            type = ""
        )
    )


    val dummyRestaurants = listOf(

        Restaurant(
            id = 1,
            name = "Burger House",
            url = "https://images.unsplash.com/photo-1550547660-d9450f859349",
            cuisines = "Burgers, Fast Food",
            deliveryTime = "25",
            hasOnlineDelivery = "",
            isDeliveringNow = "",
            featuredImage = "https://images.unsplash.com/photo-1550547660-d9450f8593",
            location = "",
            isVeg = false
        ),

        Restaurant(
            id = 2,
            name = "Pizza Palace",
            url = "https://images.unsplash.com/photo-1600891964599-f61ba0e24092",
            cuisines = "Pizza, Italian",
            deliveryTime = "30",
            hasOnlineDelivery = "",
            isDeliveringNow = "",
            featuredImage = "https://images.unsplash.com/photo-1550547660-d9450f8593",
            location = "",
            isVeg = true
        ),

        Restaurant(
            id = 3,
            name = "Spice Kitchen",
            url = "https://images.unsplash.com/photo-1604908176997-125f25cc6f3d",
            cuisines = "Indian, Biryani",
            deliveryTime = "35",
            hasOnlineDelivery = "",
            isDeliveringNow = "",
            featuredImage = "https://images.unsplash.com/photo-1550547660-d9450f8593",
            location = "",
            isVeg = false
        ),

        Restaurant(
            id = 4,
            name = "Sushi World",
            url = "https://images.unsplash.com/photo-1562158070-57b2b2c2b6e3",
            cuisines = "Sushi, Japanese",
            deliveryTime = "40",
            hasOnlineDelivery = "",
            isDeliveringNow = "",
            featuredImage = "https://images.unsplash.com/photo-1550547660-d9450f8593",
            location = "",
            isVeg = false
        ),

        Restaurant(
            id = 5,
            name = "Healthy Bites",
            url = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c",
            cuisines = "Salads, Healthy",
            deliveryTime = "20",
            hasOnlineDelivery = "",
            isDeliveringNow = "",
            featuredImage = "https://images.unsplash.com/photo-1550547660-d9450f8593",
            location = "",
            isVeg = true
        ),

        Restaurant(
            id = 6,
            name = "Tandoori Treats",
            url = "https://images.unsplash.com/photo-1601050690597-df0568f70950",
            cuisines = "North Indian",
            deliveryTime = "32",
            hasOnlineDelivery = "",
            isDeliveringNow = "",
            featuredImage = "https://images.unsplash.com/photo-1550547660-d9450f8593",
            location = "",
            isVeg = false
        ),

        Restaurant(
            id = 7,
            name = "Cafe Delight",
            url = "https://images.unsplash.com/photo-1504674900247-0877df9cc836",
            cuisines = "Cafe, Desserts",
            deliveryTime = "18",
            hasOnlineDelivery = "",
            isDeliveringNow = "",
            featuredImage = "https://images.unsplash.com/photo-1550547660-d9450f8593",
            location = "",
            isVeg = true
        ),

        Restaurant(
            id = 8,
            name = "Chinese Wok",
            url = "https://images.unsplash.com/photo-1605478900064-2c1b7f6a0f13",
            cuisines = "Chinese",
            deliveryTime = "28",
            hasOnlineDelivery = "",
            isDeliveringNow = "",
            featuredImage = "https://images.unsplash.com/photo-1550547660-d9450f8593",
            location = "",
            isVeg = true
        ),

        Restaurant(
            id = 9,
            name = "BBQ Nation",
            url = "https://images.unsplash.com/photo-1558030006-450675393462",
            cuisines = "BBQ, Grill",
            deliveryTime = "38",
            hasOnlineDelivery = "",
            isDeliveringNow = "",
            featuredImage = "https://images.unsplash.com/photo-1550547660-d9450f8593",
            location = "",
            isVeg = false
        ),

        Restaurant(
            id = 10,
            name = "South Spice",
            url = "https://images.unsplash.com/photo-1631452180519-c014fe946bc7",
            cuisines = "South Indian",
            deliveryTime = "22",
            hasOnlineDelivery = "",
            isDeliveringNow = "",
            featuredImage = "https://images.unsplash.com/photo-1550547660-d9450f8593",
            location = "",
            isVeg = true
        )
    )

    val dummyCategories = listOf(
        Category(1, "Pizza", "https://img.icons8.com/color/96/pizza.png"),
        Category(2, "Burger", "https://img.icons8.com/color/96/hamburger.png"),
        Category(3, "Biryani", "https://img.icons8.com/color/96/rice-bowl.png"),
        Category(4, "Desserts", "https://img.icons8.com/color/96/cake.png"),
        Category(5, "Drinks", "https://img.icons8.com/color/96/cocktail.png"),
        Category(6, "Chinese", "https://img.icons8.com/color/96/noodles.png"),
        Category(7, "South Indian", "https://img.icons8.com/color/96/dosa.png"),
        Category(8, "North Indian", "https://img.icons8.com/color/96/curry.png"),
        Category(9, "Drinks", "https://img.icons8.com/color/96/cocktail.png"),
        Category(17, "Chinese", "https://img.icons8.com/color/96/noodles.png"),
        Category(107, "South Indian", "https://img.icons8.com/color/96/dosa.png"),
        Category(18, "North Indian", "https://img.icons8.com/color/96/curry.png")
    )

    val profileMenuItems = listOf(
        ProfileMenuItem(
            title = "My Profile",
            action = ProfileAction.OpenProfile
        ),
        ProfileMenuItem(
            title = "Settings",
            action = ProfileAction.OpenSettings
        ),
        ProfileMenuItem(
            title = "Logout",
            action = ProfileAction.Logout
        )
    )

}

