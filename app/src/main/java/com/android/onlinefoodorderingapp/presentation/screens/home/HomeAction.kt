package com.android.onlinefoodorderingapp.presentation.screens.home

sealed class HomeAction {
    data object OpenProfile : HomeAction()
    data object OpenSettings : HomeAction()
    data object Logout : HomeAction()
}