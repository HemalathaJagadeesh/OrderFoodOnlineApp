package com.android.onlinefoodorderingapp.presentation.screens.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.android.onlinefoodorderingapp.R
import com.android.onlinefoodorderingapp.presentation.theme.spacing

@Composable
fun CartBottomBar(
    total: Double,
    onCheckoutClick: () -> Unit
) {
    Surface(
        tonalElevation = MaterialTheme.spacing.small,
        shadowElevation =  MaterialTheme.spacing.small
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding( MaterialTheme.spacing.medium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = stringResource(R.string.cart_total,total),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.testTag(stringResource(R.string.tt_cart_total_text))
            )

            Button(
                onClick = onCheckoutClick,
                modifier = Modifier.testTag(stringResource(R.string.tt_checkout_button))
            ) {
                Text(stringResource(R.string.checkout))
            }
        }
    }
}