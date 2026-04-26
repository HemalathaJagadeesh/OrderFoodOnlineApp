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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage

// ----------------------------
// SCREEN
// ----------------------------

@Composable
fun FoodDetailsScreen1(
    navController: NavController,
    foodId: String?
) {

    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 90.dp)
        ) {

            item { HeaderSection(navController) }

            item { FoodInfoSection() }

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
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
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
fun FoodInfoSection() {

    Column(modifier = Modifier.padding(16.dp)) {

        Text(
            text = "Spicy Chickpea Crunch Taco",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Crispy chickpea tossed in spicy sauce",
            color = Color.Gray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text("₹80", fontWeight = FontWeight.Bold)
    }
}

// ---------------------- OPTIONS ----------------------

@Composable
fun OptionGroupSection() {

    var selected by remember { mutableStateOf("Soft") }

    Column(modifier = Modifier.padding(16.dp)) {

        Text("Taco shell", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        listOf("Crunchy", "Soft").forEach { option ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selected = option }
                    .padding(vertical = 8.dp),
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

    Column(modifier = Modifier.padding(16.dp)) {

        Text("Add Ons", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        items.forEach { (name, price) ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column {
                    Text(name)
                    Text("+₹$price", fontSize = 12.sp, color = Color.Gray)
                }

                Checkbox(
                    checked = selected[name] == true,
                    onCheckedChange = { selected[name] = it }
                )
            }
        }
    }
}

// ---------------------- BOTTOM BAR ----------------------
/*
@Composable
fun FoodCustomizationBottomBar(
    modifier: Modifier = Modifier
) {

    var quantity by remember { mutableIntStateOf(1) }
    val pricePerItem = 80
    val totalPrice = quantity * pricePerItem

    Surface(
        modifier = modifier.fillMaxWidth(),
        tonalElevation = 8.dp,
        shadowElevation = 8.dp
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Quantity Stepper
            Row(
                modifier = Modifier
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    "-",
                    modifier = Modifier.clickable {
                        if (quantity > 1) quantity--
                    }
                )

                Text(
                    quantity.toString(),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Text(
                    "+",
                    modifier = Modifier.clickable { quantity++ }
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Add Button
            Button(
                onClick = { *//* TODO: Add to cart *//* },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD32F2F)
                )
            ) {
                Text("Add Item ₹$totalPrice")
            }
        }
    }
}*/
data class OptionItem(
    val id: String,
    val name: String
)

@Preview
@Composable
fun FoodDetailsScreen1Preview(){
FoodDetailsScreen1( foodId = null, navController = NavController(LocalContext.current))
}