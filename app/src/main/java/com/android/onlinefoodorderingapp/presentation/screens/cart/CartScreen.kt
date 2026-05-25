package com.android.onlinefoodorderingapp.presentation.screens.cart

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.android.onlinefoodorderingapp.presentation.theme.spacing
import com.android.onlinefoodorderingapp.R
import com.android.onlinefoodorderingapp.domain.model.CartItem
import com.android.onlinefoodorderingapp.presentation.viewmodel.CartViewModel
import com.android.onlinefoodorderingapp.presentation.viewmodel.CartViewModel2

@Composable
fun CartScreen(navController: NavController, cartViewModel: CartViewModel) {

    val cartItems by cartViewModel.cartItems.collectAsState()
    val totalPrice by cartViewModel.totalPrice.collectAsState()


    CartScreenContent(
        cartItems = cartItems,
        onAdd = { id -> cartViewModel.increaseQuantity(id) },
        onRemove = { id -> cartViewModel.decreaseQuantity(id) },
        onDelete = { id -> cartViewModel.removeItem(id) }
    )


    /*if (cartItems.isEmpty()) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.small)
                .testTag(stringResource(R.string.test_tag_empty_cart)),
            contentAlignment = Alignment.Center
        ) {
            Text(stringResource(R.string.car_is_empty))
        }

    } else {


        LazyColumn(modifier = Modifier.testTag(stringResource(R.string.test_tag_cart_list))) {

            items(cartItems,key = { it.foodItem.foodId + it.quantity }) { item ->
                val quantity = item.quantity
                CartItemRow(
                    item = item,
                    onAdd = { cartViewModel.increaseQuantity(item.foodItem.foodId) },
                    onRemove = { cartViewModel.decreaseQuantity(item.foodItem.foodId) },
                    onDelete = { cartViewModel.removeItem(item.foodItem.foodId) }
                )

            }
        }

    }*/

}

@Composable
fun CartScreenContent(
    cartItems: List<CartItem>,
    onAdd: (String) -> Unit,
    onRemove: (String) -> Unit,
    onDelete: (String) -> Unit
) {

    if (cartItems.isEmpty()) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.small)
                .testTag(stringResource(R.string.test_tag_empty_cart)),
            contentAlignment = Alignment.Center
        ) {
            Text(stringResource(R.string.car_is_empty))
        }

    } else {

        LazyColumn(
            modifier = Modifier.testTag(stringResource(R.string.test_tag_cart_list))
        ) {
            items(
                cartItems,
                key = { it.foodItem.foodId + it.quantity }
            ) { item ->

                CartItemRow(
                    item = item,
                    onAdd = { onAdd(item.foodItem.foodId) },
                    onRemove = { onRemove(item.foodItem.foodId) },
                    onDelete = { onDelete(item.foodItem.foodId) }
                )
            }
        }
    }
}

@Composable
fun CartItemRow(
    item: CartItem,
    onAdd: () -> Unit,
    onRemove: () -> Unit,
    onDelete: () -> Unit
) {



    val cartItemId = stringResource(
        R.string.test_tag_cart_item_id,
        item.foodItem.foodId
    )


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.small, vertical = MaterialTheme.spacing.xSmall)
            .testTag(cartItemId),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = MaterialTheme.spacing.xSmall)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.offset),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.foodItem.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(end = MaterialTheme.spacing.xSmall)

                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.xSmall))

                Text(
                    text =
                        stringResource(
                            R.string.price_format,
                            item.foodItem.price
                            ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {

                IconButton(
                    onClick = onRemove,
                    modifier = Modifier
                        .size(MaterialTheme.spacing.large)
                        .background(Color.LightGray.copy(alpha = 0.3f), CircleShape)
                        .testTag(stringResource(
                            R.string.test_tag_remove,
                            item.foodItem.foodId
                        ))
                ) {
                    Text(stringResource(R.string.minus), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.width(MaterialTheme.spacing.xxSmall))
                Text(
                    modifier = Modifier.padding(MaterialTheme.spacing.small),
                    text = item.quantity.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(MaterialTheme.spacing.xxSmall))
                IconButton(
                    onClick = onAdd,
                    modifier = Modifier
                        .size(MaterialTheme.spacing.large)
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                        .testTag(stringResource(
                            R.string.test_tag_add,
                            item.foodItem.foodId
                        ))
                ) {
                    Text(stringResource(R.string.plus), color = Color.White, style = MaterialTheme.typography.titleMedium)
                }
            }

            Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))

            IconButton(onClick = onDelete,
                modifier = Modifier.testTag(stringResource(R.string.test_tag_delete, item.foodItem.foodId))) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.desc_delete_item),
                    tint = Color.Red
                )
            }
        }
    }

}