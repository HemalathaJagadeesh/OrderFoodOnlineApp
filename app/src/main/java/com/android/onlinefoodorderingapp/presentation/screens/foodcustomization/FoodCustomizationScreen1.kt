package com.android.onlinefoodorderingapp.presentation.screens.foodcustomization

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
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
import coil.compose.AsyncImage
import com.android.onlinefoodorderingapp.R
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import com.android.onlinefoodorderingapp.presentation.theme.spacing
import com.android.onlinefoodorderingapp.presentation.util.AppConstants
import com.android.onlinefoodorderingapp.presentation.viewmodel.FoodDetailViewModel


@Composable
fun FoodDetailsScreen1(
    foodId: String?,
    navController: NavController,
    viewModel: FoodDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(foodId) {
        foodId?.let { viewModel.loadFood(it) }
    }
    val state by viewModel.state.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 90.dp)
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
                .height(220.dp)
                .background(Color.LightGray)
        )

        AsyncImage( model = "https://images.unsplash.com/photo-1550547660-d9450f859349",
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

           /* IconButton(onClick = { navController.popBackStack() }) {
                Text("<")
            }*/

           /* Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text("CUSTOMIZE", fontSize = 12.sp)
            }*/
        }
    }
}

// ---------------------- FOOD INFO ----------------------

@Composable
fun FoodInfoSection(foodItem: FoodItem?) {

    Column(modifier = Modifier.padding(MaterialTheme.spacing.medium)) {

        foodItem?.let {
            Text(
                text = foodItem.name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.xSmall))

        foodItem?.let{
            Text(
                text = foodItem.description,
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))

        Text(text =
            stringResource(
                R.string.price_rupee,
                foodItem?.price ?: stringResource(R.string.price_free)
            ), fontWeight = FontWeight.Bold)
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
                    .padding(vertical =MaterialTheme.spacing.small),
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
                    Text(stringResource(R.string.price_add_rupee,price), fontSize = 12.sp, color = Color.Gray)
                }

                Checkbox(
                    checked = selected[name] == true,
                    onCheckedChange = { selected[name] = it }
                )
            }
        }
    }
}


@Preview
@Composable
fun FoodDetailsScreen1Preview(){
FoodDetailsScreen1(  foodId = AppConstants.EMPTY_STRING, navController = NavController(LocalContext.current))
}