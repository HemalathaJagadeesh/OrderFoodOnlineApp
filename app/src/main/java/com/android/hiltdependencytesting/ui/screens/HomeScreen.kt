package com.android.hiltdependencytesting.ui.screens

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.android.hiltdependencytesting.domain.model.Category
import com.android.hiltdependencytesting.domain.model.ExploreItem
import com.android.hiltdependencytesting.ui.viewmodel.HomeViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.hiltdependencytesting.R
import com.android.hiltdependencytesting.domain.model.Restaurant
import com.android.hiltdependencytesting.ui.theme.LocalSpacing
import com.android.hiltdependencytesting.ui.theme.spacing
import com.android.hiltdependencytesting.ui.util.HomeUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val uiState by viewModel.uiState.collectAsState()

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
           /* HomeContent(uiState = uiState as HomeUiState.Success,
                viewModel = viewModel)*/

            HomeContent(
                uiState = uiState as HomeUiState.Success,
                onSearchChange = viewModel::onSearchChange,
                onSearchSubmit = viewModel::onSearchSubmit,
                onVegToggle = viewModel::toggleVegMode,
                onCategoryClick = viewModel::selectTab,
                onRestaurantClick = {}
            )

        }

    }


}

@Composable
fun HomeContent(
    uiState: HomeUiState.Success,
    onSearchChange: (String) -> Unit,
    onSearchSubmit: () -> Unit,
    onVegToggle: () -> Unit,
    onCategoryClick: (Category) -> Unit,
    onRestaurantClick: (Restaurant) -> Unit

) {
    Scaffold(
        bottomBar = { BottomBar() }) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            contentPadding = PaddingValues(bottom = MaterialTheme.spacing.medium)
        ) {

            //Header
            item {
                HeaderSection(
                    location = uiState.location,
                    searchQuery = uiState.searchQuery,
                    isVegMode = uiState.isVegMode,
                    onLocationClick = { /* Handle location click */ },
                    onSearchChange = onSearchChange,
                    onSearchSubmit = onSearchSubmit,
                    onVegToggle = onVegToggle,

                    )
            }

            item {
                HeroBanner()
            }

            item {
                FlashSaleBanner()
            }
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
                    onClick = {onRestaurantClick(restaurant)}
                )

            }

            //EXPLORE
            /* item {
                 SectionTitle("EXPLORE")
                 ExploreRow(items = uiState.exploreItems, onItemClick = {})

             }*/
        }
    }
}


@Composable
fun FoodCategoryGrid(
    categories: List<Category>,
    onCategoryClick: (Category) -> Unit
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2), // 🔥 THIS MAKES 2 ROWS
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(horizontal = 5.dp), // important!
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

        items(items = items, key = { it.id }) { item ->
            ExploreItemCard(item = item, onClick = { onItemClick(item) })
        }

    }

}

@Composable
fun ExploreItemCard(item: ExploreItem, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(80.dp)
            .clip(RoundedCornerShape(MaterialTheme.spacing.medium))
            .background(Color.White)
            .border(
                width = 1.dp, color = Color(0xFFE0E0E0), shape = RoundedCornerShape(MaterialTheme.spacing.medium)
            )
            .clickable { onClick() }
            .padding(vertical = MaterialTheme.spacing.small, horizontal = MaterialTheme.spacing.small),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = "https://4d7706ee.isolation.zscaler.com/profile/24b8ab0a-59a4-42c6-bc94-23342a75840c/zia-session/?tenant=ce753994dc24&region=hyd&controls_id=36f46a16-181d-4693-b132-784d97e54670&user=99f81287c0e2b51801d549810ae386822c50affae7487cac16efe77cb3b6247d&original_url=https%3A%2F%2Fchatgpt.com%2Fimages%2F&key=sh-1&hmac=f4e15a9ac42822515ec8b12217c50a86e7b5df38e2e31d92652dfda206df7c2c",
            contentDescription = item.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(MaterialTheme.spacing.large)
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        Text(
            text = item.title,
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1,
            textAlign = TextAlign.Center
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
            textAlign = TextAlign.Center
        )
    }

}

@Composable
fun SectionTitle(sectionTitle: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = MaterialTheme.spacing.medium), verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
        )
        Text(
            text = sectionTitle,
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
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
            shape = RoundedCornerShape(MaterialTheme.spacing.extraLarge)
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
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun HeroBanner() {
    Card(
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
    }
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
            .padding(horizontal = MaterialTheme.spacing.medium, vertical = MaterialTheme.spacing.small)
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(MaterialTheme.spacing.medium)
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


@Composable
fun BottomBar() {
    NavigationBar {
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = { Text("🏠") },
            label = { Text("Delivery") })
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Text("🔍") },
            label = { Text("Dining") })
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

/*private val previewUiState = HomeUiState.Success(
    location = "Bangalore", searchQuery = "", isVegMode = false, categories = previewCategories,
    exploreItems = previewExploreItems, restaurants = dummyRestaurants
)*/

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
    Category(8, "North Indian", "https://img.icons8.com/color/96/curry.png")
)

val dummyExploreItems = listOf(
    ExploreItem(
        1,
        "Top Rated",
        "https://images.unsplash.com/photo-1551218808-94e220e084d2",
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
    )
)


@Preview(showBackground = true, device = Devices.PIXEL_7)
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
            onVegToggle = {},
            onCategoryClick = {},
            onRestaurantClick = {})
    }



}



