package com.android.onlinefoodorderingapp.presentation.screens.home


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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.android.onlinefoodorderingapp.data.local.DummyData.dummyRestaurants
import com.android.onlinefoodorderingapp.data.local.DummyData.profileMenuItems
import com.android.onlinefoodorderingapp.presentation.screens.home.components.ProfileMenuItem
import com.android.onlinefoodorderingapp.presentation.util.AppConstants
import com.android.onlinefoodorderingapp.presentation.util.HomeUiEvent
import com.android.onlinefoodorderingapp.presentation.util.Routes
import com.android.onlinefoodorderingapp.presentation.util.UiEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController, viewModel: HomeViewModel = hiltViewModel()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val uiState by viewModel.uiState.collectAsState()

    val isVeg by viewModel.isVegModeState.collectAsState()



    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is UiEffect.NavigateToRestaurantDetails -> {
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

    LaunchedEffect(backStackEntry) {
        if (backStackEntry?.destination?.route == Routes.HOME) {
            viewModel.resetHomeState()
        }
    }


    when (uiState) {
        is HomeUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text(stringResource(R.string.loading))
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
                isVeg = isVeg,
                onCategoryClick = viewModel::selectTab,
                onRestaurantClick = { viewModel.onEvent(HomeUiEvent.OnTopRestaurantsClick(it)) },
                onProfileMenuAction = { viewModel.onEvent(HomeUiEvent.OnProfileMenuClick(it)) },
                viewModel = viewModel
            )

        }

    }


}

@Composable
fun HomeContent(
    uiState: HomeUiState.Success,
    isVeg: Boolean,
    onCategoryClick: (Category) -> Unit,
    onRestaurantClick: (Restaurant) -> Unit,
    onProfileMenuAction: (ProfileAction) -> Unit,
    viewModel: HomeViewModel

) {

    val listState = remember(uiState.searchQuery.isBlank()) {
        LazyListState()
    }

    val collapseFraction = when {
        listState.firstVisibleItemIndex > 0 -> 1f
        else -> (listState.firstVisibleItemScrollOffset / 600f).coerceIn(0f, 1f)
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // SCROLL CONTENT
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(top = MaterialTheme.spacing.heroBannerHeightMax),
            modifier = Modifier.fillMaxSize()
        ) {

            if (uiState.searchQuery.isNotBlank()) {
                // SEARCH MODE
                if (uiState.restaurants.isEmpty()) {
                    item {
                        Text(
                            stringResource(R.string.no_results_found),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(MaterialTheme.spacing.large),
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    items(uiState.restaurants) { restaurant ->
                        RestaurantCard(
                            restaurant = restaurant, onClick = { onRestaurantClick(restaurant) })
                    }
                }

            } else {
                //Normal Mode
                item { Spacer(modifier = Modifier.height(MaterialTheme.spacing.small)) }
                item { FlashSaleBanner() }

                //CATEGORIES
                item {
                    SectionTitle(stringResource(R.string.title_whats_on_your_mind))/* FoodCategoryRow(
                  uiState.categories,
                  onCategoryClick = onCategoryClick)*/
                    FoodCategoryGrid(
                        categories = uiState.categories, onCategoryClick = onCategoryClick
                    )
                }
                // Explore
                item {
                    SectionTitle(stringResource(R.string.title_explore))
                    ExploreRow(
                        items = uiState.exploreItems, onItemClick = {})
                }

                //
                item {
                    SectionTitle(stringResource(R.string.title_top_restaurants_delvr_to_you))
                }
                items(uiState.restaurants) { restaurant ->
                    RestaurantCard(
                        restaurant = restaurant, onClick = { onRestaurantClick(restaurant) })

                }
            }

            //EXPLORE
            /* item {
             SectionTitle(stringResource(R.string.title_explore))
             ExploreRow(items = uiState.exploreItems, onItemClick = {})

         }*/
        }


        //  COLLAPSING BANNER
        CollapsingBanner(
            collapseFraction, onProfileMenuAction = onProfileMenuAction, isVeg, viewModel
        )

    }
}


@Composable
fun FoodCategoryGrid(
    categories: List<Category>, onCategoryClick: (Category) -> Unit
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(AppConstants.COUNT_2),
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.medium)
    ) {
        items(categories) { category ->
            FoodCategoryItem(
                category = category, onClick = { onCategoryClick(category) })
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
            .width(MaterialTheme.spacing.spacing100)
            .padding(horizontal = MaterialTheme.spacing.small)
            .clip(MaterialTheme.shapes.medium)
            .background(Color.White)
            .border(
                width = MaterialTheme.spacing.border,
                color = Color(0xFFE0E0E0),
                shape = MaterialTheme.shapes.medium
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
            maxLines = AppConstants.VALUE_ONE,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
    }

}

@Composable
fun FoodCategoryItem(category: Category, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(MaterialTheme.spacing.categoryHeight)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = category.imageUrl,
            contentDescription = category.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(MaterialTheme.spacing.foodCategoryImageSize)
                .clip(CircleShape)
                .background(Color.LightGray)
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        Text(
            text = category.name,
            style = MaterialTheme.typography.bodySmall,
            maxLines = AppConstants.VALUE_ONE,
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
                horizontal = MaterialTheme.spacing.small, vertical = MaterialTheme.spacing.xLarge
            ), verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = MaterialTheme.spacing.border,
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
            thickness = MaterialTheme.spacing.border,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
        )
    }

}

@Composable
fun FlashSaleBanner() {
    Column {
        Text(
            text = stringResource(R.string.offer_expiry_timestamp),
            modifier = Modifier
                .align(Alignment.End)
                .padding(MaterialTheme.spacing.xSmall),
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        Card(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.small)
                .fillMaxWidth()
                .height(MaterialTheme.spacing.flashBannerHeight),
            shape = MaterialTheme.shapes.medium
        ) {
            val flashSaleUrl = stringResource(id = R.string.flash_sale_image_url)
            AsyncImage(
                model = flashSaleUrl,
                contentDescription = stringResource(R.string.desc_flash_sale_banner),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                placeholder = painterResource(id = R.drawable.ic_launcher_background)
            )
        }
    }
}


@Composable
fun RestaurantCard(
    restaurant: Restaurant, onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(
                horizontal = MaterialTheme.spacing.medium, vertical = MaterialTheme.spacing.small
            )
            .fillMaxWidth(), shape = MaterialTheme.shapes.medium, onClick = onClick
    ) {
        Column {
            AsyncImage(
                model = restaurant.url,
                contentDescription = restaurant.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(MaterialTheme.spacing.restaurantCardHeight)
                    .fillMaxWidth(),

                )

            Column(modifier = Modifier.padding(MaterialTheme.spacing.medium)) {
                Text(
                    text = restaurant.name, style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = restaurant.cuisines, style = MaterialTheme.typography.bodySmall
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


private val previewExploreItems = listOf(
    ExploreItem(id = 1, title = "Offers", iconUrl = "", type = ""),
    ExploreItem(id = 2, title = "Top Picks", iconUrl = "", type = ""),
)


@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {

    val previewUiState = HomeUiState.Success(
        location = "Bangalore", searchQuery = "", isVegMode = false, categories = listOf(
            Category(1, "Pizza", ""), Category(2, "Burger", ""), Category(3, "Biryani", "")
        ), exploreItems = previewExploreItems, restaurants = dummyRestaurants
    )

    MaterialTheme {
        /* HomeContent (
             uiState = previewUiState,
             onSearchChange = {},
             onSearchSubmit = {},
             isVeg = true,
             onVegToggleChange = {},
             onCategoryClick = {},
             onRestaurantClick = {},
             listState = LazyListState(),
             onProfileMenuAction = {  },

         )*/
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingBanner(
    collapseFraction: Float,
    onProfileMenuAction: (ProfileAction) -> Unit,
    isVeg: Boolean,
    viewModel: HomeViewModel
) {


    val maxHeight = MaterialTheme.spacing.heroBannerHeightMax
    val minHeight = MaterialTheme.spacing.heroBannerHeightMin

    val height = maxHeight - (maxHeight - minHeight) * collapseFraction

    val imageOffset = (-40 * collapseFraction).dp

    //Address moves UP & disappears
    val addressOffsetY = (-60 * collapseFraction).dp
    val addressAlpha = 1f - collapseFraction * 1.2f

    //Search becomes sticky
    val searchStartY = 100.dp
    val searchEndY = 10.dp
    val searchOffsetY = searchStartY - (searchStartY - searchEndY) * collapseFraction

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
    ) {

        //Banner Image
        AsyncImage(
            model = "https://img.freepik.com/free-vector/food-delivery-service-fast-food-delivery-scooter-delivery-service-illustration_67394-871.jpg?w=2000",
            contentDescription = stringResource(R.string.desc_hero_banner),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        //Gradient overlay
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Transparent, Color.Black.copy(alpha = 0.6f)
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
                .padding(
                    horizontal = MaterialTheme.spacing.medium,
                    vertical = MaterialTheme.spacing.small
                ), horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                Text("Ludhiana Bus Stop", color = Color.White)
                Text(
                    "Welcome Guest, Set Pickup Location",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp
                )
            }

            ProfileMenu(
                menuItems = profileMenuItems,
                onItemClick = { action -> onProfileMenuAction(action) })

        }

        // SEARCH + TOGGLE (top bar)
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

                // Text(stringResource(R.string.search_for_food), color = Color.Gray)

                TextField(
                    value = viewModel.uiState.collectAsState().value.let { state ->
                        if (state is HomeUiState.Success) state.searchQuery else ""
                    },
                    onValueChange = { viewModel.onSearchChange(it) },
                    placeholder = {
                        Text(stringResource(R.string.search_for_food))
                    },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = { viewModel.onSearchSubmit() }))


                Spacer(modifier = Modifier.weight(1f))

                Text(
                    stringResource(R.string.veg),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = MaterialTheme.spacing.xSmall)
                )

                Switch(
                    checked = isVeg,
                    onCheckedChange = { viewModel.onVegToggleChanged(it) },
                )
            }
        }
    }
}

@Composable
fun ProfileMenu(
    menuItems: List<ProfileMenuItem>, onItemClick: (ProfileAction) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        AsyncImage(
            model = "https://api.dicebear.com/7.x/personas/png?seed=fooduser",
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .clickable { expanded = true })

        DropdownMenu(
            expanded = expanded, onDismissRequest = { expanded = false }) {
            menuItems.forEach { item ->
                DropdownMenuItem(text = { Text(item.title) }, onClick = {
                    expanded = false
                    onItemClick(item.action)
                })
            }
        }
    }
}
