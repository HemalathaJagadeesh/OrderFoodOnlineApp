package com.android.onlinefoodorderingapp.presentation.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.android.onlinefoodorderingapp.domain.model.Category
import com.android.onlinefoodorderingapp.domain.model.ExploreItem
import com.android.onlinefoodorderingapp.presentation.viewmodel.HomeViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.onlinefoodorderingapp.R
import com.android.onlinefoodorderingapp.domain.model.Restaurant
import com.android.onlinefoodorderingapp.presentation.theme.spacing
import com.android.onlinefoodorderingapp.presentation.util.HomeUiState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.onlinefoodorderingapp.presentation.screens.home.components.ProfileMenuItem
import com.android.onlinefoodorderingapp.presentation.util.HomeUiEvent
import com.android.onlinefoodorderingapp.presentation.util.Routes
import com.android.onlinefoodorderingapp.presentation.util.UiEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    val isVeg = viewModel.isVeg
    val listState = rememberLazyListState()
   /* LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                UiEffect.NavigateToLogin -> {
                    navController.navigate(Routes.LOGIN_SCREEN) {
                        popUpTo(Routes.HOME_SCREEN) { inclusive = true }
                    }
                }

                is UiEffect.NavigateToRestaurantDetails -> {
                    Log.d("NAV_DEBUG", "Navigating now")
                    navController.navigate(Routes.RESTAURANT_DETAILS_SCREEN)
                }
                }


                }
            }*/
    LaunchedEffect(Unit) {
        viewModel.effect.collect {effect ->
            when(effect){
            is UiEffect.NavigateToRestaurantDetails -> {
                Log.d("NAV_DEBUG", "Navigating now")
                navController.navigate(Routes.RESTAURANT_DETAILS)
            }
                UiEffect.NavigateToLogin -> {
                    navController.navigate(Routes.AUTH_GRAPH) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                }
            }
        }
    }


    when (uiState) {
        is HomeUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text("Loading...")
            }
        }

        is HomeUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text((uiState as HomeUiState.Error).message)
            }
        }

        is HomeUiState.Success -> {

            HomeContent(
                uiState = uiState as HomeUiState.Success,
                onSearchChange = viewModel::onSearchChange,
                onSearchSubmit = viewModel::onSearchSubmit,
                isVeg = viewModel.isVeg,
                onVegToggleChange = { viewModel.onVegToggleChanged(it) },
                onCategoryClick = viewModel::selectTab,
                onRestaurantClick = {viewModel.onEvent(HomeUiEvent.OnTopRestaurantsClick(it))},
                onProfileMenuAction = { viewModel.onEvent(HomeUiEvent.OnProfileMenuClick(it)) },
                listState = listState
            )

        }

    }


}

@Composable
fun HomeContent(
    uiState: HomeUiState.Success,
    onSearchChange: (String) -> Unit,
    onSearchSubmit: () -> Unit,
    isVeg: Boolean,
    onVegToggleChange: (Boolean) -> Unit,
    onCategoryClick: (Category) -> Unit,
    onRestaurantClick: (Restaurant) -> Unit,
    onProfileMenuAction: (ProfileAction) -> Unit,
    listState: LazyListState

) {

   /* Box {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {

           *//* item {
                // HeroBanner()
                CollapsingHeroBanner(listState)
            }*//*
            item {
                SearchBarWithVegToggle(
                    isVeg = isVeg,
                    onVegToggleChange = onVegToggleChange,
                    onSearchClick = {
                        // navigate later
                    }
                )
            }

            item {
                FlashSaleBanner()
            }
            //CATEGORIES
            item {
                SectionTitle("WHAT'S ON YOUR MIND?")
                *//* FoodCategoryRow(
                    uiState.categories,
                    onCategoryClick = onCategoryClick)*//*
                FoodCategoryGrid(
                    categories = uiState.categories,
                    onCategoryClick = onCategoryClick
                )
            }
            // Explore
            item {
                SectionTitle("EXPLORE")
                ExploreRow(
                    items = uiState.exploreItems,
                    onItemClick = {}
                )
            }

            //
            item {
                SectionTitle("TOP RESTAURANTS DELIVERING TO YOU")
            }
            items(uiState.restaurants) { restaurant ->
                RestaurantCard(
                    restaurant = restaurant,
                    onClick = { onRestaurantClick(restaurant) }
                )

            }

            //EXPLORE
            *//* item {
                 SectionTitle("EXPLORE")
                 ExploreRow(items = uiState.exploreItems, onItemClick = {})

             }*//*
        }
        CollapsingHeroBanner(listState)

        // 🔝 TOP BAR
       // HomeTopBar()
    }*/


    val collapseFraction = when {
        listState.firstVisibleItemIndex > 0 -> 1f
        else -> (listState.firstVisibleItemScrollOffset / 600f).coerceIn(0f, 1f)
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // ✅ SCROLL CONTENT
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(top = 260.dp),
            modifier = Modifier.fillMaxSize()
        ) {

            item { Spacer(modifier = Modifier.height(MaterialTheme.spacing.small)) }
            item { FlashSaleBanner() }

           /*
            item { CategoriesSection() }

            items(20) {
                RestaurantItemDummy()
            }*/
            //CATEGORIES
            item {
                SectionTitle("WHAT'S ON YOUR MIND?")
              /* FoodCategoryRow(
                uiState.categories,
                onCategoryClick = onCategoryClick)*/
                FoodCategoryGrid(
                    categories = uiState.categories,
                    onCategoryClick = onCategoryClick
                )
            }
            // Explore
            item {
                SectionTitle("EXPLORE")
                ExploreRow(
                    items = uiState.exploreItems,
                    onItemClick = {}
                )
            }

            //
            item {
                SectionTitle("TOP RESTAURANTS DELIVERING TO YOU")
            }
            items(uiState.restaurants) { restaurant ->
                RestaurantCard(
                    restaurant = restaurant,
                    onClick = { onRestaurantClick(restaurant) }
                )

            }

            //EXPLORE
            item {
            SectionTitle("EXPLORE")
            ExploreRow(items = uiState.exploreItems, onItemClick = {})

        }
    }


        // 🎯 COLLAPSING BANNER
        CollapsingBanner(collapseFraction,onProfileMenuAction = onProfileMenuAction)

        // 🔍 SEARCH BAR (animated + sticky)
       // AnimatedSearchBar(collapseFraction)

        // 🔝 TOP BAR
       // HomeTopBar(collapseFraction)
    }
}


@Composable
fun FoodCategoryGrid(
    categories: List<Category>,
    onCategoryClick: (Category) -> Unit
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.medium)
    ) {
        items(categories) { category ->
            FoodCategoryItem(
                category = category,
                onClick = { onCategoryClick(category) }
            )
        }
    }
}


@Composable
fun ExploreRow(items: List<ExploreItem>, onItemClick: (ExploreItem) -> Unit = {}) {
    LazyRow {

        items(items = items) { item ->
            ExploreItemCard(item = item, onClick = { onItemClick(item) })
        }

    }

}

@Composable
fun ExploreItemCard(item: ExploreItem, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .padding(horizontal = MaterialTheme.spacing.small)
            .clip(RoundedCornerShape(MaterialTheme.spacing.medium))
            .background(Color.White)
            .border(
                width = 1.dp,
                color = Color(0xFFE0E0E0),
                shape = RoundedCornerShape(MaterialTheme.spacing.medium)
            )
            .clickable { onClick() }
            .padding(
                MaterialTheme.spacing.medium
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = "https://img.freepik.com/premium-vector/flash-sale-discount-promotion-banner_603380-265.jpg?w=2000",
            contentDescription = item.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(MaterialTheme.spacing.large)
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        Text(
            text = item.title,
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
    }

}

@Composable
fun FoodCategoryRow(categories: List<Category>, onCategoryClick: (Category) -> Unit = {}) {
    LazyRow {
        items(
            items = categories, key = { it.id }) { category ->
            FoodCategoryItem(category, onClick = { onCategoryClick(category) })
        }
    }

}

@Composable
fun FoodCategoryItem(category: Category, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(80.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = category.imageUrl,
            contentDescription = category.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = category.name,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
        )
    }

}

@Composable
fun SectionTitle(sectionTitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = MaterialTheme.spacing.small,
                vertical = MaterialTheme.spacing.xLarge
            ), verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
        )
        Text(
            text = sectionTitle,
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.xLarge),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
        )
    }

}

@Composable
fun FlashSaleBanner() {
    Column {
        Text(
            text = "Offer Expires in 09:30:35",
            modifier = Modifier
                .align(Alignment.End)
                .padding(5.dp),
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        Card(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.small)
                .fillMaxWidth()
                .height(130.dp),
            shape = RoundedCornerShape(MaterialTheme.spacing.medium)
        ) {
            /**//* Image(
                 painter = painterResource(R.drawable.ic_launcher_background),
                 contentDescription = null,
                 contentScale = ContentScale.Crop,
                 modifier = Modifier.fillMaxSize()
             )*/
            val flashSaleUrl = stringResource(id = R.string.flash_sale_image_url)
            AsyncImage(
                model = flashSaleUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                placeholder = painterResource(id = R.drawable.ic_launcher_background)
            )
        }
    }
}

@Composable
fun HeroBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        AsyncImage(
            model = "https://img.freepik.com/free-vector/food-delivery-service-fast-food-delivery-scooter-delivery-service-illustration_67394-871.jpg?w=2000",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(MaterialTheme.spacing.medium)
        ) {
            Text("Your Cravings,\nDelivered Fresh")
            Button(onClick = {}) {
                Text("Order Now")
            }
        }
    }
    /*Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        shape = RoundedCornerShape(bottomStart = MaterialTheme.spacing.large, bottomEnd = MaterialTheme.spacing.large)
    ) {
        Box(
            modifier = Modifier.background(
                color = Color.Yellow, shape = RoundedCornerShape(MaterialTheme.spacing.large)
            )
        ) {

            AsyncImage(
                model = "https://img.freepik.com/free-vector/food-delivery-service-fast-food-delivery-scooter-delivery-service-illustration_67394-871.jpg?w=2000",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(modifier = Modifier.align(Alignment.CenterStart)) {
                Text(
                    text = "Your Cravings,\\nDelivered Fresh",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(Color.Red),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(10.dp)
            ) {
                Text("Order Now")
            }
        }
    }*/
}

@Composable
fun HeaderSection(
    location: String,
    searchQuery: String,
    isVegMode: Boolean,
    onLocationClick: () -> Unit,
    onSearchChange: (String) -> Unit,
    onSearchSubmit: () -> Unit,
    onVegToggle: () -> Unit,
) {

    Column {
        LocationHeader(location, onLocationClick)
        SearchBarRow(
            query = searchQuery,
            isVegMode = isVegMode,
            onQueryChange = onSearchChange,
            onSearch = onSearchSubmit,
            onVegToggle = onVegToggle
        )
    }
}

@Composable
fun SearchBarRow(
    query: String,
    isVegMode: Boolean,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onVegToggle: () -> Unit
) {

}

@Composable
fun LocationHeader(location: String, onLocationClick: () -> Unit) {

}

@Composable
fun RestaurantCard(
    restaurant: Restaurant,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(
                horizontal = MaterialTheme.spacing.medium,
                vertical = MaterialTheme.spacing.small
            )
            .fillMaxWidth(),
        shape = RoundedCornerShape(MaterialTheme.spacing.medium),
        onClick = onClick
    ) {
        Column {
            AsyncImage(
                model = restaurant.url,
                contentDescription = restaurant.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth(),

            )

            Column(modifier = Modifier.padding(MaterialTheme.spacing.medium)) {
                Text(
                    text = restaurant.name,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = restaurant.cuisines,
                    style = MaterialTheme.typography.bodySmall
                )

                Row {
                   // Text("⭐ ${restaurant.rating}")
                   // Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                    Text("${restaurant.deliveryTime} mins")
                }
            }
        }
    }
}


private val previewCategories = listOf(
    Category(id = 1, name = "Pizza", imageUrl = ""),
    Category(id = 2, name = "Burger", imageUrl = ""),
    Category(id = 3, name = "Biryani", imageUrl = ""),
    Category(id = 4, name = "Desserts", imageUrl = "")
)


private val previewExploreItems = listOf(
    ExploreItem(id = 1, title = "Offers", iconUrl = "", type = ""),
    ExploreItem(id = 2, title = "Top Picks", iconUrl = "", type = ""),
)

val dummyRestaurants = listOf(

    Restaurant(
        id = 1,
        name = "Burger House",
        url = "https://images.unsplash.com/photo-1550547660-d9450f859349",
        cuisines = "Burgers, Fast Food",
        deliveryTime = "25",
        has_online_delivery = "",
        is_delivering_now = "",
        featured_image = "https://images.unsplash.com/photo-1550547660-d9450f8593",
        location = ""
    ),

    Restaurant(
        id = 2,
        name = "Pizza Palace",
        url = "https://images.unsplash.com/photo-1600891964599-f61ba0e24092",
        cuisines = "Pizza, Italian",
        deliveryTime = "30",
        has_online_delivery = "",
        is_delivering_now = "",
        featured_image = "https://images.unsplash.com/photo-1550547660-d9450f8593",
        location = ""
    ),

    Restaurant(
        id = 3,
        name = "Spice Kitchen",
        url = "https://images.unsplash.com/photo-1604908176997-125f25cc6f3d",
        cuisines = "Indian, Biryani",
        deliveryTime = "35",
        has_online_delivery = "",
        is_delivering_now = "",
        featured_image = "https://images.unsplash.com/photo-1550547660-d9450f8593",
        location = ""
    ),

    Restaurant(
        id = 4,
        name = "Sushi World",
        url = "https://images.unsplash.com/photo-1562158070-57b2b2c2b6e3",
        cuisines = "Sushi, Japanese",
        deliveryTime = "40",
        has_online_delivery = "",
        is_delivering_now = "",
        featured_image = "https://images.unsplash.com/photo-1550547660-d9450f8593",
        location = ""
    ),

    Restaurant(
        id = 5,
        name = "Healthy Bites",
        url = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c",
        cuisines = "Salads, Healthy",
        deliveryTime = "20",
        has_online_delivery = "",
        is_delivering_now = "",
        featured_image = "https://images.unsplash.com/photo-1550547660-d9450f8593",
        location = ""
    ),

    Restaurant(
        id = 6,
        name = "Tandoori Treats",
        url = "https://images.unsplash.com/photo-1601050690597-df0568f70950",
        cuisines = "North Indian",
        deliveryTime = "32",
        has_online_delivery = "",
        is_delivering_now = "",
        featured_image = "https://images.unsplash.com/photo-1550547660-d9450f8593",
        location = ""
    ),

    Restaurant(
        id = 7,
        name = "Cafe Delight",
        url = "https://images.unsplash.com/photo-1504674900247-0877df9cc836",
        cuisines = "Cafe, Desserts",
        deliveryTime = "18",
        has_online_delivery = "",
        is_delivering_now = "",
        featured_image = "https://images.unsplash.com/photo-1550547660-d9450f8593",
        location = ""
    ),

    Restaurant(
        id = 8,
        name = "Chinese Wok",
        url = "https://images.unsplash.com/photo-1605478900064-2c1b7f6a0f13",
        cuisines = "Chinese",
        deliveryTime = "28",
        has_online_delivery = "",
        is_delivering_now = "",
        featured_image = "https://images.unsplash.com/photo-1550547660-d9450f8593",
        location = ""
    ),

    Restaurant(
        id = 9,
        name = "BBQ Nation",
        url = "https://images.unsplash.com/photo-1558030006-450675393462",
        cuisines = "BBQ, Grill",
        deliveryTime = "38",
        has_online_delivery = "",
        is_delivering_now = "",
        featured_image = "https://images.unsplash.com/photo-1550547660-d9450f8593",
        location = ""
    ),

    Restaurant(
        id = 10,
        name = "South Spice",
        url = "https://images.unsplash.com/photo-1631452180519-c014fe946bc7",
        cuisines = "South Indian",
        deliveryTime = "22",
        has_online_delivery = "",
        is_delivering_now = "",
        featured_image = "https://images.unsplash.com/photo-1550547660-d9450f8593",
        location = ""
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


@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {

    val previewUiState = HomeUiState.Success(
        location = "Bangalore",
        searchQuery = "",
        isVegMode = false,
        categories = listOf(
            Category(1, "Pizza", ""),
            Category(2, "Burger", ""),
            Category(3, "Biryani", "")
        ),
        exploreItems = previewExploreItems,
        restaurants = dummyRestaurants
    )

    MaterialTheme {
        HomeContent (
            uiState = previewUiState,
            onSearchChange = {},
            onSearchSubmit = {},
            isVeg = true,
            onVegToggleChange = {},
            onCategoryClick = {},
            onRestaurantClick = {},
            listState = LazyListState(),
            onProfileMenuAction = {  }
        )
    }



}

@Composable
fun CollapsingHeroBanner(listState: LazyListState) {

    val maxHeight = 220.dp
    val minHeight = 80.dp

    val collapseFraction = (listState.firstVisibleItemScrollOffset / 600f)
        .coerceIn(0f, 1f)

    val height = maxHeight - (maxHeight - minHeight) * collapseFraction

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(Color.Black)
    ) {

        // Background image
        AsyncImage(
            model = "YOUR_IMAGE_URL",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        // Gradient overlay (Zomato feel)
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black)
                    )
                )
        )

        // Text content
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text("Your Cravings,", color = Color.White)
            Text("Delivered Fresh", color = Color.White)
        }
    }
}
@Composable
fun SearchBarWithVegToggle(
    isVeg: Boolean,
    onVegToggleChange: (Boolean) -> Unit,
    onSearchClick: () -> Unit = {} // 👈 optional for navigation
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // 🔍 SEARCH BAR
        Row(
            modifier = Modifier
                .weight(1f)
                .height(52.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Color(0xFFF5F5F5))
                .clickable { onSearchClick() } // 👈 clickable
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = Color.Gray
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Search for dishes, restaurants...",
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // 🌱 VEG TOGGLE (styled)
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(
                    if (isVeg) Color(0xFFE8F5E9) else Color(0xFFF0F0F0)
                )
                .padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "VEG",
                color = if (isVeg) Color(0xFF2E7D32) else Color.Gray,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.width(6.dp))

            Switch(
                checked = isVeg,
                onCheckedChange = onVegToggleChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color(0xFF2E7D32),
                    checkedTrackColor = Color(0xFFA5D6A7)
                )
            )
        }
    }
}

@Composable
fun CollapsingBanner(collapseFraction: Float,onProfileMenuAction: (ProfileAction) -> Unit) {


    val maxHeight = MaterialTheme.spacing.heroBannerHeightMax
    val minHeight = MaterialTheme.spacing.heroBannerHeightMin

    val height = maxHeight - (maxHeight - minHeight) * collapseFraction

    val imageOffset = (-40 * collapseFraction).dp

    // 🔥 Address moves UP & disappears
    val addressOffsetY = (-60 * collapseFraction).dp
    val addressAlpha = 1f - collapseFraction * 1.2f

    // 🔥 Search becomes sticky
    val searchStartY = 100.dp
    val searchEndY = 10.dp
    val searchOffsetY = searchStartY - (searchStartY - searchEndY) * collapseFraction

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
    ) {

        // 🌄 Banner Image
        AsyncImage(
            model = "https://img.freepik.com/free-vector/food-delivery-service-fast-food-delivery-scooter-delivery-service-illustration_67394-871.jpg?w=2000",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // 🌑 Gradient overlay
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.6f)
                        )
                    )
                )
        )

        //ADDRESS (moves up & disappears)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .offset(y = addressOffsetY)
                .alpha(addressAlpha)
                .padding(horizontal = MaterialTheme.spacing.medium, vertical = MaterialTheme.spacing.small),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                Text("Ludhiana Bus Stop", color = Color.White)
                Text(
                    "Welcome Guest, Set Pickup Location",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp
                )
            }

          // ProfileSection(onLogoutClick = {})

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

            ProfileMenu(menuItems = profileMenuItems , onItemClick = {action -> onProfileMenuAction(action)})


            /*Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .clickable {  }
            ){
                AsyncImage(
                    model = R.drawable.ic_launcher_background,
                    contentDescription = "Profile Icon",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()

                )
            }*/
        }

        // 🔍 SEARCH + TOGGLE (becomes top bar)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = searchOffsetY)
                .padding(horizontal = MaterialTheme.spacing.medium),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(
                if (collapseFraction > 0.9f) MaterialTheme.spacing.small else MaterialTheme.spacing.zero
            )
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .background(Color.White)
                    .padding(horizontal = MaterialTheme.spacing.small),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(Icons.Default.Search, contentDescription = null)

                Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))

                Text(stringResource(R.string.search_for_food), color = Color.Gray)

                Spacer(modifier = Modifier.weight(1f))

                Text(stringResource(R.string.veg), fontWeight = FontWeight.Bold)

                Switch(
                    checked = false,
                    onCheckedChange = {}
                )
            }
        }
    }
}

@Composable
fun ProfileMenu(
    menuItems: List<ProfileMenuItem>,
    onItemClick: (ProfileAction) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        AsyncImage(
            model = "https://api.dicebear.com/7.x/avataaars/png?seed=User",
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .clickable { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            menuItems.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item.title) },
                    onClick = {
                        expanded = false
                        onItemClick(item.action)
                    }
                )
            }
        }
    }
}
