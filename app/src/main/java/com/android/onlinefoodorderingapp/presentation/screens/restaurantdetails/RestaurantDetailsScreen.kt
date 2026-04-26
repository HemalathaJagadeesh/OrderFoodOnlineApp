package com.android.onlinefoodorderingapp.presentation.screens.restaurantdetails

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.android.onlinefoodorderingapp.R
import com.android.onlinefoodorderingapp.presentation.viewmodel.RestaurantDetailViewModel
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import com.android.onlinefoodorderingapp.presentation.util.Routes

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RestaurantDetailsScreen(navController: NavController,
                            viewModel: RestaurantDetailViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()
    var searchText by remember { mutableStateOf("") }
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val scrollState = rememberLazyListState()
    val isCollapsed by remember {
        derivedStateOf { scrollState.firstVisibleItemScrollOffset > 200 }
    }

    Box {
        LazyColumn(
            state = scrollState,
            contentPadding = PaddingValues(bottom = 120.dp)
        ) {

            item {
                HeroSection(isCollapsed)
            }

            item {
                RestaurantInfoSection()
            }

            /* stickyHeader {
                StickyTopBar(isCollapsed)
            }*/

            item {
                RecommendedSection()
            }


            items(state.foodItem){ foodItem ->
                FoodItemCard(item = foodItem,
                    onItemClick = {
                        //viewModel.onFoodItemClick(foodItem)
                        Log.d("NAV_DEBUG", "Navigating to: ${Routes.FOOD_DETAILS_SCREEN}/${foodItem.id}")
                        navController.navigate("food_details/${foodItem.id}") },
                    onAddClick = {viewModel.onAddItemClick()})
            }


        }

    }

}
@Composable
fun HeroSection(isCollapsed: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {
        AsyncImage(
            model = "https://images.unsplash.com/photo-1550547660-d9450f859349",
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        /*if (!isCollapsed) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text("Julie's", color = Color.White, fontSize = 22.sp)
                Text("Italian • ₹300 for one", color = Color.White)
                Text("⭐ 4.2 • 35 mins", color = Color.White)
            }
        }*/
    }
}

@Composable
fun RestaurantHeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Julie’s",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "0.7 km away • 18 mins",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Kerala • Indian • Fast Food",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(10.dp))

            OfferBadge()

            Spacer(modifier = Modifier.height(10.dp))

            RestaurantStats()
        }

        Spacer(modifier = Modifier.width(12.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),//food_sample
            contentDescription = "Restaurant image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(16.dp))
        )
    }
}


@Composable
fun OfferBadge() {
    Box(
        modifier = Modifier
            .background(Color(0xFFFFF3CD), RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = "50% OFF",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFB26A00)
        )
    }
}

@Composable
fun RestaurantStats() {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        StatItem("4.7", Icons.Default.Star)
       // StatItem("41–45 min", Icons.Default.AccessTime)
       // StatItem("₹300 for two", Icons.Default.CurrencyRupee)
    }
}

@Composable
fun StatItem(text: String, icon: ImageVector) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(14.dp))
        Spacer(Modifier.width(4.dp))
        Text(text, fontSize = 12.sp)
    }
}


@Composable
fun DietTag(text: String, bgColor: Color) {
    Text(
        text = text,
        fontSize = 12.sp,
        modifier = Modifier
            .background(bgColor, RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    )
}


@Preview
@Composable
fun RestaurantDetailsContentPreview() {

   // RestaurantDetailsScreen(rememberNavController())
}

//------------

@Composable
fun StickyTopBar(isCollapsed: Boolean) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {

        if (isCollapsed) {
            Text(
                "Julie's",
                modifier = Modifier.padding(16.dp),
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Search for dishes") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text("Veg", color = Color.Green)
        }
    }
}
@Composable
fun RecommendedSection() {
    Column(modifier = Modifier.padding(16.dp)) {

        Text("Recommended for you", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(12.dp))

        //FoodItemCard()
    }
}

@Composable
fun FoodItemCard(item: FoodItem,
                 onItemClick: () -> Unit,
                 onAddClick: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 24.dp),
        onClick = onItemClick) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {

            // 🔷 LEFT: IMAGE + OVERLAYS
            Box(
                modifier = Modifier.size(130.dp)
            ) {

                AsyncImage(
                    model = item.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(12.dp))
                )

                // 🔝 TOP TAG (overlapping)
                Text(
                    text = "Bestseller",
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = (-10).dp)
                        .background(Color.White, RoundedCornerShape(6.dp))
                        .padding(horizontal = 12.dp, vertical = 2.dp),
                    fontSize = 10.sp,
                    color = Color.Black,
                )

                // 🔽 ADD BUTTON (overlapping bottom)

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = 12.dp) // 👈 makes it overlap outside
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                        .clickable {onAddClick() }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "ADD",
                        color = Color.Green,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // 🔷 RIGHT: TEXT CONTENT
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.Top)
            ) {

                // Veg / Non-Veg + Name
                Row(verticalAlignment = Alignment.CenterVertically) {

                    VegNonVegIcon(isVeg = true)

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "Spicy Chicken Crunch Taco",
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Crunchy taco with spicy chicken filling",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = item.price,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
@Composable
fun VegNonVegIcon(isVeg: Boolean) {

    val color = if (isVeg) Color(0xFF4CAF50) else Color.Red

    Box(
        modifier = Modifier
            .size(14.dp)
            .border(1.dp, color, RectangleShape),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(color, CircleShape)
        )
    }
}


@Composable
fun RestaurantMetaRow() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        MetaItem("50% OFF", Color(0xFF4CAF50))
        MetaItem("⭐ 4.2", color = Color(0xFFFFC107))
        MetaItem("35 mins", color = Color(0xFF4CAF50))
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

@Composable
fun FilterRow() {

    val filters = listOf("Filter", "Veg", "Non-Veg", "Spicy")
    var selectedFilter by remember { mutableStateOf(filters[0]) }

    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        filters.forEach { filter ->

            FilterChip(
                text = filter,
                selected = selectedFilter == filter,
                onClick = {
                    selectedFilter = filter
                }
            )
        }
    }
}

@Composable
fun FilterChip(text: String, selected: Boolean,
               onClick: () -> Unit) {

    val (icon, tint) = when (text) {
        "Veg" -> Icons.Default.Circle to Color(0xFF4CAF50)
        "Non-Veg" -> Icons.Default.Circle to Color.Red
        "Spicy" -> Icons.Default.LocalFireDepartment to Color(0xFFFF5722)
        else -> null to Color.Unspecified
    }
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .border(1.dp, Color(0xFF4CAF50), RoundedCornerShape(6.dp))

            .background(
                if (selected) Color.Green else Color.Transparent,
                RoundedCornerShape(6.dp)
            )
            .clickable { onClick() }

    ) {
        Text(text = text, fontSize = 12.sp,
            color = if (selected) Color.White else Color.White,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))

    }
}
@Composable
fun RestaurantInfoSection() {

    Column {

        RestaurantMetaRow()

        Spacer(modifier = Modifier.height(8.dp))

        FilterRow()

        Spacer(modifier = Modifier.height(12.dp))
    }
}