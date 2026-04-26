package com.android.onlinefoodorderingapp.presentation.screens.foodcustomization

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.android.onlinefoodorderingapp.domain.model.foodcustomization.Option
import com.android.onlinefoodorderingapp.domain.model.foodcustomization.OptionGroup
import com.android.onlinefoodorderingapp.presentation.viewmodel.FoodDetailViewModel

@Composable
fun FoodDetailsScreen( foodId: String?,
                       viewModel: FoodDetailViewModel = hiltViewModel()
) {

    //Text(text = "Food ID: $foodId")
    LaunchedEffect(foodId) {
        foodId?.let { viewModel.loadFood(it) }
    }
    val scrollState = rememberLazyListState()
    val collapseProgress by remember {
        derivedStateOf {
            (scrollState.firstVisibleItemScrollOffset / 300f).coerceIn(0f, 1f)
        }
    }

    val optionGroups = sampleData()

    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            state = scrollState,
        ) {

            item {
                HeroSectionFoodCustomization(collapseProgress)
            }

            item {
                FoodTitleSection()
            }

            items(optionGroups) { group ->
                OptionGroupSection(group)
            }
        }

        // 🔥 Blur overlay (collapsing effect)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(
                    Color.White.copy(alpha = collapseProgress * 0.7f)
                )
        )

        FoodCustomizationBottomBar()
    }
}

@Composable
fun HeroSectionFoodCustomization(collapseProgress: Float) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {

        AsyncImage(
            model = "https://images.unsplash.com/photo-1550547660-d9450f859349",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // 🔥 Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = null)
        }

        // 🔥 Restaurant name fades
        Text(
            "Julie's",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            fontSize = 22.sp,
            color = Color.White.copy(alpha = 1f - collapseProgress)
        )
    }
}
@Composable
fun FoodTitleSection() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(16.dp)
    ) {

        Text(
            "Spicy Chickpea Crunch Taco",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Text("₹80", fontWeight = FontWeight.SemiBold)

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun OptionGroupSection(group: OptionGroup) {
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var selectedOptions by remember { mutableStateOf(setOf<String>()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray)
            .padding(16.dp)
    ) {

        Text(group.title, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        group.options.forEach { option ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (group.isSingleChoice) {
                            selectedOption = option.id
                        } else {
                            selectedOptions =
                                if (option.id in selectedOptions)
                                    selectedOptions - option.id
                                else
                                    selectedOptions + option.id
                        }
                    }
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // 🔷 LEFT: NAME
                Text(
                    text = option.name,
                    modifier = Modifier.weight(1f)
                )

                // 🔷 RIGHT: PRICE + CONTROL
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    if (option.price > 0) {
                        Text(
                            text = "+₹${option.price}",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }

                    if (group.isSingleChoice) {
                        RadioButton(
                            selected = selectedOption == option.id,
                            onClick = null
                        )
                    } else {
                        Checkbox(
                            checked = option.id in selectedOptions,
                            onCheckedChange = null
                        )
                    }
                }
            }
        }
    }

}


@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.ArrowBack, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text("Julie's")
    }
}
@Composable
fun FoodInfo() {
    Column(modifier = Modifier.padding(16.dp)) {

        Text("Spicy Chicken Crunch Taco", fontWeight = FontWeight.Bold)
        Text("₹180")

        Spacer(modifier = Modifier.height(16.dp))

        Text("Tacos", fontWeight = FontWeight.Bold)

        Row {
            Text("Soft")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Hard")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Add Ons", fontWeight = FontWeight.Bold)

        AddOnItem("Extra Cheese", 40)
        AddOnItem("Sauce Dip", 20)
    }
}
@Composable
fun AddOnItem(name: String, price: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(name)
        Text("+₹$price")
    }
}
@Composable
fun AddToCartBar() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Red)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("1", color = Color.White)
        Text("Add Item ₹180", color = Color.White)
    }
}
fun sampleData(): List<OptionGroup> {
    return listOf(
        OptionGroup(
            title = "Taco shell",
            isSingleChoice = true,
            options = listOf(
                Option("1", "Crunchy shell"),
                Option("2", "Soft shell")
            )
        ),
        OptionGroup(
            title = "Add Ons",
            isSingleChoice = false,
            options = listOf(
                Option("3", "Jalapeño Cheese", 20),
                Option("4", "Extra Sauce", 10),
                Option("5", "Jalapeño Dip", 15)
            )
        )
    )
}

@Preview
@Composable
fun FoodDetailsScreenPreview() {
    FoodDetailsScreen(foodId = "1")
}