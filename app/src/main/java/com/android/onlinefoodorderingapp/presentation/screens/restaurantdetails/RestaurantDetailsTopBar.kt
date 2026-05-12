package com.android.onlinefoodorderingapp.presentation.screens.restaurantdetails

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.onlinefoodorderingapp.R
import com.android.onlinefoodorderingapp.presentation.theme.spacing
import com.android.onlinefoodorderingapp.presentation.viewmodel.CartViewModel
import com.android.onlinefoodorderingapp.presentation.viewmodel.CartViewModel2

@Composable
fun RestaurantDetailsTopBar(navController: NavController ) {


    val cartViewModel: CartViewModel2 = hiltViewModel()
    val cartCount by cartViewModel.cartCount.collectAsState(initial = 0)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Red)
            .statusBarsPadding()
            .padding(horizontal = MaterialTheme.spacing.medium, vertical = MaterialTheme.spacing.small),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {


        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.desc_back),
                tint = Color.Black
            )
        }


        IconButton(
            onClick = {
                navController.navigate("cart_screen")
            }
        ) {
            BadgedBox(
                badge = {
                    if (cartCount > 0) {
                        Badge {
                            Text(text = cartCount.toString())
                        }
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Cart",
                    tint = Color.Black
                )
            }
        }

        /*OutlinedButton(
            onClick = { *//* filter *//* },
            shape = MaterialTheme.shapes.large,
            border = BorderStroke(1.dp, Color.LightGray),
            contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.medium, vertical = MaterialTheme.spacing.small)
        ) {
            Icon(
                imageVector = Icons.Default.ThumbUp, //it has to be Tune, check it
                contentDescription = stringResource(R.string.desc_filter),
                modifier = Modifier.size(MaterialTheme.spacing.medium)
            )
            Spacer(Modifier.width(MaterialTheme.spacing.small))
            Text(
                text = "FILTER",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium
            )
        }*/
    }

}

@Preview
@Composable
fun RestaurantDetailsTopBarPreview(){
  //  RestaurantDetailsTopBar(navController = NavController(LocalContext.current))
}
