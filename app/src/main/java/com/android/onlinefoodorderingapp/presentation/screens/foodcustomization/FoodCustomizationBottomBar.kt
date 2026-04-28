package com.android.onlinefoodorderingapp.presentation.screens.foodcustomization

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.onlinefoodorderingapp.presentation.theme.spacing
import com.android.onlinefoodorderingapp.R


@Composable
    fun FoodCustomizationBottomBar() {

        var quantity by remember { mutableIntStateOf(1) }
        val pricePerItem = 80
        val totalPrice = quantity * pricePerItem
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            tonalElevation = MaterialTheme.spacing.small,
            shadowElevation = MaterialTheme.spacing.small
        ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(MaterialTheme.spacing.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Quantity Stepper
            QuantityStepper(
                quantity = quantity,
                onIncrease = { quantity++ },
                onDecrease = { if (quantity > 1) quantity-- }
            )


            Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))

            //Add Item Button
            Button(
                onClick = { /* Add to cart */ },
                modifier = Modifier
                    .weight(1f)
                    .height(MaterialTheme.spacing.xxLarge),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD32F2F)
                )
            ) {
                Text(
                    text = "Add Item ₹$totalPrice",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
    }

@Composable
fun QuantityStepper(
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .border(1.dp, Color.LightGray, MaterialTheme.shapes.small),
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(onClick = onDecrease, enabled = quantity > 1) {
            Icon(Icons.Default.Remove, contentDescription = stringResource(R.string.desc_decrease_quantity))
        }

        Text(
            text = quantity.toString(),
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small),
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        IconButton(onClick = onIncrease) {
            Icon(Icons.Default.Add, contentDescription = stringResource(R.string.desc_increase_quantity))
        }
    }
}

@Preview
@Composable
fun FoodCustomizationBottomBarPreview() {
    FoodCustomizationBottomBar()
}

