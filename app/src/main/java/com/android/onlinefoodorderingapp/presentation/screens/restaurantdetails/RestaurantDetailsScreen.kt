package com.android.onlinefoodorderingapp.presentation.screens.restaurantdetails

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.android.onlinefoodorderingapp.presentation.viewmodel.RestaurantDetailViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.onlinefoodorderingapp.R
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import com.android.onlinefoodorderingapp.presentation.theme.spacing
import com.android.onlinefoodorderingapp.presentation.util.AppConstants
import com.android.onlinefoodorderingapp.presentation.util.FoodFilter
import com.android.onlinefoodorderingapp.presentation.util.RestaurantDetailUiState


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RestaurantDetailsScreen(
    restaurantId: String?,
    navController: NavController,
    viewModel: RestaurantDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.loadData(restaurantId ?: AppConstants.EMPTY_STRING)
    }
    val state by viewModel.state.collectAsState()
    val scrollState = rememberLazyListState()

    val isCollapsed by remember {
        derivedStateOf { scrollState.firstVisibleItemScrollOffset > 200 }
    }


    Box {
        LazyColumn(
            state = scrollState,
            contentPadding = PaddingValues(bottom = MaterialTheme.spacing.spacing120)
        ) {

            item {
                HeroSection(isCollapsed)
            }

            item {
                RestaurantInfoSection(state,viewModel)
            }

            item {
                RecommendedSection()
            }

            items(
                items = state.foodItem,
                key = { it.id }
            ) { foodItem ->
                FoodItemCard(
                    item = foodItem,
                    onItemClick = {
                        navController.navigate("food_details/${foodItem.id}")
                    },
                    onAddClick = {
                        viewModel.onAddItemClick()
                    }
                )
            }
        }
    }
}

/* ----------------------------- Hero ----------------------------- */

@Composable
fun HeroSection(isCollapsed: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(MaterialTheme.spacing.heroBannerHeightMax)
    ) {
        AsyncImage(
            model = "https://images.unsplash.com/photo-1550547660-d9450f859349",
            contentDescription = stringResource(R.string.desc_hero_banner),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

/* ----------------------- Restaurant Info ------------------------ */

@Composable
fun RestaurantInfoSection(state: RestaurantDetailUiState, viewModel: RestaurantDetailViewModel) {
    Column {
        RestaurantMetaRow()
        Spacer(Modifier.height(MaterialTheme.spacing.small))

        FilterRow(selectedFilter = state.selectedFilter,
            onFilterClick = viewModel::onFilterSelected
        )

        Spacer(Modifier.height(MaterialTheme.spacing.small))
    }
}

@Composable
fun RestaurantMetaRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = MaterialTheme.spacing.medium,
                vertical = MaterialTheme.spacing.small
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MetaItem("50% OFF", Color(0xFF4CAF50))
        MetaItem("⭐ 4.2", Color(0xFFFFC107))
        MetaItem("35 mins", Color(0xFF4CAF50))
    }
}

@Composable
fun MetaItem(text: String, color: Color = Color.Black) {
    Text(
        text = text,
        color = color,
        fontWeight = FontWeight.Medium
    )
}

/* --------------------------- Filters ---------------------------- */

@Composable
fun FilterRow(
    selectedFilter: FoodFilter,
    onFilterClick: (FoodFilter) -> Unit
) {
    val filters = listOf(
        FoodFilter.ALL to "Filter",
        FoodFilter.VEG to "Veg",
        FoodFilter.NON_VEG to "Non-Veg",
        FoodFilter.SPICY to "Spicy"
    )


    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = MaterialTheme.spacing.medium),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        filters.forEach { (filter, label) ->
            FilterChip(
                text = label,
                selected = selectedFilter == filter,
                onClick = { onFilterClick(filter) }
            )

        }
    }
}

@Composable
fun FilterChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .border(MaterialTheme.spacing.border, Color(0xFF4CAF50), MaterialTheme.shapes.small)
            .background(if (selected) Color.Green else Color.Transparent)
            .clickable { onClick() }
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            modifier = Modifier.padding(
                horizontal = MaterialTheme.spacing.medium,
                vertical = MaterialTheme.spacing.small
            )
        )
    }
}

/* ------------------------ Recommended --------------------------- */

@Composable
fun RecommendedSection() {
    Column(modifier = Modifier.padding(MaterialTheme.spacing.medium)) {
        Text(
            text = stringResource(R.string.recommended_for_you),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
    }
}

/* ------------------------- Food Item ---------------------------- */

@Composable
fun FoodItemCard(
    item: FoodItem,
    onItemClick: () -> Unit,
    onAddClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = MaterialTheme.spacing.medium,
                vertical = MaterialTheme.spacing.large
            )
    ) {
        Row(
            modifier = Modifier
                .clickable { onItemClick() }
                .padding(
                    horizontal = MaterialTheme.spacing.medium,
                    vertical = MaterialTheme.spacing.large
                )
        ) {

            FoodItemImage(
                imageUrl = item.image,
                onAddClick = onAddClick
            )

            Spacer(Modifier.width(MaterialTheme.spacing.medium))

            FoodItemDetails(modifier = Modifier.weight(1f), item)
        }
    }
}

@Composable
private fun FoodItemImage(
    imageUrl: String,
    onAddClick: () -> Unit
) {
    Box(modifier = Modifier.size(MaterialTheme.spacing.spacing130)) {

        AsyncImage(
            model = imageUrl,
            contentDescription = stringResource(R.string.desc_food_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()
                .clip(MaterialTheme.shapes.medium)
        )

        Text(
            text = stringResource(R.string.bestseller),
            style = MaterialTheme.typography.bodySmall,
            color = Color.DarkGray,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = MaterialTheme.spacing.minus10)
                .background(Color.White, MaterialTheme.shapes.small)
                .padding(
                    horizontal = MaterialTheme.spacing.small,
                    vertical = MaterialTheme.spacing.xSmall
                )
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = MaterialTheme.spacing.offset)
                .clip(MaterialTheme.shapes.small)
                .background(Color.White)
                .border(MaterialTheme.spacing.border, Color.LightGray, MaterialTheme.shapes.small)
                .clickable { onAddClick() }
                .padding(
                    horizontal = MaterialTheme.spacing.small,
                    vertical = MaterialTheme.spacing.xSmall
                )
        ) {
            Text(
                text = stringResource(R.string.add),
                color = Color.Green,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun FoodItemDetails(modifier: Modifier = Modifier, item: FoodItem) {
    Column(modifier = modifier) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            VegNonVegIcon(isVeg = item.isVeg)
            Spacer(Modifier.width(MaterialTheme.spacing.small))
            Text(
                text = item.name,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(Modifier.height(MaterialTheme.spacing.xSmall))

        Text(
            text = item.description,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            maxLines = AppConstants.COUNT_2
        )

        Spacer(Modifier.height(MaterialTheme.spacing.small))

        Text(
            text = stringResource(R.string.price_rupee, item.price),
            fontWeight = FontWeight.Bold
        )
    }
}

/* --------------------- Veg / Non‑Veg Icon ----------------------- */

@Composable
fun VegNonVegIcon(isVeg: Boolean) {
    val color = if (isVeg) Color(0xFF4CAF50) else Color.Red

    Box(
        modifier = Modifier
            .size(MaterialTheme.spacing.medium)
            .border(MaterialTheme.spacing.border, color, RectangleShape),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(MaterialTheme.spacing.small)
                .background(color, CircleShape)
        )
    }
}