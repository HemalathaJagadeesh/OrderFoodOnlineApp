package com.android.onlinefoodorderingapp.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.android.onlinefoodorderingapp.presentation.util.Routes
import com.android.onlinefoodorderingapp.presentation.viewmodel.StartupViewModel

@Composable
fun AppRoot(navController: NavHostController = rememberNavController()) {

    val viewModel: StartupViewModel = hiltViewModel()
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    when (isLoggedIn) {

        null -> {
            // Splash / loading
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        true -> {
            NavigationHost(
                navController = navController,
                startDestination = Routes.HOME_SCREEN
            )
        }

        false -> {
            NavigationHost(
                navController = navController,
                startDestination = Routes.AUTHENTICATION_SCREEN
            )
        }
    }
}//100630056008 chinnu@07