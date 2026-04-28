package com.android.onlinefoodorderingapp.presentation.screens

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.android.onlinefoodorderingapp.R
import com.android.onlinefoodorderingapp.presentation.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(scrollBehavior: TopAppBarScrollBehavior) {
    LargeTopAppBar(
        title = {
            MainHeaderRow()
        }, scrollBehavior = scrollBehavior
    )
}

@Composable
fun MainHeaderRow() {
    Column {
        // ✅ Address Row
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.LocationOn, contentDescription = stringResource(R.string.desc_location_icon))
            Text("Ludhiana Bus Stop")
            Spacer(modifier = Modifier.weight(1f))
            Icon(Icons.Default.AccountCircle, contentDescription = stringResource(R.string.desc_profile_icon))
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        // ✅ Search + Veg Toggle
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.weight(1f),
                placeholder = { Text(stringResource(R.string.search_for_food)) })
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(stringResource(R.string.veg))
                Switch(
                    checked = true, onCheckedChange = {})
            }
        }
    }
}

@Composable
fun HeroBanner_() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {

        // 🔹 Background Image
        AsyncImage(
            model = "https://img.freepik.com/free-vector/food-delivery-service-fast-food-delivery-scooter",
            contentDescription = "Banner",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // 🔹 Optional dark overlay (for better text visibility)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
        )

        // 🔹 Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            // Top spacing (keeps content balanced)
            Spacer(modifier = Modifier.height(8.dp))

            // 🔹 Main Text + Button
            Column {

                Text(
                    text = "Your Cravings,\nDelivered Fresh",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 26.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { /* TODO */ }, colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFC107), contentColor = Color.Black
                    ), shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Order Now")
                }
            }
        }
    }
}

@Composable
fun SearchRow() {

    Column {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.LocationOn, null)
            Text("Ludhiana Bus Stop")

            Spacer(modifier = Modifier.weight(1f))

            Icon(Icons.Default.AccountCircle, null)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {

            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                placeholder = { Text("Search...", fontSize = 14.sp) })

            Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))

            Column {
                Text("VEG", fontSize = 14.sp)
                Switch(
                    checked = true, onCheckedChange = {})
            }

        }
    }
}

/*
@Composable
fun HomeTopBar() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .background(Color.Transparent)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column {
            Text("Home", color = Color.White, fontWeight = FontWeight.Bold)
            Text("Sarojini Nagar", color = Color.White.copy(alpha = 0.7f))
        }

        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            tint = Color.White
        )
    }
}*/


@Composable
fun HomeTopBar(collapseFraction: Float) {

    val backgroundAlpha = collapseFraction

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .background(Color(0xFFFFC107).copy(alpha = backgroundAlpha))
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column {
            Text("Ludhiana Bus Stop", fontWeight = FontWeight.Bold)
            Text(
                "Welcome Guest, Set Pickup Location",
                fontSize = 12.sp
            )
        }

        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        )
    }
}
