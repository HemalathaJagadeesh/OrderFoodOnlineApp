package com.android.onlinefoodorderingapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.android.onlinefoodorderingapp.domain.model.OrderType

@Composable
fun MainBottomBar(
    selectedType: OrderType,
    onTypeChange: (OrderType) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        BottomBarButton(
            text = "Delivery",
            selected = selectedType == OrderType.DELIVERY,
            onClick = { onTypeChange(OrderType.DELIVERY) },
            modifier = Modifier.weight(1f)
        )

        BottomBarButton(
            text = "Dining",
            selected = selectedType == OrderType.DINE_IN,
            onClick = { onTypeChange(OrderType.DINE_IN) },
            modifier = Modifier.weight(1f)
        )
    }
}
@Composable
fun BottomBarButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Color(0xFFFFC107) else Color.LightGray,
            contentColor = if (selected) Color.Black else Color.DarkGray
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text)
    }
}

