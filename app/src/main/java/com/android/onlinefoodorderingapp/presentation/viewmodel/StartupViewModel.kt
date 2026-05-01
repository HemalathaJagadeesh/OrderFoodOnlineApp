package com.android.onlinefoodorderingapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.onlinefoodorderingapp.data.local.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class StartupViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {
    val isLoggedIn = sessionManager.isLoggedIn
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            null // initial unknown
        )
}