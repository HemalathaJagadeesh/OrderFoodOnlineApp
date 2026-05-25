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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.android.onlinefoodorderingapp.presentation.theme.spacing
import com.android.onlinefoodorderingapp.R
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.FoodItem
import com.android.onlinefoodorderingapp.presentation.theme.AppColors

@Composable
    fun FoodCustomizationBottomBar(
    item: FoodItem,
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onAddToCart: (Int) -> Unit

) {


        val pricePerItem = item.price
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
                onIncrease = onIncrease,
                onDecrease =onDecrease
            )


            Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))

            //Add Item Button
            Button(
                onClick = { onAddToCart(quantity) },
                modifier = Modifier
                    .weight(1f)
                    .height(MaterialTheme.spacing.xxLarge)
                .testTag(stringResource(R.string.tt_add_to_cart_button)),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(AppColors.addItemButtonColor.toArgb())
                )
            ) {
                Text(
                    text = stringResource(R.string.add_item_with_price,totalPrice.toString()),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.testTag(stringResource(R.string.tt_total_price_text))
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
            .border(MaterialTheme.spacing.border, Color.LightGray, MaterialTheme.shapes.small),
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(onClick = onDecrease, enabled = quantity > 1,
            modifier = Modifier.testTag(stringResource(R.string.tt_decrease_button))) {
            Icon(Icons.Default.Remove, contentDescription = stringResource(R.string.desc_decrease_quantity))
        }

        Text(
            text = quantity.toString(),
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small)
                .testTag(stringResource(R.string.tt_quantity_text)),
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        IconButton(onClick = onIncrease,
            modifier = Modifier.testTag(stringResource(R.string.tt_increase_button))) {
            Icon(Icons.Default.Add, contentDescription = stringResource(R.string.desc_increase_quantity))
        }
    }
}

@Preview
@Composable
fun FoodCustomizationBottomBarPreview() {

    FoodDetailsContent(FoodItem(
        "",
        "", 0.0, "", "", true, true, ""
    )) { }
}

