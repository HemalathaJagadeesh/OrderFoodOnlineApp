package com.android.onlinefoodorderingapp.presentation.screens.home

sealed class ProfileAction {
    data object OpenProfile : ProfileAction()
    data object OpenSettings : ProfileAction()
    data object Logout : ProfileAction()
}