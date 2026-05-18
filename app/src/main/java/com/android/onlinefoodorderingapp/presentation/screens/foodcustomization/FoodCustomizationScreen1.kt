package com.android.onlinefoodorderingapp.presentation.screens.foodcustomization

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import com.android.onlinefoodorderingapp.R
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import com.android.onlinefoodorderingapp.presentation.theme.spacing
import com.android.onlinefoodorderingapp.presentation.util.AppConstants
import com.android.onlinefoodorderingapp.presentation.util.shareFoodWithImage
import com.android.onlinefoodorderingapp.presentation.viewmodel.CartViewModel2
import com.android.onlinefoodorderingapp.presentation.viewmodel.FoodDetailViewModel


@Composable
fun FoodDetailsScreen1(
    foodId: String?,
    navController: NavController,
    cartViewModel: CartViewModel2,
    onFoodLoaded: (FoodItem) -> Unit,
    viewModel: FoodDetailViewModel = hiltViewModel()
) {


    /*val foodItem = state.allFoodItems.find { it.id == foodId }
        ?: return*/


    val state by viewModel.state.collectAsState()
    val foodItem = state.foodItem

    Log.i("TAG", "FoodDetailsScreen1: ${foodItem?.name}")


    LaunchedEffect(foodId) {
        foodId?.let { viewModel.loadFood(it) }

    }
    // ✅ Send data back to NavigationHost
    LaunchedEffect(foodItem) {
        foodItem?.let { onFoodLoaded(it) }
    }


    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = MaterialTheme.spacing.spacing130)
        ) {

            item { HeaderSection(navController) }

            item { FoodInfoSection(state.foodItem) }

            item { OptionGroupSection() }

            item { AddOnSection() }
        }

        /*FoodCustomizationBottomBar(
            modifier = Modifier.align(Alignment.BottomCenter)
        )*/
    }
}

// ---------------------- HEADER ----------------------

@Composable
fun HeaderSection(navController: NavController) {

    Box {

        // Replace with Coil if needed
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.spacing.spacing220)
                .background(Color.LightGray)
        )

        AsyncImage(
            model = "https://images.unsplash.com/photo-1550547660-d9450f859349",
            contentDescription = stringResource(R.string.desc_profile_image),
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.small),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

        }
    }
}

// ---------------------- FOOD INFO ----------------------

@Composable
fun FoodInfoSection(foodItem: FoodItem?) {
    val context = LocalContext.current
    Column(modifier = Modifier.padding(MaterialTheme.spacing.medium)) {

        foodItem?.let {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = it.name,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )

                IconButton(
                    onClick = {
                        //shareFoodItem(context, it)

                        shareFoodWithImage(context, foodItem)
                    }) {
                    Icon(Icons.Default.Share, contentDescription = "Share")

                }
            }
        }

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.xSmall))

        foodItem?.let {
            Text(
                text = foodItem.description,
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))

        Text(
            text =
                stringResource(
                    R.string.price_rupee,
                    foodItem?.price ?: stringResource(R.string.price_free)
                ), fontWeight = FontWeight.Bold
        )
    }
}

// ---------------------- OPTIONS ----------------------

@Composable
fun OptionGroupSection() {

    var selected by remember { mutableStateOf("Soft") }

    Column(modifier = Modifier.padding(MaterialTheme.spacing.medium)) {

        Text("Taco shell", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))

        listOf("Crunchy", "Soft").forEach { option ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selected = option }
                    .padding(vertical = MaterialTheme.spacing.small),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(option)

                RadioButton(
                    selected = selected == option,
                    onClick = { selected = option }
                )
            }
        }
    }
}

// ---------------------- ADD ONS ----------------------

@Composable
fun AddOnSection() {

    val items = listOf(
        "Jalapeno Cheese" to 20,
        "Extra Sauce" to 10,
        "Jalapeno Dip" to 15
    )

    val selected: SnapshotStateMap<String, Boolean> = remember {
        mutableStateMapOf()
    }

    Column(modifier = Modifier.padding(MaterialTheme.spacing.medium)) {

        Text(stringResource(id = R.string.add_ons), fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))

        items.forEach { (name, price) ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.spacing.small),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column {
                    Text(name)
                    Text(
                        stringResource(R.string.price_add_rupee, price),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

                Checkbox(
                    checked = selected[name] == true,
                    onCheckedChange = { selected[name] = it }
                )
            }
        }
    }
}


fun shareFoodItem(context: Context, item: FoodItem) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(
            Intent.EXTRA_TEXT,
            "Check out this item 🍔\n\n${item.name}\n₹${item.price}\n\n${item.description}"
        )
    }

    context.startActivity(
        Intent.createChooser(intent, "Share via")
    )
}


@Preview
@Composable
fun FoodDetailsScreen1Preview() {
    FoodDetailsScreen1(
        foodId = AppConstants.EMPTY_STRING,
        navController = NavController(LocalContext.current),
        cartViewModel = CartViewModel2(),
        onFoodLoaded = {}
    )
}