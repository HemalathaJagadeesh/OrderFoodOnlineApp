package com.android.onlinefoodorderingapp.presentation.screens.home

sealed class HomeEvent {
    data object onLogoutClicked : HomeEvent()
}