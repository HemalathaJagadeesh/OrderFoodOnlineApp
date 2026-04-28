package com.android.onlinefoodorderingapp.presentation.screens.restaurantdetails

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.android.onlinefoodorderingapp.R
import com.android.onlinefoodorderingapp.domain.model.restaurantdetails.MenuItem
import com.android.onlinefoodorderingapp.presentation.theme.spacing

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RestaurantDetailsBottomBar(
    searchText: String,
    onSearchChange: (String) -> Unit,
    onMenuClick: () -> Unit,
    modifier:Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.small),
        verticalAlignment = Alignment.CenterVertically
    ) {

        TextField(
            value = searchText,
            onValueChange = onSearchChange,
            placeholder = { Text(stringResource(R.string.serach_menu)) },
            modifier = modifier
                .weight(1f)
                .height(MaterialTheme.spacing.searchFieldHeight),
            shape =MaterialTheme.shapes.small,
            singleLine = true
        )

        Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))

        Button(
            onClick = {
                onMenuClick()},
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.height(MaterialTheme.spacing.searchFieldHeight)
        ) {
            Text(stringResource(R.string.menu))
        }
    }
}

@Composable
fun MenuContent(
    categories: List<String>,
    onClick: (String) -> Unit
) {
    LazyColumn {
        items(categories) { category ->
            Text(
                text = category,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(category) }
                    .padding(MaterialTheme.spacing.medium)
            )
        }
    }
}
@Composable
fun MenuItemRow(item: MenuItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.medium, vertical = MaterialTheme.spacing.small),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.xSmall))

            Text(
                text = "₹${item.price}",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Button(
            onClick = { /* TODO: add to cart */ },
            shape = MaterialTheme.shapes.small
        ) {
            Text(stringResource(R.string.add))
        }
    }
}

    @Preview
    @Composable
    fun BottomSearchMenuBarPreview() {
        RestaurantDetailsBottomBar("", {}, {}, Modifier)
    }
