package com.android.onlinefoodorderingapp.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val border :Dp = 1.dp,
    val zero: Dp = 0.dp,
    val xxSmall: Dp = 2.dp,
    val xSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 24.dp,
    val xLarge: Dp = 32.dp,
    val xxLarge: Dp = 48.dp,

    val heroBannerHeightMax: Dp = 260.dp,
    val heroBannerHeightMin: Dp = 80.dp,
    val foodCategoryImageSize : Dp = 60.dp,
    val flashBannerHeight: Dp = 130.dp,
    val searchFieldHeight: Dp = 50.dp,
    val buttonHeight: Dp = 56.dp,
    val categoryHeight: Dp = 80.dp,
    val restaurantCardHeight: Dp = 180.dp,

    val offset : Dp = 12.dp,
    val spacing130 : Dp = 130.dp,
    val spacing120 : Dp = 120.dp,
    val minus10 : Dp = (-10).dp,

    val spacing40: Dp = 40.dp,
    val spacing100: Dp = 100.dp

)
val LocalSpacing = staticCompositionLocalOf { Spacing() }

val MaterialTheme.spacing: Spacing
    @Composable
    get() = LocalSpacing.current
